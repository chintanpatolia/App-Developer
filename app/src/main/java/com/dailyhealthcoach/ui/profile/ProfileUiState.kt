package com.dailyhealthcoach.ui.profile

import android.net.Uri

data class HcImportConflict(
    val fieldLabel: String,
    val existingDisplay: String,
    val hcDisplay: String
)

data class ProfileUiState(
    val name: String = "",
    val age: String = "",
    val sex: String = "Male",
    val heightFeet: String = "",
    val heightInches: String = "",
    val weightGoal: String = "",
    val bodyFatGoal: String = "",
    val proteinMin: String = "170",
    val proteinMax: String = "200",
    val stepMin: String = "8000",
    val stepMax: String = "10000",
    val sleepTarget: String = "7",
    val strengthTarget: String = "3",
    val nutritionGoal: String = "Maintain",
    val dietPreferences: List<String> = listOf("Vegetarian"),
    val foodRestrictions: List<String> = emptyList(),
    val workoutGoals: List<String> = listOf("General Fitness"),
    val activityLevel: String = "Moderately Active",
    val calorieTarget: String = "",
    val carbTarget: String = "",
    val fatTarget: String = "",
    val fiberTarget: String = "",
    val calculatedCalories: Int? = null,
    val calculatedProteinMin: Int? = null,
    val calculatedProteinMax: Int? = null,
    val calculatedCarbs: Int? = null,
    val calculatedFat: Int? = null,
    val calculatedFiber: Int? = null,
    val macroCalculationMessage: String? = null,
    val error: String? = null,
    val savedSuccess: Boolean = false,
    val hcImportConflict: HcImportConflict? = null,
    val hcImportMessage: String? = null,
    val pendingShareUri: Uri? = null,
    val exportStatus: String? = null,
    val showRestoreDialog: Boolean = false,
    val restoreStatus: String? = null,
    val profilePhotoPath: String? = null
)
