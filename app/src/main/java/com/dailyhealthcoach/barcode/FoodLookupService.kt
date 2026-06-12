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
    val zinc: Double? = null
)

interface FoodLookupService {
    suspend fun lookup(barcode: String): FoodLookupResult?
}
