package com.dailyhealthcoach.ui.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.data.export.DataExportService
import com.dailyhealthcoach.data.export.DataRestoreService
import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.BodyMetricLogInput
import com.dailyhealthcoach.domain.model.UserProfile
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import com.dailyhealthcoach.domain.repository.MacroTargetRepository
import com.dailyhealthcoach.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate

class ProfileViewModel(
    private val userProfileRepository: UserProfileRepository,
    private val macroTargetRepository: MacroTargetRepository,
    private val bodyMetricRepository: BodyMetricRepository,
    private val dataExportService: DataExportService,
    private val dataRestoreService: DataRestoreService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var existingBirthDate: String? = null
    private var existingBedtime: String? = null

    private enum class PendingField { STEPS, SLEEP, WEIGHT, RESTING_HR }
    private var pendingField: PendingField? = null
    private var pendingSteps: Long? = null
    private var pendingSleep: Double? = null
    private var pendingWeight: Double? = null
    private var pendingHr: Int? = null
    private var pendingExisting: BodyMetricLog? = null

    init {
        viewModelScope.launch {
            val profile = userProfileRepository.observeUserProfile().first()
            val macroTarget = macroTargetRepository.observeActiveTarget().first()
            existingBirthDate = profile?.birthDate
            existingBedtime = profile?.bedtime
            val heightParts = profile?.heightInches.toHeightParts()
            _uiState.value = ProfileUiState(
                name = profile?.name ?: "",
                age = profile?.age?.toString() ?: "",
                sex = profile?.sex ?: "Male",
                heightFeet = heightParts.first,
                heightInches = heightParts.second,
                weightGoal = profile?.weightGoalPounds.cleanString(),
                bodyFatGoal = profile?.bodyFatGoalPercent.cleanString(),
                proteinMin = macroTarget?.proteinMinGrams?.toString() ?: "170",
                proteinMax = macroTarget?.proteinMaxGrams?.toString() ?: "200",
                stepMin = profile?.stepMinTarget?.toString() ?: "8000",
                stepMax = profile?.stepMaxTarget?.toString() ?: "10000",
                sleepTarget = profile?.sleepTargetHours?.cleanString() ?: "7",
                strengthTarget = profile?.strengthTrainingDaysPerWeek?.toString() ?: "3",
                nutritionGoal = profile?.nutritionGoal ?: "Maintain",
                dietPreference = profile?.dietPreference ?: "No Restriction"
            )
        }
    }

    fun updateForm(transform: (ProfileUiState) -> ProfileUiState) {
        _uiState.value = transform(_uiState.value).copy(error = null, savedSuccess = false)
    }

    fun save() {
        val s = _uiState.value
        val proteinMin = s.proteinMin.toIntOrNull()
        val proteinMax = s.proteinMax.toIntOrNull()
        val stepMin = s.stepMin.toIntOrNull()
        val stepMax = s.stepMax.toIntOrNull()
        val sleepTarget = s.sleepTarget.toDoubleOrNull()
        val strengthTarget = s.strengthTarget.toIntOrNull()
        val heightFeet = s.heightFeet.toIntOrNull()
        val heightInchesComponent = s.heightInches.toIntOrNull() ?: 0

        if (proteinMin != null && proteinMax != null && proteinMin > proteinMax) {
            _uiState.value = s.copy(error = "Protein min must be ≤ protein max"); return
        }
        if (stepMin != null && stepMax != null && stepMin > stepMax) {
            _uiState.value = s.copy(error = "Step min must be ≤ step max"); return
        }
        if (sleepTarget != null && sleepTarget !in 4.0..12.0) {
            _uiState.value = s.copy(error = "Sleep target must be 4–12 hours"); return
        }
        if (strengthTarget != null && strengthTarget !in 1..7) {
            _uiState.value = s.copy(error = "Strength sessions must be 1–7 per week"); return
        }
        if (heightFeet != null && heightFeet !in 3..8) {
            _uiState.value = s.copy(error = "Height feet must be 3–8"); return
        }
        if (s.heightInches.isNotBlank() && heightInchesComponent !in 0..11) {
            _uiState.value = s.copy(error = "Height inches must be 0–11"); return
        }

        val totalHeightInches = if (heightFeet != null) {
            (heightFeet * 12 + heightInchesComponent).toDouble()
        } else null

        viewModelScope.launch {
            userProfileRepository.saveProfile(
                UserProfile(
                    id = 1,
                    name = s.name.trim(),
                    heightInches = totalHeightInches,
                    birthDate = existingBirthDate,
                    bedtime = existingBedtime,
                    age = s.age.toIntOrNull(),
                    sex = s.sex.ifBlank { null },
                    weightGoalPounds = s.weightGoal.toDoubleOrNull(),
                    bodyFatGoalPercent = s.bodyFatGoal.toDoubleOrNull(),
                    stepMinTarget = stepMin,
                    stepMaxTarget = stepMax,
                    sleepTargetHours = sleepTarget,
                    strengthTrainingDaysPerWeek = strengthTarget,
                    nutritionGoal = s.nutritionGoal.ifBlank { null },
                    dietPreference = s.dietPreference.ifBlank { null }
                )
            )
            macroTargetRepository.saveTarget(
                proteinMin = proteinMin ?: 170,
                proteinMax = proteinMax ?: 200
            )
            _uiState.value = _uiState.value.copy(savedSuccess = true, error = null)
        }
    }

    // ── Health Connect Import ────────────────────────────────────────────────
    // TODO: track that imported values originated from Health Connect (requires schema change).

    fun requestStepsImport(steps: Long) {
        val today = LocalDate.now().toString()
        viewModelScope.launch {
            val existing = bodyMetricRepository.observeForDate(today).first()
            if (existing?.stepCount != null) {
                pendingField = PendingField.STEPS
                pendingSteps = steps
                pendingExisting = existing
                _uiState.value = _uiState.value.copy(
                    hcImportConflict = HcImportConflict(
                        fieldLabel = "Steps",
                        existingDisplay = "%,d steps".format(existing.stepCount),
                        hcDisplay = "%,d steps".format(steps)
                    )
                )
            } else {
                bodyMetricRepository.saveForDate(buildInput(today, existing, stepCount = steps.toInt()))
                _uiState.value = _uiState.value.copy(hcImportMessage = "Steps imported from Health Connect.")
            }
        }
    }

    fun requestSleepImport(hours: Double) {
        val today = LocalDate.now().toString()
        viewModelScope.launch {
            val existing = bodyMetricRepository.observeForDate(today).first()
            if (existing?.sleepHours != null) {
                pendingField = PendingField.SLEEP
                pendingSleep = hours
                pendingExisting = existing
                _uiState.value = _uiState.value.copy(
                    hcImportConflict = HcImportConflict(
                        fieldLabel = "Sleep",
                        existingDisplay = "%.1f h".format(existing.sleepHours),
                        hcDisplay = "%.1f h".format(hours)
                    )
                )
            } else {
                bodyMetricRepository.saveForDate(buildInput(today, existing, sleepHours = hours))
                _uiState.value = _uiState.value.copy(hcImportMessage = "Sleep imported from Health Connect.")
            }
        }
    }

    fun requestWeightImport(lbs: Double) {
        val today = LocalDate.now().toString()
        viewModelScope.launch {
            val existing = bodyMetricRepository.observeForDate(today).first()
            if (existing?.bodyWeight != null) {
                pendingField = PendingField.WEIGHT
                pendingWeight = lbs
                pendingExisting = existing
                _uiState.value = _uiState.value.copy(
                    hcImportConflict = HcImportConflict(
                        fieldLabel = "Weight",
                        existingDisplay = "%.1f lbs".format(existing.bodyWeight),
                        hcDisplay = "%.1f lbs".format(lbs)
                    )
                )
            } else {
                bodyMetricRepository.saveForDate(buildInput(today, existing, bodyWeight = lbs))
                _uiState.value = _uiState.value.copy(hcImportMessage = "Weight imported from Health Connect.")
            }
        }
    }

    fun requestHrImport(bpm: Int) {
        val today = LocalDate.now().toString()
        viewModelScope.launch {
            val existing = bodyMetricRepository.observeForDate(today).first()
            if (existing?.restingHeartRate != null) {
                pendingField = PendingField.RESTING_HR
                pendingHr = bpm
                pendingExisting = existing
                _uiState.value = _uiState.value.copy(
                    hcImportConflict = HcImportConflict(
                        fieldLabel = "Resting Heart Rate",
                        existingDisplay = "${existing.restingHeartRate} bpm",
                        hcDisplay = "$bpm bpm"
                    )
                )
            } else {
                bodyMetricRepository.saveForDate(buildInput(today, existing, restingHeartRate = bpm))
                _uiState.value = _uiState.value.copy(hcImportMessage = "Resting HR imported from Health Connect.")
            }
        }
    }

    fun confirmImport() {
        val today = LocalDate.now().toString()
        val existing = pendingExisting
        val field = pendingField
        viewModelScope.launch {
            val msg: String? = when (field) {
                PendingField.STEPS -> pendingSteps?.let {
                    bodyMetricRepository.saveForDate(buildInput(today, existing, stepCount = it.toInt()))
                    "Steps imported from Health Connect."
                }
                PendingField.SLEEP -> pendingSleep?.let {
                    bodyMetricRepository.saveForDate(buildInput(today, existing, sleepHours = it))
                    "Sleep imported from Health Connect."
                }
                PendingField.WEIGHT -> pendingWeight?.let {
                    bodyMetricRepository.saveForDate(buildInput(today, existing, bodyWeight = it))
                    "Weight imported from Health Connect."
                }
                PendingField.RESTING_HR -> pendingHr?.let {
                    bodyMetricRepository.saveForDate(buildInput(today, existing, restingHeartRate = it))
                    "Resting HR imported from Health Connect."
                }
                null -> null
            }
            clearPending()
            _uiState.value = _uiState.value.copy(
                hcImportConflict = null,
                hcImportMessage = msg ?: _uiState.value.hcImportMessage
            )
        }
    }

    fun cancelImport() {
        clearPending()
        _uiState.value = _uiState.value.copy(hcImportConflict = null)
    }

    // ── Export / Backup ─────────────────────────────────────────────────────

    fun exportBackup(context: Context) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(exportStatus = "Exporting...")
            runCatching {
                dataExportService.exportJson(context.applicationContext)
            }.onSuccess { uri ->
                _uiState.value = _uiState.value.copy(pendingShareUri = uri, exportStatus = null)
            }.onFailure {
                _uiState.value = _uiState.value.copy(exportStatus = "Export failed. Please try again.")
            }
        }
    }

    fun clearShareUri() {
        _uiState.value = _uiState.value.copy(pendingShareUri = null)
    }

    // ── Restore Backup ──────────────────────────────────────────────────────

    private var pendingRestoreUri: Uri? = null

    fun onRestoreFileSelected(context: Context, uri: Uri) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(restoreStatus = "Validating backup...")
            when (val result = dataRestoreService.validate(context, uri)) {
                is DataRestoreService.RestoreResult.Success -> {
                    pendingRestoreUri = uri
                    _uiState.value = _uiState.value.copy(showRestoreDialog = true, restoreStatus = null)
                }
                is DataRestoreService.RestoreResult.Invalid -> {
                    _uiState.value = _uiState.value.copy(restoreStatus = result.reason)
                }
                is DataRestoreService.RestoreResult.Error -> {
                    _uiState.value = _uiState.value.copy(restoreStatus = result.message)
                }
            }
        }
    }

    fun confirmRestore(context: Context) {
        val uri = pendingRestoreUri ?: return
        pendingRestoreUri = null
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(showRestoreDialog = false, restoreStatus = "Restoring...")
            when (val result = dataRestoreService.restore(context, uri)) {
                is DataRestoreService.RestoreResult.Success ->
                    _uiState.value = _uiState.value.copy(restoreStatus = "Backup restored successfully.")
                is DataRestoreService.RestoreResult.Error ->
                    _uiState.value = _uiState.value.copy(restoreStatus = result.message)
                is DataRestoreService.RestoreResult.Invalid ->
                    _uiState.value = _uiState.value.copy(restoreStatus = result.reason)
            }
        }
    }

    fun cancelRestore() {
        pendingRestoreUri = null
        _uiState.value = _uiState.value.copy(showRestoreDialog = false, restoreStatus = null)
    }

    // ── Profile Photo ────────────────────────────────────────────────────────

    fun setInitialPhotoPath(path: String?) {
        _uiState.value = _uiState.value.copy(profilePhotoPath = path)
    }

    fun saveProfilePhoto(context: Context, sourceUri: Uri) {
        val dir = File(context.filesDir, "profile").also { it.mkdirs() }
        val dest = File(dir, "photo.jpg")
        runCatching {
            context.contentResolver.openInputStream(sourceUri)?.use { input ->
                FileOutputStream(dest).use { output -> input.copyTo(output) }
            }
        }
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit().putString("profile_photo_path", dest.absolutePath).apply()
        _uiState.value = _uiState.value.copy(profilePhotoPath = dest.absolutePath)
    }

    fun removeProfilePhoto(context: Context) {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit().remove("profile_photo_path").apply()
        File(context.filesDir, "profile/photo.jpg").delete()
        _uiState.value = _uiState.value.copy(profilePhotoPath = null)
    }

    private fun clearPending() {
        pendingField = null
        pendingSteps = null
        pendingSleep = null
        pendingWeight = null
        pendingHr = null
        pendingExisting = null
    }

    // Builds a full BodyMetricLogInput, preserving all existing fields except the ones explicitly overridden.
    private fun buildInput(
        today: String,
        existing: BodyMetricLog?,
        stepCount: Int? = existing?.stepCount,
        sleepHours: Double? = existing?.sleepHours,
        bodyWeight: Double? = existing?.bodyWeight,
        restingHeartRate: Int? = existing?.restingHeartRate
    ) = BodyMetricLogInput(
        date = today,
        heightInches = existing?.heightInches,
        bodyWeight = bodyWeight,
        bodyFatPercentage = existing?.bodyFatPercentage,
        calculatedBodyFatPercent = existing?.calculatedBodyFatPercent,
        manualBodyFatPercent = existing?.manualBodyFatPercent,
        isBodyFatOverridden = existing?.isBodyFatOverridden ?: false,
        waistMeasurement = existing?.waistMeasurement,
        neckMeasurement = existing?.neckMeasurement,
        chestMeasurement = existing?.chestMeasurement,
        armMeasurement = existing?.armMeasurement,
        sleepHours = sleepHours,
        energyLevel = existing?.energyLevel,
        stressLevel = existing?.stressLevel,
        sorenessLevel = existing?.sorenessLevel,
        restingHeartRate = restingHeartRate,
        stepCount = stepCount,
        notes = existing?.notes
    )
}

private fun Double?.toHeightParts(): Pair<String, String> {
    if (this == null || this <= 0.0) return "" to ""
    val total = toInt()
    return (total / 12).toString() to (total % 12).toString()
}

private fun Double?.cleanString(): String {
    if (this == null) return ""
    return if (this % 1.0 == 0.0) toInt().toString() else String.format("%.1f", this)
}

class ProfileViewModelFactory(
    private val userProfileRepository: UserProfileRepository,
    private val macroTargetRepository: MacroTargetRepository,
    private val bodyMetricRepository: BodyMetricRepository,
    private val dataExportService: DataExportService,
    private val dataRestoreService: DataRestoreService
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(
                userProfileRepository, macroTargetRepository, bodyMetricRepository,
                dataExportService, dataRestoreService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
