package com.dailyhealthcoach.barcode

data class FoodLookupResult(
    val barcode: String,
    val foodName: String,
    val brandName: String?,
    val servingDescription: String?,
    val calories: Int?,
    val proteinGrams: Double?,
    val carbGrams: Double?,
    val fatGrams: Double?,
    val fiberGrams: Double?,
    val source: String,
    val vitaminA: Double? = null,
    val vitaminC: Double? = null,
    val vitaminD: Double? = null,
    val vitaminB12: Double? = null,
    val calcium: Double? = null,
    val iron: Double? = null,
    val potassium: Double? = null,
    val magnesium: Double? = null,
    val sodium: Double? = null,
    val zinc: Double? = null,
    val vitaminE: Double? = null,
    val vitaminK: Double? = null,
    val vitaminB1: Double? = null,
    val vitaminB2: Double? = null,
    val vitaminB3: Double? = null,
    val vitaminB6: Double? = null,
    val folate: Double? = null,
    val biotin: Double? = null,
    val pantothenicAcid: Double? = null,
    val phosphorus: Double? = null,
    val iodine: Double? = null,
    val selenium: Double? = null,
    val copper: Double? = null,
    val manganese: Double? = null,
    val chromium: Double? = null,
    val molybdenum: Double? = null
)

interface FoodLookupService {
    suspend fun lookup(barcode: String): FoodLookupResult?
}
