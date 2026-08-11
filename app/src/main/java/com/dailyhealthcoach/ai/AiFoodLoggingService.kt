package com.dailyhealthcoach.ai

import com.dailyhealthcoach.domain.model.MealEstimate
import com.dailyhealthcoach.domain.model.ParsedFoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class AiFoodLoggingService {

    private data class NutrientProfile(
        val calories: Int,
        val protein: Double,
        val carbs: Double,
        val fat: Double,
        val fiber: Double,
        val aliases: List<String>,
        val gramsPerServing: Double? = null,
        val mlPerServing: Double? = null,
        val isWhole: Boolean = true
    )

    private data class FoodMention(
        val start: Int,
        val foodName: String,
        val quantity: Double,
        val unit: String?,
        val confidence: String,
        val profile: NutrientProfile
    )

    private data class QuantityResult(
        val multiplier: Double,
        val unit: String?,
        val confidence: String
    )

    private val knownFoods: Map<String, NutrientProfile> = mapOf(
        "greek yogurt" to NutrientProfile(100, 10.0, 6.0, 0.7, 0.0, aliases = listOf("greek yogurt"), gramsPerServing = 170.0, mlPerServing = 170.0),
        "protein shake" to NutrientProfile(120, 25.0, 4.0, 2.0, 0.0, aliases = listOf("protein shake", "shake"), mlPerServing = 350.0, isWhole = false),
        "roti" to NutrientProfile(100, 3.0, 18.0, 2.0, 2.0, aliases = listOf("roti", "rotis", "chapati", "chapatis"), gramsPerServing = 40.0),
        "paneer" to NutrientProfile(265, 18.0, 3.0, 20.0, 0.0, aliases = listOf("paneer"), gramsPerServing = 100.0),
        "chicken" to NutrientProfile(165, 31.0, 0.0, 3.6, 0.0, aliases = listOf("chicken"), gramsPerServing = 100.0),
        "milk" to NutrientProfile(122, 8.0, 12.0, 4.8, 0.0, aliases = listOf("milk"), mlPerServing = 250.0),
        "yogurt" to NutrientProfile(100, 5.0, 12.0, 2.5, 0.0, aliases = listOf("yogurt"), gramsPerServing = 170.0, mlPerServing = 170.0),
        "banana" to NutrientProfile(89, 1.1, 23.0, 0.3, 2.6, aliases = listOf("banana", "bananas"), gramsPerServing = 118.0),
        "salad" to NutrientProfile(50, 2.0, 7.0, 2.0, 3.0, aliases = listOf("salad"), gramsPerServing = 100.0),
        "bread" to NutrientProfile(80, 3.0, 15.0, 1.0, 1.0, aliases = listOf("bread"), gramsPerServing = 30.0, isWhole = false),
        "whey" to NutrientProfile(120, 25.0, 4.0, 2.0, 0.0, aliases = listOf("whey"), gramsPerServing = 30.0, isWhole = false),
        "peanut butter" to NutrientProfile(95, 3.5, 3.5, 8.0, 1.0, aliases = listOf("peanut butter"), gramsPerServing = 16.0, isWhole = false),
        "tofu" to NutrientProfile(144, 17.0, 3.0, 9.0, 0.3, aliases = listOf("tofu"), gramsPerServing = 100.0),
        "oats" to NutrientProfile(150, 5.0, 27.0, 3.0, 4.0, aliases = listOf("oats", "oatmeal"), gramsPerServing = 40.0),
        "rice" to NutrientProfile(200, 4.0, 44.0, 0.5, 1.0, aliases = listOf("rice"), gramsPerServing = 158.0),
        "chai" to NutrientProfile(60, 2.0, 9.0, 2.0, 0.0, aliases = listOf("chai", "tea"), mlPerServing = 180.0, isWhole = false),
        "dal" to NutrientProfile(150, 9.0, 27.0, 0.5, 8.0, aliases = listOf("dal", "daal"), gramsPerServing = 240.0, mlPerServing = 240.0),
        "egg" to NutrientProfile(72, 6.0, 0.0, 5.0, 0.0, aliases = listOf("egg", "eggs"), gramsPerServing = 50.0)
    )

    private val wordToNumber = mapOf(
        "a" to 1.0,
        "an" to 1.0,
        "one" to 1.0,
        "two" to 2.0,
        "three" to 3.0,
        "four" to 4.0,
        "five" to 5.0,
        "six" to 6.0,
        "seven" to 7.0,
        "eight" to 8.0,
        "nine" to 9.0,
        "ten" to 10.0,
        "half" to 0.5,
        "quarter" to 0.25
    )

    private val householdUnits = mapOf(
        "cup" to 1.0,
        "cups" to 1.0,
        "tablespoon" to 1.0,
        "tablespoons" to 1.0,
        "tbsp" to 1.0,
        "teaspoon" to 1.0 / 3.0,
        "teaspoons" to 1.0 / 3.0,
        "tsp" to 1.0 / 3.0,
        "pinch" to 0.05,
        "pinches" to 0.05
    )

    private val servingUnits = mapOf(
        "serving" to 1.0,
        "servings" to 1.0,
        "handful" to 0.5,
        "handfuls" to 0.5,
        "bowl" to 1.5,
        "bowls" to 1.5,
        "plate" to 2.0,
        "plates" to 2.0,
        "slice" to 1.0,
        "slices" to 1.0,
        "piece" to 1.0,
        "pieces" to 1.0,
        "scoop" to 1.0,
        "scoops" to 1.0,
        "glass" to 1.0,
        "glasses" to 1.0,
        "mug" to 1.0,
        "mugs" to 1.0
    )

    private val relativeSizes = mapOf(
        "small" to 0.5,
        "medium" to 1.0,
        "large" to 1.5,
        "extra large" to 2.0
    )

    suspend fun parse(input: String): MealEstimate = withContext(Dispatchers.Default) {
        val lower = input.lowercase()
        val mentions = findFoodMentions(lower)
        var totalCal = 0.0
        var totalProtein = 0.0
        var totalCarbs = 0.0
        var totalFat = 0.0
        var totalFiber = 0.0
        var allWhole = true

        val identified = mentions.sortedBy { it.start }.map { mention ->
            val profile = mention.profile
            totalCal += profile.calories * mention.quantity
            totalProtein += profile.protein * mention.quantity
            totalCarbs += profile.carbs * mention.quantity
            totalFat += profile.fat * mention.quantity
            totalFiber += profile.fiber * mention.quantity
            if (!profile.isWhole) allWhole = false
            formatMealNameItem(mention)
        }

        val confidence = when {
            mentions.isEmpty() -> "Low"
            mentions.any { it.confidence == "Low" } -> "Low"
            mentions.all { it.confidence == "High" } -> "High"
            else -> "Medium"
        }

        val estimatedGlucoseResponse = estimateGlucoseResponse(
            carbs = totalCarbs,
            fiber = totalFiber,
            protein = totalProtein,
            fat = totalFat,
            hasProcessedFood = mentions.any { !it.profile.isWhole }
        )

        val notes = if (mentions.isEmpty()) {
            "Food not recognized. Values are zero - please enter manually."
        } else {
            "Estimated glucose response: $estimatedGlucoseResponse. Estimate based on recognized foods and common serving conversions. Review before saving."
        }

        MealEstimate(
            mealName = identified.ifEmpty { listOf(input.trim().take(60)) }.joinToString(", "),
            calories = totalCal.roundToInt(),
            proteinGrams = totalProtein,
            carbGrams = totalCarbs,
            fatGrams = totalFat,
            fiberGrams = totalFiber,
            confidence = confidence,
            notes = notes,
            isWholeFoodBased = allWhole,
            parsedItems = mentions.sortedBy { it.start }.map {
                ParsedFoodItem(
                    foodName = it.foodName,
                    quantity = it.quantity,
                    unit = it.unit,
                    confidence = it.confidence
                )
            },
            estimatedGlucoseResponse = estimatedGlucoseResponse
        )
    }

    private fun estimateGlucoseResponse(
        carbs: Double,
        fiber: Double,
        protein: Double,
        fat: Double,
        hasProcessedFood: Boolean
    ): String {
        if (carbs <= 0.0) return "Low"
        val netCarbs = (carbs - fiber).coerceAtLeast(0.0)
        val buffer = protein + fiber + (fat * 0.5)
        return when {
            netCarbs >= 60.0 && buffer < 35.0 -> "High"
            netCarbs >= 45.0 && hasProcessedFood -> "High"
            netCarbs >= 25.0 && buffer < 45.0 -> "Moderate"
            netCarbs >= 40.0 -> "Moderate"
            else -> "Low"
        }
    }

    private fun findFoodMentions(text: String): List<FoodMention> {
        val aliases = knownFoods.flatMap { (foodName, profile) ->
            profile.aliases.map { alias -> Triple(alias, foodName, profile) }
        }.sortedByDescending { it.first.length }
        val mentions = mutableListOf<FoodMention>()
        val accountedRanges = mutableListOf<IntRange>()

        for ((alias, foodName, profile) in aliases) {
            val regex = Regex("""(?<![a-z])${Regex.escape(alias)}(?![a-z])""")
            for (match in regex.findAll(text)) {
                val range = match.range
                if (accountedRanges.any { it.first <= range.last && range.first <= it.last }) continue
                val quantity = extractQuantityBefore(text, match.range.first, profile)
                mentions.add(
                    FoodMention(
                        start = match.range.first,
                        foodName = foodName,
                        quantity = quantity.multiplier,
                        unit = quantity.unit,
                        confidence = quantity.confidence,
                        profile = profile
                    )
                )
                accountedRanges.add(range)
            }
        }
        return mentions
    }

    private fun extractQuantityBefore(text: String, foodIdx: Int, profile: NutrientProfile): QuantityResult {
        val before = text.substring(0, foodIdx).trimEnd()
        val segmentStart = listOf(
            before.lastIndexOf(","),
            before.lastIndexOf(" and "),
            before.lastIndexOf(" with "),
            before.lastIndexOf(" plus ")
        ).maxOrNull() ?: -1
        val currentSegment = before.substring(segmentStart + 1)
        val tokens = currentSegment.split(Regex("""[\s,]+""")).filter { it.isNotBlank() && it != "of" }.takeLast(5)
        val phrase = tokens.joinToString(" ")
        val amount = parseAmount(tokens)
        val size = relativeSizes.entries.firstOrNull { phrase.contains(it.key) }

        val unit = tokens.asReversed().firstOrNull { it in metricUnits || it in imperialUnits || it in householdUnits || it in servingUnits }
        if (unit != null) {
            val count = amount ?: 1.0
            val multiplier = multiplierForUnit(unit, count, profile)
            val confidence = when {
                unit in metricUnits || unit in imperialUnits -> "High"
                normalizeUnit(unit) == "pinch" -> "Medium"
                count < 1.0 -> "Medium"
                amount != null -> "High"
                else -> "Medium"
            }
            return QuantityResult(
                multiplier = multiplier * (size?.value ?: 1.0),
                unit = normalizeUnit(unit),
                confidence = if (size != null) "Medium" else confidence
            )
        }

        if (size != null) {
            return QuantityResult(size.value * (amount ?: 1.0), null, "Medium")
        }

        if (amount != null) {
            return QuantityResult(amount, null, "High")
        }

        return QuantityResult(1.0, null, "Medium")
    }

    private fun parseAmount(tokens: List<String>): Double? {
        val joined = tokens.joinToString(" ")
        Regex("""(\d+(?:\.\d+)?)\s*$""").find(joined)?.let { return it.groupValues[1].toDoubleOrNull() }
        Regex("""(\d+(?:\.\d+)?)\s+[a-z]+$""").find(joined)?.let { return it.groupValues[1].toDoubleOrNull() }

        if ("half" in tokens) return 0.5
        if ("quarter" in tokens) return 0.25

        val lastNumber = tokens.asReversed().firstOrNull { it in wordToNumber }
        return wordToNumber[lastNumber]
    }

    private fun multiplierForUnit(unit: String, amount: Double, profile: NutrientProfile): Double {
        return when (unit) {
            in metricUnits -> metricMultiplier(unit, amount, profile)
            in imperialUnits -> imperialMultiplier(unit, amount, profile)
            in householdUnits -> amount * householdUnits.getValue(unit)
            in servingUnits -> amount * servingUnits.getValue(unit)
            else -> amount
        }
    }

    private fun metricMultiplier(unit: String, amount: Double, profile: NutrientProfile): Double {
        return when (unit) {
            "g", "gram", "grams" -> amount / (profile.gramsPerServing ?: 100.0)
            "kg" -> (amount * 1000.0) / (profile.gramsPerServing ?: 100.0)
            "ml" -> amount / (profile.mlPerServing ?: profile.gramsPerServing ?: 240.0)
            "litre", "liter", "litres", "liters" -> (amount * 1000.0) / (profile.mlPerServing ?: profile.gramsPerServing ?: 240.0)
            else -> amount
        }
    }

    private fun imperialMultiplier(unit: String, amount: Double, profile: NutrientProfile): Double {
        val grams = when (unit) {
            "oz", "ounce", "ounces" -> amount * 28.3495
            "lb", "pound", "pounds" -> amount * 453.592
            else -> amount
        }
        return grams / (profile.gramsPerServing ?: 100.0)
    }

    private fun normalizeUnit(unit: String): String {
        return when (unit) {
            "cups" -> "cup"
            "tablespoons", "tbsp" -> "tablespoon"
            "teaspoons", "tsp" -> "teaspoon"
            "pinches" -> "pinch"
            "servings" -> "serving"
            "handfuls" -> "handful"
            "bowls" -> "bowl"
            "plates" -> "plate"
            "slices" -> "slice"
            "pieces" -> "piece"
            "scoops" -> "scoop"
            "glasses" -> "glass"
            "mugs" -> "mug"
            "grams" -> "g"
            "gram" -> "g"
            "litre", "litres", "liters" -> "liter"
            "ounce", "ounces" -> "oz"
            "pound", "pounds" -> "lb"
            else -> unit
        }
    }

    private fun formatMealNameItem(mention: FoodMention): String {
        val quantity = mention.quantity
        return if (quantity == 1.0 && mention.unit !in metricUnits && mention.unit !in imperialUnits) {
            mention.foodName
        } else {
            "${formatQuantity(quantity)} ${mention.foodName}"
        }
    }

    private fun formatQuantity(quantity: Double): String =
        if (quantity % 1.0 == 0.0) quantity.toInt().toString() else "%.2f".format(quantity).trimEnd('0').trimEnd('.')

    private companion object {
        val metricUnits = setOf("g", "gram", "grams", "kg", "ml", "litre", "liter", "litres", "liters")
        val imperialUnits = setOf("oz", "ounce", "ounces", "lb", "pound", "pounds")
    }
}
