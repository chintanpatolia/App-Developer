package com.dailyhealthcoach.domain.model

data class RecoveryActivity(
    val id: Long = 0,
    val workoutId: Long,
    val name: String,
    val status: String,
    val durationSeconds: Int?,
    val rpe: Int?,
    val notes: String?
)
