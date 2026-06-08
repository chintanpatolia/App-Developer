package com.dailyhealthcoach.domain.model

data class UserProfile(
    val id: Long,
    val name: String,
    val heightInches: Double?,
    val birthDate: String?,
    val bedtime: String?
)
