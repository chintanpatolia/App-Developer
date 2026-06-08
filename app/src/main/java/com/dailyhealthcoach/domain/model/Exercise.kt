package com.dailyhealthcoach.domain.model

data class Exercise(
    val id: Long,
    val name: String,
    val muscleGroup: String,
    val equipmentType: String,
    val movementPattern: String,
    val notes: String?,
    val isCustom: Boolean
)
