package com.dailyhealthcoach.domain.model

enum class WorkoutStatus(val storageValue: String, val label: String) {
    COMPLETED("COMPLETED", "Completed"),
    PARTIAL("PARTIAL", "Partial"),
    SKIPPED("SKIPPED", "Skipped");

    companion object {
        fun fromStorageValue(value: String): WorkoutStatus {
            return entries.firstOrNull { it.storageValue == value } ?: COMPLETED
        }
    }
}
