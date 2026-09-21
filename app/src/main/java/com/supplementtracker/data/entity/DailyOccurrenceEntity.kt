package com.supplementtracker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Immutable record of a supplement occurrence for a given date.
 * scheduledHour/scheduledMinute are captured from the group at creation time
 * so historical accuracy is preserved if the group time changes later.
 */
@Entity(
    tableName = "daily_occurrences",
    foreignKeys = [ForeignKey(
        entity = SupplementEntity::class,
        parentColumns = ["id"],
        childColumns = ["supplementId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index("supplementId"),
        Index(value = ["supplementId", "scheduledDate"], unique = true)
    ]
)
data class DailyOccurrenceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val supplementId: Long,
    val scheduledDate: String,      // "yyyy-MM-dd" in device local zone
    val scheduledHour: Int,
    val scheduledMinute: Int,
    val scheduleGroupId: Long,
    val completed: Boolean = false,
    val completedAt: Long? = null   // epoch millis UTC
)
