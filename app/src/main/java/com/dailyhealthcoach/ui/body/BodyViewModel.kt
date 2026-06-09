package com.dailyhealthcoach.ui.body

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.BodyMetricLogInput
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import com.dailyhealthcoach.domain.repository.UserProfileRepository
import java.time.LocalDate
import kotlin.math.log10
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BodyViewModel(
    private val bodyMetricRepository: BodyMetricRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    private val today = LocalDate.now().toString()
    private val formState = MutableStateFlow(BodyMetricFormUiState())

    val uiState: StateFlow<BodyUiState> = combine(
        bodyMetricRepository.observeForDate(today),
        bodyMetricRepository.observeAll(),
        userProfileRepository.observeUserProfile(),
        formState
    ) { todayLog, allLogs, userProfile, form ->
        val profileHeight = userProfile?.heightInches?.takeIf { it in 36.0..96.0 }
        val displayForm = if (form.isDirty) {
            form.withBodyFatEstimate(profileHeight)
        } else {
            (todayLog?.toFormUiState(profileHeight) ?: form.withHeight(profileHeight)).withBodyFatEstimate(profileHeight)
        }
        BodyUiState(
            today = todayLog?.toUiState(),
            form = displayForm,
            recentLogs = allLogs.take(7).map { it.toUiState() }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BodyUiState()
    )

    fun updateForm(transform: (BodyMetricFormUiState) -> BodyMetricFormUiState) {
        formState.update { transform(it).copy(isDirty = true) }
    }

    fun saveMetrics() {
        val form = uiState.value.form
        val height = form.totalHeightInchesOrNull()
        val waist = form.waistMeasurement.toPositiveDoubleOrNull()
        val neck = form.neckMeasurement.toPositiveDoubleOrNull()
        val calculated = calculateBodyFatPercent(height = height, waist = waist, neck = neck)
        val manual = form.bodyFatPercentage.toManualBodyFatOrNull()
        val finalBodyFat = if (form.isBodyFatOverridden) manual else calculated
        viewModelScope.launch {
            bodyMetricRepository.saveForDate(
                BodyMetricLogInput(
                    date = today,
                    heightInches = height,
                    bodyWeight = form.bodyWeight.toPositiveDoubleOrNull(),
                    bodyFatPercentage = finalBodyFat,
                    calculatedBodyFatPercent = calculated,
                    manualBodyFatPercent = if (form.isBodyFatOverridden) manual else null,
                    isBodyFatOverridden = form.isBodyFatOverridden,
                    waistMeasurement = form.waistMeasurement.toPositiveDoubleOrNull(),
                    neckMeasurement = form.neckMeasurement.toPositiveDoubleOrNull(),
                    chestMeasurement = form.chestMeasurement.toPositiveDoubleOrNull(),
                    armMeasurement = form.armMeasurement.toPositiveDoubleOrNull(),
                    sleepHours = form.sleepHours.toRangeDoubleOrNull(0.0, 24.0),
                    energyLevel = form.energyLevel.toLevelOrNull(),
                    stressLevel = form.stressLevel.toLevelOrNull(),
                    sorenessLevel = form.sorenessLevel.toLevelOrNull(),
                    restingHeartRate = form.restingHeartRate.toPositiveIntOrNull(),
                    stepCount = form.stepCount.toPositiveIntOrNull(),
                    notes = form.notes.ifBlank { null }
                )
            )
            formState.value = form.copy(isDirty = false)
        }
    }
}

private fun BodyMetricLog.toUiState(): BodyMetricLogUiState {
    return BodyMetricLogUiState(
        date = date,
        heightInches = heightInches,
        bodyWeight = bodyWeight,
        bodyFatPercentage = bodyFatPercentage,
        calculatedBodyFatPercent = calculatedBodyFatPercent,
        manualBodyFatPercent = manualBodyFatPercent,
        isBodyFatOverridden = isBodyFatOverridden,
        waistMeasurement = waistMeasurement,
        neckMeasurement = neckMeasurement,
        chestMeasurement = chestMeasurement,
        armMeasurement = armMeasurement,
        sleepHours = sleepHours,
        energyLevel = energyLevel,
        stressLevel = stressLevel,
        sorenessLevel = sorenessLevel,
        restingHeartRate = restingHeartRate,
        stepCount = stepCount,
        notes = notes
    )
}

private fun BodyMetricLog.toFormUiState(profileHeight: Double?): BodyMetricFormUiState {
    val height = profileHeight ?: heightInches?.takeIf { it in 36.0..96.0 }
    val heightParts = height.toHeightParts()
    return BodyMetricFormUiState(
        heightFeet = heightParts.first,
        heightInches = heightParts.second,
        bodyWeight = bodyWeight.clean(),
        bodyFatPercentage = (manualBodyFatPercent ?: bodyFatPercentage).clean(),
        calculatedBodyFatPercent = calculatedBodyFatPercent,
        isBodyFatOverridden = isBodyFatOverridden,
        waistMeasurement = waistMeasurement.clean(),
        neckMeasurement = neckMeasurement.clean(),
        chestMeasurement = chestMeasurement.clean(),
        armMeasurement = armMeasurement.clean(),
        sleepHours = sleepHours.clean(),
        energyLevel = energyLevel?.toString().orEmpty(),
        stressLevel = stressLevel?.toString().orEmpty(),
        sorenessLevel = sorenessLevel?.toString().orEmpty(),
        restingHeartRate = restingHeartRate?.toString().orEmpty(),
        stepCount = stepCount?.toString().orEmpty(),
        notes = notes.orEmpty()
    )
}

private fun BodyMetricFormUiState.withHeight(profileHeight: Double?): BodyMetricFormUiState {
    if (profileHeight == null || profileHeight !in 36.0..96.0 || heightFeet.isNotBlank() || heightInches.isNotBlank()) return this
    val heightParts = profileHeight.toHeightParts()
    return copy(heightFeet = heightParts.first, heightInches = heightParts.second)
}

private fun BodyMetricFormUiState.withBodyFatEstimate(profileHeight: Double?): BodyMetricFormUiState {
    val height = profileHeight?.takeIf { it in 36.0..96.0 } ?: totalHeightInchesOrNull()
    val waist = waistMeasurement.toPositiveDoubleOrNull()
    val neck = neckMeasurement.toPositiveDoubleOrNull()
    val calculated = calculateBodyFatPercent(height = height, waist = waist, neck = neck)
    val manual = bodyFatPercentage.toManualBodyFatOrNull()
    val helper = when {
        isBodyFatOverridden && bodyFatPercentage.isNotBlank() && manual == null -> "Enter a manual body fat value between 3 and 75."
        isBodyFatOverridden -> "Manual value overrides calculated estimate."
        !hasValidHeight() && profileHeight == null -> "Enter valid height, waist, and neck measurements to estimate body fat."
        waist == null || waist !in 20.0..80.0 -> "Enter valid height, waist, and neck measurements to estimate body fat."
        neck == null || neck !in 8.0..30.0 -> "Enter valid height, waist, and neck measurements to estimate body fat."
        waist <= neck -> "Waist must be greater than neck to estimate body fat."
        calculated == null -> "Enter valid height, waist, and neck measurements to estimate body fat."
        else -> "Estimated from height, waist, and neck measurements."
    }
    val heightParts = (profileHeight?.takeIf { it in 36.0..96.0 } ?: totalHeightInchesOrNull()).toHeightParts()
    return copy(
        heightFeet = if (profileHeight != null) heightParts.first else heightFeet,
        heightInches = if (profileHeight != null) heightParts.second else heightInches,
        calculatedBodyFatPercent = calculated,
        bodyFatHelperText = helper
    )
}

private fun String.toPositiveDoubleOrNull(): Double? {
    return toDoubleOrNull()?.takeIf { it >= 0.0 }
}

private fun String.toPercentOrNull(): Double? {
    return toRangeDoubleOrNull(0.0, 100.0)
}

private fun String.toManualBodyFatOrNull(): Double? {
    return toRangeDoubleOrNull(3.0, 75.0)
}

private fun String.toRangeDoubleOrNull(min: Double, max: Double): Double? {
    return toDoubleOrNull()?.takeIf { it in min..max }
}

private fun String.toPositiveIntOrNull(): Int? {
    return toIntOrNull()?.takeIf { it >= 0 }
}

private fun String.toLevelOrNull(): Int? {
    return toIntOrNull()?.coerceIn(1, 10)
}

private fun Double?.clean(): String {
    if (this == null) return ""
    return if (this % 1.0 == 0.0) toInt().toString() else String.format("%.1f", this)
}

private fun BodyMetricFormUiState.totalHeightInchesOrNull(): Double? {
    val feet = heightFeet.toIntOrNull() ?: return null
    val inches = heightInches.toIntOrNull() ?: 0
    if (feet !in 3..8 || inches !in 0..11) return null
    val total = feet * 12 + inches
    return total.takeIf { it in 36..96 }?.toDouble()
}

private fun BodyMetricFormUiState.hasValidHeight(): Boolean {
    return totalHeightInchesOrNull() != null
}

private fun Double?.toHeightParts(): Pair<String, String> {
    if (this == null || this <= 0.0) return "" to ""
    val total = toInt()
    return (total / 12).toString() to (total % 12).toString()
}

private fun calculateBodyFatPercent(height: Double?, waist: Double?, neck: Double?): Double? {
    if (height == null || height !in 36.0..96.0) return null
    if (waist == null || waist !in 20.0..80.0) return null
    if (neck == null || neck !in 8.0..30.0) return null
    if (waist <= neck) return null
    val estimate = 86.010 * log10(waist - neck) - 70.041 * log10(height) + 36.76
    return estimate.takeIf { it in 3.0..75.0 }
}

class BodyViewModelFactory(
    private val bodyMetricRepository: BodyMetricRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BodyViewModel::class.java)) {
            return BodyViewModel(
                bodyMetricRepository = bodyMetricRepository,
                userProfileRepository = userProfileRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
