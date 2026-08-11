package com.dailyhealthcoach.ui.recipes

data class Recipe(
    val id: String,
    val name: String,
    val description: String = "",

    // "Breakfast" | "Lunch" | "Dinner" | "Snack" | "Protein Booster"
    val mealType: String,

    // Diet style tags: "Vegetarian" | "Vegan" | "Pescatarian" | "Mediterranean" | "Omnivore" | "Flexitarian"
    val tags: List<String>,

    // Primary protein source — empty for plant-mixed or no dominant protein
    // "Chicken" | "Turkey" | "Beef" | "Fish" | "Seafood" | "Tofu" | "Paneer" |
    // "Tempeh" | "Eggs" | "Lentils" | "Greek Yogurt" | "Cottage Cheese" | "Soy Chunks" | ""
    val proteinSource: String = "",

    // Explicit restriction tags — replaces heuristic ingredient scanning
    // "Dairy Free" | "Gluten Free" | "Nut Free" | "Soy Free" | "Egg Free"
    val restrictions: List<String> = emptyList(),

    // Program collections
    // "Metabolic Reset" | "Anti-Inflammatory" | "Heart Healthy" | "Fat Loss" | "Muscle Gain"
    val collection: List<String> = emptyList(),

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

    // Health program scores (1–10, higher = more beneficial)
    val metabolicResetScore: Int = 0,
    val antiInflammatoryScore: Int = 0,
    val insulinResistanceScore: Int = 0,
    val womensHealthScore: Int = 0,

    // Glucose impact (1 = minimal spike, 10 = high spike — lower is better)
    // Replaces isGlucoseConscious: Boolean — use glucoseImpactScore <= 3 wherever
    // isGlucoseConscious = true was used previously
    val glucoseImpactScore: Int = 0,
)
