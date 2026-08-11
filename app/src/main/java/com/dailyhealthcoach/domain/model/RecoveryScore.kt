package com.dailyhealthcoach.domain.model

data class RecoveryScore(
    val id: Long,
    val date: String,
    val score: Int,
    val label: String,
    val reasons: List<String>,
    val notes: String?
)
