package com.dailyhealthcoach.barcode

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class OpenFoodFactsService : FoodLookupService {

    override suspend fun lookup(barcode: String): FoodLookupResult? = withContext(Dispatchers.IO) {
        val conn: HttpURLConnection
        try {
            val url = URL("https://world.openfoodfacts.org/api/v0/product/$barcode.json")
            conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 10_000
            conn.readTimeout = 10_000
            conn.setRequestProperty("User-Agent", "DailyHealthCoach/1.0")
        } catch (e: IOException) {
            throw e  // network / DNS failure — caller shows network error message
        }

        try {
            if (conn.responseCode != 200) return@withContext null

            val body = conn.inputStream.bufferedReader().readText()
            conn.disconnect()

            val root = JSONObject(body)
            if (root.optInt("status", 0) != 1) return@withContext null

            val product = root.optJSONObject("product") ?: return@withContext null
            val name = product.optString("product_name").ifBlank { null } ?: return@withContext null
            val brand = product.optString("brands").ifBlank { null }
            val servingSize = product.optString("serving_size").ifBlank { null }

            val nutriments = product.optJSONObject("nutriments")

            fun nutr(key: String): Double? {
                val v = nutriments?.opt("${key}_serving") ?: nutriments?.opt("${key}_100g") ?: nutriments?.opt(key)
                return when (v) {
                    is Number -> v.toDouble().takeIf { it > 0 }
                    is String -> v.toDoubleOrNull()?.takeIf { it > 0 }
                    else -> null
                }
            }

            // OFF stores micronutrients in the native unit declared by _unit (mg, µg, or g).
            // Using _unit avoids the 1000x error that occurs when a product stores mg values directly.
            fun nutrUnit(key: String): String =
                nutriments?.optString("${key}_unit").orEmpty().lowercase().trim()

            fun toMg(key: String): Double? {
                val v = nutr(key) ?: return null
                return when (nutrUnit(key)) {
                    "mg" -> v
                    "µg", "mcg", "ug", "μg" -> v / 1000.0
                    else -> v * 1000.0  // assume grams (OFF default for most macros)
                }
            }

            fun toMcg(key: String): Double? {
                val v = nutr(key) ?: return null
                return when (nutrUnit(key)) {
                    "µg", "mcg", "ug", "μg" -> v
                    "mg" -> v * 1000.0
                    else -> v * 1_000_000.0  // assume grams
                }
            }

            FoodLookupResult(
                barcode = barcode,
                foodName = name,
                brandName = brand,
                servingDescription = servingSize,
                calories = nutr("energy-kcal")?.toInt() ?: nutr("energy")?.let { (it / 4.184).toInt() },
                proteinGrams = nutr("proteins"),
                carbGrams = nutr("carbohydrates"),
                fatGrams = nutr("fat"),
                fiberGrams = nutr("fiber"),
                source = "OPEN_FOOD_FACTS",
                vitaminA = toMcg("vitamin-a"),
                vitaminC = toMg("vitamin-c"),
                vitaminD = toMcg("vitamin-d"),
                vitaminB12 = toMcg("vitamin-b12"),
                calcium = toMg("calcium"),
                iron = toMg("iron"),
                potassium = toMg("potassium"),
                magnesium = toMg("magnesium"),
                sodium = toMg("sodium"),
                zinc = toMg("zinc"),
                vitaminE = toMg("vitamin-e"),
                vitaminK = toMcg("vitamin-k"),
                vitaminB1 = toMg("vitamin-b1") ?: toMg("thiamin"),
                vitaminB2 = toMg("vitamin-b2") ?: toMg("riboflavin"),
                vitaminB3 = toMg("vitamin-pp") ?: toMg("niacin") ?: toMg("vitamin-b3"),
                vitaminB6 = toMg("vitamin-b6"),
                folate = toMcg("vitamin-b9") ?: toMcg("folates") ?: toMcg("folic-acid"),
                biotin = toMcg("biotin"),
                pantothenicAcid = toMg("pantothenic-acid"),
                phosphorus = toMg("phosphorus"),
                iodine = toMcg("iodine"),
                selenium = toMcg("selenium"),
                copper = toMg("copper"),
                manganese = toMg("manganese"),
                chromium = toMcg("chromium"),
                molybdenum = toMcg("molybdenum")
            )
        } catch (e: IOException) {
            throw e
        } catch (_: Exception) {
            null
        }
    }
}
