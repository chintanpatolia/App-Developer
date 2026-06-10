package com.dailyhealthcoach.barcode

// Demo lookup service. Replace with Open Food Facts or another API in a future phase.
class MockFoodLookupService : FoodLookupService {
    override suspend fun lookup(barcode: String): FoodLookupResult? = when (barcode) {
        "0000000000001" -> FoodLookupResult(
            barcode = barcode,
            foodName = "Demo Protein Bar",
            brandName = "Demo Brand",
            servingDescription = "1 bar (60g)",
            calories = 220,
            proteinGrams = 20.0,
            carbGrams = 22.0,
            fatGrams = 8.0,
            fiberGrams = 5.0,
            source = "BARCODE_MOCK"
        )
        "0000000000002" -> FoodLookupResult(
            barcode = barcode,
            foodName = "Demo Greek Yogurt",
            brandName = "Demo Brand",
            servingDescription = "1 cup (227g)",
            calories = 150,
            proteinGrams = 17.0,
            carbGrams = 12.0,
            fatGrams = 3.0,
            fiberGrams = 0.0,
            source = "BARCODE_MOCK"
        )
        else -> null
    }
}
