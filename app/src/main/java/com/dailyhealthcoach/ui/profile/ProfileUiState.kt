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
    val error: String? = null,
    val savedSuccess: Boolean = false,
    val hcImportConflict: HcImportConflict? = null,
    val hcImportMessage: String? = null,
    val pendingShareUri: Uri? = null,
    val exportStatus: String? = null,
    val showRestoreDialog: Boolean = false,
    val restoreStatus: String? = null
)
