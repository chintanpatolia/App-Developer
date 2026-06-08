package com.dailyhealthcoach.domain.model

data class MacroTarget(
    val id: Long,
    val proteinMinGrams: Int,
    val proteinMaxGrams: Int,
    val calorieTarget: Int?,
    val carbTargetGrams: Int?,
    val fatTargetGrams: Int?,
    val fiberTargetGrams: Int?
)
