package com.dailyhealthcoach.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recovery_scores",
    indices = [Index(value = ["date"], unique = true)]
)
data class RecoveryScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val score: Int,
    val sleepContribution: Int,
    val proteinContribution: Int,
    val sorenessContribution: Int,
    val stressContribution: Int,
    val workoutContribution: Int,
    val stepsContribution: Int,
    val restDayContribution: Int,
    val notes: String? = null,
    val createdAt: String
)
