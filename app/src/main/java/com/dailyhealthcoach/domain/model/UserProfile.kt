package com.dailyhealthcoach.domain.model

data class UserProfile(
    val id: Long,
    val name: String,
    val heightInches: Double?,
    val birthDate: String?,
    val bedtime: String?,
    val age: Int? = null,
    val sex: String? = null,
    val weightGoalPounds: Double? = null,
    val bodyFatGoalPercent: Double? = null,
    val stepMinTarget: Int? = null,
    val stepMaxTarget: Int? = null,
    val sleepTargetHours: Double? = null,
    val strengthTrainingDaysPerWeek: Int? = null
)
