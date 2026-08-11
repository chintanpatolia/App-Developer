package com.dailyhealthcoach.ai

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AiFoodLoggingServiceTest {

    private val service = AiFoodLoggingService()

    @Test
    fun `parse applies fractional serving language before foods`() = runTest {
        val estimate = service.parse("half cup rice with two eggs")

        assertEquals("0.5 rice, 2 egg", estimate.mealName)
        assertEquals(244, estimate.calories)
        assertEquals(14.0, estimate.proteinGrams, 0.01)
        assertEquals(22.0, estimate.carbGrams, 0.01)
        assertEquals(10.25, estimate.fatGrams, 0.01)
    }

    @Test
    fun `parse applies meal portion words before foods`() = runTest {
        val estimate = service.parse("a bowl of dal and a small protein shake")

        assertEquals("1.5 dal, 0.5 protein shake", estimate.mealName)
        assertEquals(285, estimate.calories)
        assertEquals(26.0, estimate.proteinGrams, 0.01)
        assertEquals(42.5, estimate.carbGrams, 0.01)
        assertEquals(1.75, estimate.fatGrams, 0.01)
        assertFalse(estimate.isWholeFoodBased)
    }

    @Test
    fun `parse supports compound serving language with plural foods`() = runTest {
        val estimate = service.parse("2 rotis with half a bowl of dal")

        assertEquals("2 roti, 0.75 dal", estimate.mealName)
        assertEquals(313, estimate.calories)
        assertEquals(12.75, estimate.proteinGrams, 0.01)
        assertEquals(56.25, estimate.carbGrams, 0.01)
        assertEquals(4.38, estimate.fatGrams, 0.01)
        assertEquals(listOf("High", "Medium"), estimate.parsedItems.map { it.confidence })
    }

    @Test
    fun `parse supports relative size before food`() = runTest {
        val estimate = service.parse("one large banana and a protein shake")

        assertEquals("1.5 banana, protein shake", estimate.mealName)
        assertEquals(254, estimate.calories)
        assertEquals(26.65, estimate.proteinGrams, 0.01)
        assertEquals("Medium", estimate.confidence)
        assertEquals(listOf("Medium", "High"), estimate.parsedItems.map { it.confidence })
    }

    @Test
    fun `parse supports metric volume units`() = runTest {
        val estimate = service.parse("250 ml milk")

        assertEquals("1 milk", estimate.mealName)
        assertEquals(122, estimate.calories)
        assertEquals(8.0, estimate.proteinGrams, 0.01)
        assertEquals(12.0, estimate.carbGrams, 0.01)
        assertEquals("High", estimate.parsedItems.single().confidence)
    }

    @Test
    fun `parse supports tablespoons and energy dense foods`() = runTest {
        val estimate = service.parse("2 tablespoons peanut butter")

        assertEquals("2 peanut butter", estimate.mealName)
        assertEquals(190, estimate.calories)
        assertEquals(7.0, estimate.proteinGrams, 0.01)
        assertEquals(16.0, estimate.fatGrams, 0.01)
        assertEquals("High", estimate.parsedItems.single().confidence)
    }

    @Test
    fun `parse supports scoop and serving phrases`() = runTest {
        val whey = service.parse("one scoop whey")
        val yogurt = service.parse("one serving greek yogurt")

        assertEquals("whey", whey.mealName)
        assertEquals(120, whey.calories)
        assertEquals("High", whey.parsedItems.single().confidence)

        assertEquals("greek yogurt", yogurt.mealName)
        assertEquals(100, yogurt.calories)
        assertEquals("High", yogurt.parsedItems.single().confidence)
    }

    @Test
    fun `parse handles ambiguous inputs gracefully`() = runTest {
        val estimate = service.parse("some curry and snacks")

        assertEquals("some curry and snacks", estimate.mealName)
        assertEquals(0, estimate.calories)
        assertEquals("Low", estimate.confidence)
        assertTrue(estimate.parsedItems.isEmpty())
        assertTrue(estimate.notes.contains("Food not recognized"))
    }

    @Test
    fun `parse supports household and serving unit families`() = runTest {
        val estimate = service.parse("quarter cup oats, one handful salad, a slice bread, one plate rice and one piece chicken")

        assertEquals(708, estimate.calories)
        assertEquals(44.25, estimate.proteinGrams, 0.01)
        assertEquals(113.25, estimate.carbGrams, 0.01)
        assertEquals(listOf("cup", "handful", "slice", "plate", "piece"), estimate.parsedItems.map { it.unit })
    }

    @Test
    fun `parse supports teaspoon pinch glass and mug units`() = runTest {
        val estimate = service.parse("1 teaspoon peanut butter, a pinch whey, one glass milk and one mug chai")

        assertEquals(220, estimate.calories)
        assertEquals(listOf("teaspoon", "pinch", "glass", "mug"), estimate.parsedItems.map { it.unit })
        assertEquals(listOf("High", "Medium", "High", "High"), estimate.parsedItems.map { it.confidence })
    }

    @Test
    fun `parse supports metric weight and volume units`() = runTest {
        val estimate = service.parse("100 grams chicken, 0.5 kg tofu, 1 liter milk and 1 litre chai")

        assertEquals(1706, estimate.calories)
        assertEquals(listOf("g", "kg", "liter", "liter"), estimate.parsedItems.map { it.unit })
        assertEquals(listOf("High", "High", "High", "High"), estimate.parsedItems.map { it.confidence })
    }

    @Test
    fun `parse supports imperial units`() = runTest {
        val estimate = service.parse("2 oz paneer and 1 lb chicken")

        assertEquals(899, estimate.calories)
        assertEquals(listOf("oz", "lb"), estimate.parsedItems.map { it.unit })
        assertEquals(listOf("High", "High"), estimate.parsedItems.map { it.confidence })
    }

    @Test
    fun `parse estimates low glucose response for protein and fiber balanced meals`() = runTest {
        val estimate = service.parse("chicken salad")

        assertEquals("Low", estimate.estimatedGlucoseResponse)
        assertTrue(estimate.notes.contains("Estimated glucose response: Low"))
    }

    @Test
    fun `parse estimates moderate glucose response for mixed carb meals`() = runTest {
        val estimate = service.parse("one roti with dal")

        assertEquals("Moderate", estimate.estimatedGlucoseResponse)
        assertTrue(estimate.notes.contains("Estimated glucose response: Moderate"))
    }

    @Test
    fun `parse estimates high glucose response for high carb low fiber meals`() = runTest {
        val estimate = service.parse("2 plates rice and chai")

        assertEquals("High", estimate.estimatedGlucoseResponse)
        assertTrue(estimate.notes.contains("Estimated glucose response: High"))
    }
}
