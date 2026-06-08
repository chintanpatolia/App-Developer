package com.dailyhealthcoach.domain.model

data class HabitDefinition(
    val id: Long,
    val name: String,
    val description: String,
    val frequencyType: String,
    val targetPerWeek: Int?,
    val sortOrder: Int
)
