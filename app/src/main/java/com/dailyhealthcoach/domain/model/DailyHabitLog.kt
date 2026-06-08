package com.dailyhealthcoach.domain.model

data class DailyHabitLog(
    val id: Long,
    val habitDefinitionId: Long,
    val date: String,
    val status: String,
    val notes: String?
)
