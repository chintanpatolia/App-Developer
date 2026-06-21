package com.dailyhealthcoach.domain.model

object PlannedMealSlotKey {
    fun fromDisplayLabel(label: String): String = when (label) {
        "Breakfast" -> "breakfast"
        "Lunch" -> "lunch"
        "Dinner" -> "dinner"
        "Snack" -> "snack_1"
        "Protein Booster 1" -> "protein_booster_1"
        "Protein Booster 2" -> "protein_booster_2"
        else -> label.lowercase().replace(" ", "_")
    }

    fun toDisplayLabel(slotKey: String): String = when (slotKey) {
        "breakfast" -> "Breakfast"
        "lunch" -> "Lunch"
        "dinner" -> "Dinner"
        "snack_1" -> "Snack"
        "protein_booster_1" -> "Protein Booster 1"
        "protein_booster_2" -> "Protein Booster 2"
        else -> slotKey
    }
}
