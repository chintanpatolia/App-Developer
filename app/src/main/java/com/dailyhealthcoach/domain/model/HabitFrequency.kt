package com.dailyhealthcoach.domain.model

enum class HabitFrequency(val storageValue: String, val label: String) {
    DAILY("DAILY", "Daily"),
    WEEKLY_TARGET("WEEKLY_TARGET", "Weekly"),
    INTERVAL("INTERVAL", "Every 6-12 months"),
    AS_NEEDED("AS_NEEDED", "As needed");

    companion object {
        fun labelFor(value: String): String {
            return entries.firstOrNull { it.storageValue == value }?.label ?: value
        }

        fun isTodayTrackable(value: String): Boolean {
            return value != INTERVAL.storageValue
        }
    }
}
