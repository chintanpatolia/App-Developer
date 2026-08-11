package com.dailyhealthcoach.domain.nutrition

data class MacroResult(
    val calories: Int,
    val proteinMinGrams: Int,
    val proteinMaxGrams: Int,
    val carbGrams: Int,
    val fatGrams: Int,
    val fiberGrams: Int
)

object MacroCalculatorEngine {

    val activityLevels = listOf(
        "Sedentary", "Lightly Active", "Moderately Active", "Very Active", "Extra Active"
    )

    val nutritionGoals = listOf(
        "Fat Loss", "Maintain", "Muscle Gain", "Metabolic Reset",
        "Anti-Inflammatory", "General Health", "Insulin Resistance / Prediabetes"
    )

    private val ACTIVITY_MULTIPLIERS = mapOf(
        "Sedentary" to 1.2,
        "Lightly Active" to 1.375,
        "Moderately Active" to 1.55,
        "Very Active" to 1.725,
        "Extra Active" to 1.9
    )

    // Katch-McArdle if body fat % available, else Mifflin-St Jeor. Returns null if insufficient data.
    fun calculateBmr(
        sex: String?,
        ageYears: Int?,
        heightInches: Double?,
        weightLbs: Double?,
        bodyFatPercent: Double?
    ): Double? {
        if (weightLbs == null || weightLbs <= 0) return null
        val weightKg = weightLbs * 0.453592
        if (bodyFatPercent != null && bodyFatPercent > 0) {
            val lbmKg = weightKg * (1.0 - bodyFatPercent / 100.0)
            return 370.0 + 21.6 * lbmKg
        }
        if (heightInches == null || ageYears == null) return null
        val heightCm = heightInches * 2.54
        val base = 10.0 * weightKg + 6.25 * heightCm - 5.0 * ageYears
        return if (sex?.equals("Female", ignoreCase = true) == true) base - 161 else base + 5
    }

    fun calculateTdee(bmr: Double, activityLevel: String): Double =
        bmr * (ACTIVITY_MULTIPLIERS[activityLevel] ?: 1.55)

    fun calculateMacros(tdee: Double, nutritionGoal: String?, weightLbs: Double?): MacroResult {
        val goal = nutritionGoal?.lowercase().orEmpty()
        val lbs = weightLbs ?: 150.0

        return when {
            goal.contains("fat loss") || goal.contains("lose") || goal.contains("weight loss") -> {
                val cal = (tdee - 500).coerceAtLeast(1200.0).toInt()
                val protMax = (lbs * 1.3).toInt()
                val protMin = (lbs * 1.0).toInt()
                val carb = ((cal.toDouble() - protMax * 4) * 0.40 / 4).toInt().coerceAtLeast(80)
                val fat = ((cal - protMax * 4 - carb * 4).toDouble() / 9).toInt().coerceAtLeast(40)
                MacroResult(cal, protMin, protMax, carb, fat, 28)
            }
            goal.contains("muscle") || goal.contains("gain") || goal.contains("bulk") -> {
                val cal = (tdee + 300).toInt()
                val protMin = (lbs * 0.9).toInt()
                val protMax = (lbs * 1.2).toInt()
                val carb = (cal * 0.45 / 4).toInt()
                val fat = (cal * 0.25 / 9).toInt()
                MacroResult(cal, protMin, protMax, carb, fat, 30)
            }
            goal.contains("metabolic") || goal.contains("prediabetes") ||
            goal.contains("insulin") || goal.contains("blood sugar") || goal.contains("glucose") -> {
                val cal = tdee.toInt()
                val protMin = (lbs * 0.8).toInt()
                val protMax = (lbs * 1.1).toInt()
                val carb = 130
                val fat = ((cal - protMax * 4 - carb * 4).toDouble() / 9).toInt().coerceAtLeast(40)
                MacroResult(cal, protMin, protMax, carb, fat, 35)
            }
            goal.contains("inflam") || goal.contains("anti") -> {
                val cal = tdee.toInt()
                val protMin = (lbs * 0.7).toInt()
                val protMax = (lbs * 1.0).toInt()
                val carb = (cal * 0.40 / 4).toInt()
                val fat = (cal * 0.35 / 9).toInt()
                MacroResult(cal, protMin, protMax, carb, fat, 30)
            }
            else -> {
                val cal = tdee.toInt()
                val protMin = (lbs * 0.7).toInt()
                val protMax = (lbs * 1.0).toInt()
                val carb = (cal * 0.45 / 4).toInt()
                val fat = (cal * 0.30 / 9).toInt()
                MacroResult(cal, protMin, protMax, carb, fat, 25)
            }
        }
    }
}
