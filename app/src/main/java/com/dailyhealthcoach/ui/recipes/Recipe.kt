package com.dailyhealthcoach.ui.recipes

data class Recipe(
    val id: String,
    val name: String,
    val mealType: String,
    val tags: List<String>,
    val ingredients: List<String>,
    val calories: Int,
    val proteinGrams: Double,
    val carbGrams: Double,
    val fatGrams: Double,
    val fiberGrams: Double?,
    val prepMinutes: Int,
    val instructions: List<String>
)
