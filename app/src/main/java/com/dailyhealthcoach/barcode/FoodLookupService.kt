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
    val source: String
)

interface FoodLookupService {
    suspend fun lookup(barcode: String): FoodLookupResult?
}
