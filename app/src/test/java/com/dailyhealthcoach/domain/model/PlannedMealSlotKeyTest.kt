package com.dailyhealthcoach.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PlannedMealSlotKeyTest {

    @Test
    fun `fromDisplayLabel maps all standard labels to stable keys`() {
        assertEquals("breakfast", PlannedMealSlotKey.fromDisplayLabel("Breakfast"))
        assertEquals("lunch", PlannedMealSlotKey.fromDisplayLabel("Lunch"))
        assertEquals("dinner", PlannedMealSlotKey.fromDisplayLabel("Dinner"))
        assertEquals("snack_1", PlannedMealSlotKey.fromDisplayLabel("Snack"))
        assertEquals("protein_booster_1", PlannedMealSlotKey.fromDisplayLabel("Protein Booster 1"))
        assertEquals("protein_booster_2", PlannedMealSlotKey.fromDisplayLabel("Protein Booster 2"))
    }

    @Test
    fun `toDisplayLabel maps all stable keys to standard labels`() {
        assertEquals("Breakfast", PlannedMealSlotKey.toDisplayLabel("breakfast"))
        assertEquals("Lunch", PlannedMealSlotKey.toDisplayLabel("lunch"))
        assertEquals("Dinner", PlannedMealSlotKey.toDisplayLabel("dinner"))
        assertEquals("Snack", PlannedMealSlotKey.toDisplayLabel("snack_1"))
        assertEquals("Protein Booster 1", PlannedMealSlotKey.toDisplayLabel("protein_booster_1"))
        assertEquals("Protein Booster 2", PlannedMealSlotKey.toDisplayLabel("protein_booster_2"))
    }

    @Test
    fun `fromDisplayLabel and toDisplayLabel are inverse for all standard keys`() {
        val labels = listOf("Breakfast", "Lunch", "Dinner", "Snack", "Protein Booster 1", "Protein Booster 2")
        labels.forEach { label ->
            assertEquals(
                label,
                PlannedMealSlotKey.toDisplayLabel(PlannedMealSlotKey.fromDisplayLabel(label))
            )
        }
    }
}
