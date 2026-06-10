package com.dailyhealthcoach.ai

import com.dailyhealthcoach.domain.model.MealEstimate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AiFoodLoggingService {

    private data class NutrientProfile(
        val calories: Int,
        val protein: Double,
        val carbs: Double,
        val fat: Double,
        val fiber: Double,
        val isWhole: Boolean = true
    )

    // Per standard serving (1 unit / 100g / 1 cup as appropriate)
    private val knownFoods: Map<String, NutrientProfile> = mapOf(
        "greek yogurt"   to NutrientProfile(100, 10.0,  6.0,  0.7, 0.0),
        "protein shake"  to NutrientProfile(120, 25.0,  4.0,  2.0, 0.0, isWhole = false),
        "roti"           to NutrientProfile(100,  3.0, 18.0,  2.0, 2.0),
        "chapati"        to NutrientProfile(100,  3.0, 18.0,  2.0, 2.0),
        "paneer"         to NutrientProfile(265, 18.0,  3.0, 20.0, 0.0),
        "chicken"        to NutrientProfile(165, 31.0,  0.0,  3.6, 0.0),
        "yogurt"         to NutrientProfile(100,  5.0, 12.0,  2.5, 0.0),
        "banana"         to NutrientProfile( 89,  1.1, 23.0,  0.3, 2.6),
        "salad"          to NutrientProfile( 50,  2.0,  7.0,  2.0, 3.0),
        "bread"          to NutrientProfile( 80,  3.0, 15.0,  1.0, 1.0, isWhole = false),
        "whey"           to NutrientProfile(120, 25.0,  4.0,  2.0, 0.0, isWhole = false),
        "tofu"           to NutrientProfile(144, 17.0,  3.0,  9.0, 0.3),
        "oats"           to NutrientProfile(150,  5.0, 27.0,  3.0, 4.0),
        "rice"           to NutrientProfile(200,  4.0, 44.0,  0.5, 1.0),
        "chai"           to NutrientProfile( 60,  2.0,  9.0,  2.0, 0.0, isWhole = false),
        "dal"            to NutrientProfile(150,  9.0, 27.0,  0.5, 8.0),
        "egg"            to NutrientProfile( 72,  6.0,  0.0,  5.0, 0.0),
    )

    private val wordToNumber = mapOf(
        "a" to 1, "an" to 1, "one" to 1, "two" to 2, "three" to 3,
        "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
    )

    suspend fun parse(input: String): MealEstimate = withContext(Dispatchers.Default) {
        val lower = input.lowercase()
        val identified = mutableListOf<String>()
        var totalCal = 0
        var totalProtein = 0.0
        var totalCarbs = 0.0
        var totalFat = 0.0
        var totalFiber = 0.0
        var allWhole = true

        // Longer keys checked first to prevent "yogurt" stealing "greek yogurt" match
        val orderedFoods = knownFoods.entries.sortedByDescending { it.key.length }
        val accountedRanges = mutableListOf<IntRange>()

        for ((foodKey, profile) in orderedFoods) {
            var searchStart = 0
            while (true) {
                val idx = lower.indexOf(foodKey, searchStart)
                if (idx == -1) break
                val range = idx until (idx + foodKey.length)
                if (accountedRanges.any { it.first <= range.last && range.first <= it.last }) {
                    searchStart = idx + 1
                    continue
                }
                val qty = extractQuantityBefore(lower, idx) ?: 1
                totalCal += profile.calories * qty
                totalProtein += profile.protein * qty
                totalCarbs += profile.carbs * qty
                totalFat += profile.fat * qty
                totalFiber += profile.fiber * qty
                if (!profile.isWhole) allWhole = false
                identified.add(if (qty > 1) "$qty $foodKey" else foodKey)
                accountedRanges.add(range)
                searchStart = idx + foodKey.length
            }
        }

        val mealName = if (identified.isNotEmpty())
            identified.joinToString(", ").replaceFirstChar { it.uppercase() }
        else
            input.trim().take(60)

        val confidence = when {
            identified.size >= 3 -> "Medium"
            identified.size >= 1 -> "Medium"
            else -> "Low"
        }

        val notes = if (identified.isEmpty())
            "Food not recognized. Values are zero — please enter manually."
        else
            "Estimate based on common portions. Review before saving."

        MealEstimate(
            mealName = mealName,
            calories = totalCal,
            proteinGrams = totalProtein,
            carbGrams = totalCarbs,
            fatGrams = totalFat,
            fiberGrams = totalFiber,
            confidence = confidence,
            notes = notes,
            isWholeFoodBased = allWhole
        )
    }

    private fun extractQuantityBefore(text: String, foodIdx: Int): Int? {
        if (foodIdx == 0) return null
        val before = text.substring(0, foodIdx).trimEnd()
        val digitMatch = Regex("""(\d+)\s*$""").find(before)
        if (digitMatch != null) return digitMatch.groupValues[1].toIntOrNull()
        val lastWord = before.split(Regex("""[\s,]+""")).lastOrNull { it.isNotBlank() }
        return wordToNumber[lastWord]
    }
}
