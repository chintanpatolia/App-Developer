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

            fun nutrMg(key: String): Double? = nutr(key)?.let { it * 1000.0 }
            fun nutrMcg(key: String): Double? = nutr(key)?.let { it * 1_000_000.0 }

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
                vitaminA = nutrMcg("vitamin-a"),
                vitaminC = nutrMg("vitamin-c"),
                vitaminD = nutrMcg("vitamin-d"),
                vitaminB12 = nutrMcg("vitamin-b12"),
                calcium = nutrMg("calcium"),
                iron = nutrMg("iron"),
                potassium = nutrMg("potassium"),
                magnesium = nutrMg("magnesium"),
                sodium = nutrMg("sodium"),
                zinc = nutrMg("zinc"),
                vitaminE = nutrMg("vitamin-e"),
                vitaminK = nutrMcg("vitamin-k"),
                vitaminB1 = nutrMg("vitamin-b1") ?: nutrMg("thiamin"),
                vitaminB2 = nutrMg("vitamin-b2") ?: nutrMg("riboflavin"),
                vitaminB3 = nutrMg("vitamin-pp") ?: nutrMg("niacin"),
                vitaminB6 = nutrMg("vitamin-b6"),
                folate = nutrMcg("vitamin-b9") ?: nutrMcg("folates"),
                biotin = nutrMcg("biotin"),
                pantothenicAcid = nutrMg("pantothenic-acid"),
                phosphorus = nutrMg("phosphorus"),
                iodine = nutrMcg("iodine"),
                selenium = nutrMcg("selenium"),
                copper = nutrMg("copper"),
                manganese = nutrMg("manganese"),
                chromium = nutrMcg("chromium"),
                molybdenum = nutrMcg("molybdenum")
            )
        } catch (e: IOException) {
            throw e
        } catch (_: Exception) {
            null
        }
    }
}
