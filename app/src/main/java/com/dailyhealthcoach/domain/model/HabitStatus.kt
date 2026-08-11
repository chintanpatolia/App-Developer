package com.dailyhealthcoach.domain.model

enum class HabitStatus(val storageValue: String) {
    COMPLETE("COMPLETE"),
    SKIPPED("SKIPPED"),
    NOT_DONE("NOT_DONE");

    companion object {
        fun fromStorageValue(value: String?): HabitStatus {
            return entries.firstOrNull { it.storageValue == value } ?: NOT_DONE
        }
    }
}
