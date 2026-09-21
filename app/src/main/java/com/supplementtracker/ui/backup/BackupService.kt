package com.supplementtracker.ui.backup

import com.google.gson.*
import com.supplementtracker.data.db.AppDatabase
import com.supplementtracker.data.entity.DailyOccurrenceEntity
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity

class BackupService(private val db: AppDatabase) {
    data class BackupData(
        val version: Int = 1,
        val scheduleGroups: List<ScheduleGroupEntity> = emptyList(),
        val supplements: List<SupplementEntity> = emptyList(),
        val occurrences: List<DailyOccurrenceEntity> = emptyList()
    )

    suspend fun export(): String {
        val data = BackupData(
            version = 1,
            scheduleGroups = db.scheduleGroupDao().getAll(),
            supplements = db.supplementDao().getActive(),
            occurrences = db.dailyOccurrenceDao().getInRange("2000-01-01", "2999-12-31")
        )
        return Gson().toJson(data)
    }

    suspend fun import(json: String): Result<Unit> = runCatching {
        val data = try {
            Gson().fromJson(json, BackupData::class.java)
                ?: throw IllegalArgumentException("Invalid backup file: null result")
        } catch (e: JsonSyntaxException) {
            throw IllegalArgumentException("Invalid backup file: ${e.message}")
        }

        if (data.version != 1) throw IllegalArgumentException("Unsupported backup version: ${data.version}")
        if (data.scheduleGroups.isEmpty()) throw IllegalArgumentException("Backup contains no schedule groups")

        db.withTransaction {
            data.scheduleGroups.forEach { db.scheduleGroupDao().insert(it) }
            data.supplements.forEach { db.supplementDao().insert(it) }
            data.occurrences.forEach { db.dailyOccurrenceDao().insert(it) }
        }
    }
}
