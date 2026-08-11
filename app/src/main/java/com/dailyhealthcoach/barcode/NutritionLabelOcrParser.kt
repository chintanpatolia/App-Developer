package com.dailyhealthcoach.barcode

import com.dailyhealthcoach.ui.nutrition.FoodEntryFormUiState

object NutritionLabelOcrParser {

    fun parse(text: String, existing: FoodEntryFormUiState): FoodEntryFormUiState {
        val lines = text.lines().map { it.trim() }.filter { it.isNotEmpty() }
        var form = existing

        // Match number + unit; unit must not be immediately followed by % (to avoid capturing %DV numbers)
        val valuePattern = Regex("""(\d+\.?\d*)\s*(mg|mcg|µg|μg|ug|g|iu)\b""", RegexOption.IGNORE_CASE)

        fun extractValue(line: String): Pair<Double, String>? {
            val m = valuePattern.find(line) ?: return null
            val v = m.groupValues[1].toDoubleOrNull()?.takeIf { it > 0 } ?: return null
            return v to m.groupValues[2].lowercase()
        }

        // Try same line; fall back to next line for two-line label entries
        fun findValue(idx: Int): Pair<Double, String>? =
            extractValue(lines[idx]) ?: lines.getOrNull(idx + 1)?.let { extractValue(it) }

        fun fmt(v: Double): String =
            if (v == kotlin.math.floor(v) && v < 1_000_000) v.toLong().toString()
            else "%.2f".format(v).trimEnd('0').trimEnd('.')

        fun toMg(v: Double, u: String): Double = when (u) {
            "g" -> v * 1000.0
            "mg" -> v
            "mcg", "µg", "μg", "ug" -> v / 1000.0
            else -> v
        }

        fun toMcg(v: Double, u: String): Double = when (u) {
            "g" -> v * 1_000_000.0
            "mg" -> v * 1000.0
            "mcg", "µg", "μg", "ug" -> v
            else -> v
        }

        lines.forEachIndexed { i, line ->
            val l = line.lowercase()
            when {
                // Calories — no unit on label, just grab first integer on the "Calories" line
                Regex("""^calories\b""").containsMatchIn(l) -> {
                    if (form.calories.isEmpty())
                        Regex("""(\d+)""").find(line)?.let { form = form.copy(calories = it.value) }
                }
                // Protein (stored in grams)
                Regex("""^protein\b""").containsMatchIn(l) -> {
                    if (form.proteinGrams.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(proteinGrams = fmt(toMg(v, u) / 1000.0))
                    }
                }
                // Total Fat
                Regex("""^total fat\b""").containsMatchIn(l) -> {
                    if (form.fatGrams.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(fatGrams = fmt(toMg(v, u) / 1000.0))
                    }
                }
                // Total Carbohydrate
                Regex("""^total carb""").containsMatchIn(l) -> {
                    if (form.carbGrams.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(carbGrams = fmt(toMg(v, u) / 1000.0))
                    }
                }
                // Dietary Fiber
                Regex("""dietary (fiber|fibre)""").containsMatchIn(l) -> {
                    if (form.fiberGrams.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(fiberGrams = fmt(toMg(v, u) / 1000.0))
                    }
                }
                // Minerals stored in mg
                Regex("""^sodium\b""").containsMatchIn(l) -> {
                    if (form.sodium.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(sodium = fmt(toMg(v, u)))
                    }
                }
                Regex("""^potassium\b""").containsMatchIn(l) -> {
                    if (form.potassium.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(potassium = fmt(toMg(v, u)))
                    }
                }
                Regex("""^calcium\b""").containsMatchIn(l) -> {
                    if (form.calcium.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(calcium = fmt(toMg(v, u)))
                    }
                }
                Regex("""^iron\b""").containsMatchIn(l) -> {
                    if (form.iron.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(iron = fmt(toMg(v, u)))
                    }
                }
                Regex("""^magnesium\b""").containsMatchIn(l) -> {
                    if (form.magnesium.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(magnesium = fmt(toMg(v, u)))
                    }
                }
                Regex("""^zinc\b""").containsMatchIn(l) -> {
                    if (form.zinc.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(zinc = fmt(toMg(v, u)))
                    }
                }
                Regex("""^phosphorus\b""").containsMatchIn(l) -> {
                    if (form.phosphorus.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(phosphorus = fmt(toMg(v, u)))
                    }
                }
                Regex("""^copper\b""").containsMatchIn(l) -> {
                    if (form.copper.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(copper = fmt(toMg(v, u)))
                    }
                }
                Regex("""^manganese\b""").containsMatchIn(l) -> {
                    if (form.manganese.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(manganese = fmt(toMg(v, u)))
                    }
                }
                // Minerals stored in mcg
                Regex("""^iodine\b""").containsMatchIn(l) -> {
                    if (form.iodine.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(iodine = fmt(toMcg(v, u)))
                    }
                }
                Regex("""^selenium\b""").containsMatchIn(l) -> {
                    if (form.selenium.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(selenium = fmt(toMcg(v, u)))
                    }
                }
                Regex("""^chromium\b""").containsMatchIn(l) -> {
                    if (form.chromium.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(chromium = fmt(toMcg(v, u)))
                    }
                }
                Regex("""^molybdenum\b""").containsMatchIn(l) -> {
                    if (form.molybdenum.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(molybdenum = fmt(toMcg(v, u)))
                    }
                }
                // Vitamins — matched with contains() to handle "Vitamin A 450mcg 50%" style lines
                l.contains("vitamin a") -> {
                    if (form.vitaminA.isEmpty()) findValue(i)?.let { (v, u) ->
                        // 1 IU Vit A = 0.3 mcg retinol
                        val mcg = if (u == "iu") v * 0.3 else toMcg(v, u)
                        form = form.copy(vitaminA = fmt(mcg))
                    }
                }
                l.contains("vitamin c") || l.contains("ascorbic acid") -> {
                    if (form.vitaminC.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(vitaminC = fmt(toMg(v, u)))
                    }
                }
                l.contains("vitamin d") -> {
                    if (form.vitaminD.isEmpty()) findValue(i)?.let { (v, u) ->
                        // 1 IU Vit D = 0.025 mcg
                        val mcg = if (u == "iu") v * 0.025 else toMcg(v, u)
                        form = form.copy(vitaminD = fmt(mcg))
                    }
                }
                l.contains("vitamin e") -> {
                    if (form.vitaminE.isEmpty()) findValue(i)?.let { (v, u) ->
                        // 1 IU Vit E ≈ 0.67 mg (natural)
                        val mg = if (u == "iu") v * 0.67 else toMg(v, u)
                        form = form.copy(vitaminE = fmt(mg))
                    }
                }
                l.contains("vitamin k") -> {
                    if (form.vitaminK.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(vitaminK = fmt(toMcg(v, u)))
                    }
                }
                l.contains("thiamin") || l.contains("vitamin b1") || l.contains("vitamin b-1") || l.contains("vitamin b 1") -> {
                    if (form.vitaminB1.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(vitaminB1 = fmt(toMg(v, u)))
                    }
                }
                l.contains("riboflavin") || l.contains("vitamin b2") || l.contains("vitamin b-2") || l.contains("vitamin b 2") -> {
                    if (form.vitaminB2.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(vitaminB2 = fmt(toMg(v, u)))
                    }
                }
                l.contains("niacin") || l.contains("vitamin b3") || l.contains("vitamin b-3") || l.contains("vitamin b 3") -> {
                    if (form.vitaminB3.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(vitaminB3 = fmt(toMg(v, u)))
                    }
                }
                l.contains("vitamin b6") || l.contains("vitamin b-6") || l.contains("vitamin b 6") || l.contains("pyridoxine") -> {
                    if (form.vitaminB6.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(vitaminB6 = fmt(toMg(v, u)))
                    }
                }
                l.contains("folate") || l.contains("folic acid") || l.contains("vitamin b9") || l.contains("vitamin b-9") -> {
                    if (form.folate.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(folate = fmt(toMcg(v, u)))
                    }
                }
                l.contains("vitamin b12") || l.contains("vitamin b-12") || l.contains("vitamin b 12") || l.contains("cobalamin") -> {
                    if (form.vitaminB12.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(vitaminB12 = fmt(toMcg(v, u)))
                    }
                }
                l.contains("biotin") -> {
                    if (form.biotin.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(biotin = fmt(toMcg(v, u)))
                    }
                }
                l.contains("pantothenic") || l.contains("vitamin b5") || l.contains("vitamin b-5") || l.contains("pantothenate") -> {
                    if (form.pantothenicAcid.isEmpty()) findValue(i)?.let { (v, u) ->
                        form = form.copy(pantothenicAcid = fmt(toMg(v, u)))
                    }
                }
            }
        }

        return form
    }
}
