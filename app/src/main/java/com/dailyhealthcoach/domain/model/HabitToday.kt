package com.dailyhealthcoach.domain.model

data class HabitToday(
    val id: Long,
    val name: String,
    val description: String,
    val frequencyType: String,
    val frequencyLabel: String,
    val targetPerWeek: Int?,
    val status: HabitStatus,
    val isTodayTrackable: Boolean
)
