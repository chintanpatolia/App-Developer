package com.supplementtracker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "supplements",
    foreignKeys = [ForeignKey(
        entity = ScheduleGroupEntity::class,
        parentColumns = ["id"],
        childColumns = ["scheduleGroupId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("scheduleGroupId")]
)
data class SupplementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val dose: String,
    val notes: String = "",
    val active: Boolean = true,
    val scheduleGroupId: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val sortOrder: Int = 0,
    // Epoch-millis when the current scheduleGroupId assignment was set.
    // Reconciliation only creates historical occurrences for dates on or after this date,
    // because we cannot know which group the supplement was in before the last assignment.
    val groupAssignedAt: Long = System.currentTimeMillis()
)
