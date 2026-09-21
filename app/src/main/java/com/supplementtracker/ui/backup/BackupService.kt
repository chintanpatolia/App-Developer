package com.supplementtracker.ui.backup

import com.google.gson.*
import com.supplementtracker.data.dao.DailyOccurrenceDao
import com.supplementtracker.data.dao.ScheduleGroupDao
import com.supplementtracker.data.dao.SupplementDao
import com.supplementtracker.data.entity.DailyOccurrenceEntity
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity

class BackupService(
    private val scheduleGroupDao: ScheduleGroupDao,
    private val supplementDao: SupplementDao,
    private val occurrenceDao: DailyOccurrenceDao
) {
    data class BackupData(
        val version: Int = 1,
        val scheduleGroups: List<ScheduleGroupEntity> = emptyList(),
        val supplements: List<SupplementEntity> = emptyList(),
        val occurrences: List<DailyOccurrenceEntity> = emptyList()
    )

    suspend fun export(): String {
        val data = BackupData(
            version = 1,
            scheduleGroups = scheduleGroupDao.getAll(),
            supplements = supplementDao.getActive(),
            occurrences = occurrenceDao.getInRange("2000-01-01", "2999-12-31")
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

        data.scheduleGroups.forEach { scheduleGroupDao.insert(it) }
        data.supplements.forEach { supplementDao.insert(it) }
        data.occurrences.forEach { occurrenceDao.insert(it) }
    }
}
