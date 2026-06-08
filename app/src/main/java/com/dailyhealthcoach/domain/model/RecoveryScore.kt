package com.dailyhealthcoach.domain.model

data class RecoveryScore(
    val id: Long,
    val date: String,
    val score: Int,
    val notes: String?
)
