package com.dailyhealthcoach.domain.model

data class Workout(
    val id: Long,
    val date: String,
    val name: String,
    val durationMinutes: Int?,
    val status: String,
    val overallRpe: Int?,
    val notes: String?
)
