package com.dailyhealthcoach.ui.recipes

data class Recipe(
    val id: String,
    val name: String,
    val description: String = "",
    val mealType: String,
    val tags: List<String>,
    val collection: List<String> = emptyList(),   // "Metabolic Reset", "Anti-Inflammatory"
    val ingredients: List<String>,
    val calories: Int,
    val proteinGrams: Double,
    val carbGrams: Double,
    val fatGrams: Double,
    val fiberGrams: Double?,
    val prepMinutes: Int,
    val cookMinutes: Int = 0,
    val instructions: List<String>,
    val storageNotes: String? = null,
    val mealPrepNotes: String? = null,
    val metabolicResetScore: Int = 0,         // 1–10
    val antiInflammatoryScore: Int = 0,       // 1–10
    val isGlucoseConscious: Boolean = false
)
