package com.dailyhealthcoach.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.UserProfile
import com.dailyhealthcoach.domain.repository.MacroTargetRepository
import com.dailyhealthcoach.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userProfileRepository: UserProfileRepository,
    private val macroTargetRepository: MacroTargetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var existingBirthDate: String? = null
    private var existingBedtime: String? = null

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
                strengthTarget = profile?.strengthTrainingDaysPerWeek?.toString() ?: "3"
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
                    strengthTrainingDaysPerWeek = strengthTarget
                )
            )
            macroTargetRepository.saveTarget(
                proteinMin = proteinMin ?: 170,
                proteinMax = proteinMax ?: 200
            )
            _uiState.value = _uiState.value.copy(savedSuccess = true, error = null)
        }
    }
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
    private val macroTargetRepository: MacroTargetRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userProfileRepository, macroTargetRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
