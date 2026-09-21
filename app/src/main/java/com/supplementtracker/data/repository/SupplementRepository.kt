package com.supplementtracker.data.repository

import com.supplementtracker.data.dao.DailyOccurrenceDao
import com.supplementtracker.data.dao.ScheduleGroupDao
import com.supplementtracker.data.dao.SupplementDao
import com.supplementtracker.data.entity.DailyOccurrenceEntity
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity
import kotlinx.coroutines.flow.Flow

class SupplementRepository(
    private val supplementDao: SupplementDao,
    private val scheduleGroupDao: ScheduleGroupDao,
    private val occurrenceDao: DailyOccurrenceDao
) : com.supplementtracker.domain.ReconcileDataSource {
    val activeSupplements: Flow<List<SupplementEntity>> = supplementDao.observeActive()
    val allSupplements: Flow<List<SupplementEntity>> = supplementDao.observeAll()
    val allGroups: Flow<List<ScheduleGroupEntity>> = scheduleGroupDao.observeAll()

    fun observeTodayOccurrences(date: String): Flow<List<DailyOccurrenceEntity>> =
        occurrenceDao.observeByDate(date)

    suspend fun getGroups(): List<ScheduleGroupEntity> = scheduleGroupDao.getAll()

    suspend fun getGroupById(id: Long): ScheduleGroupEntity? = scheduleGroupDao.getById(id)

    suspend fun upsertGroup(group: ScheduleGroupEntity): Long {
        return if (group.id == 0L) scheduleGroupDao.insert(group)
        else { scheduleGroupDao.update(group); group.id }
    }

    suspend fun upsertSupplement(supplement: SupplementEntity): Long {
        return if (supplement.id == 0L) {
            val now = System.currentTimeMillis()
            supplementDao.insert(supplement.copy(createdAt = now, groupAssignedAt = now))
        } else {
            val existing = supplementDao.getById(supplement.id)
            val groupAssignedAt = if (existing != null && existing.scheduleGroupId != supplement.scheduleGroupId) {
                System.currentTimeMillis()
            } else {
                existing?.groupAssignedAt ?: supplement.groupAssignedAt
            }
            supplementDao.update(supplement.copy(
                updatedAt = System.currentTimeMillis(),
                groupAssignedAt = groupAssignedAt
            ))
            supplement.id
        }
    }

    suspend fun deleteSupplement(supplement: SupplementEntity) {
        supplementDao.delete(supplement)
    }

    suspend fun ensureTodayOccurrences(date: String) {
        val supplements = supplementDao.getActive()
        val existing = occurrenceDao.getByDate(date).map { it.supplementId }.toSet()
        val groups = scheduleGroupDao.getAll().associateBy { it.id }
        val toInsert = supplements
            .filter { it.id !in existing }
            .mapNotNull { s ->
                val group = groups[s.scheduleGroupId] ?: return@mapNotNull null
                DailyOccurrenceEntity(
                    supplementId = s.id,
                    scheduledDate = date,
                    scheduledHour = group.reminderHour,
                    scheduledMinute = group.reminderMinute,
                    scheduleGroupId = group.id
                )
            }
        if (toInsert.isNotEmpty()) occurrenceDao.insertAll(toInsert)
    }

    /** Creates occurrences for a specific past date for the given supplement list.
     *  Uses OnConflictStrategy.IGNORE — safe to call multiple times (idempotent). */
    suspend fun createOccurrencesForDate(supplements: List<SupplementEntity>, date: String) {
        val groups = scheduleGroupDao.getAll().associateBy { it.id }
        val toInsert = supplements.mapNotNull { s ->
            val group = groups[s.scheduleGroupId] ?: return@mapNotNull null
            DailyOccurrenceEntity(
                supplementId = s.id,
                scheduledDate = date,
                scheduledHour = group.reminderHour,
                scheduledMinute = group.reminderMinute,
                scheduleGroupId = group.id
            )
        }
        if (toInsert.isNotEmpty()) occurrenceDao.insertAll(toInsert)
    }

    suspend fun getOccurrenceSupplementIdsForDate(date: String): Set<Long> =
        occurrenceDao.getByDate(date).map { it.supplementId }.toSet()

    suspend fun setCompleted(supplementId: Long, date: String, completed: Boolean) {
        val completedAt = if (completed) System.currentTimeMillis() else null
        occurrenceDao.setCompletedBySupplementAndDate(supplementId, date, completed, completedAt)
    }

    suspend fun markGroupCompleted(groupId: Long, date: String) {
        val occurrences = occurrenceDao.getByGroupAndDate(groupId, date)
        val now = System.currentTimeMillis()
        occurrences.filter { !it.completed }.forEach { occ ->
            occurrenceDao.setCompleted(occ.id, true, now)
        }
    }

    suspend fun getOutstandingInGroup(groupId: Long, date: String): List<DailyOccurrenceEntity> =
        occurrenceDao.getByGroupAndDate(groupId, date).filter { !it.completed }

    suspend fun getOccurrencesInRange(startDate: String, endDate: String): List<DailyOccurrenceEntity> =
        occurrenceDao.getInRange(startDate, endDate)

    suspend fun getEarliestDate(): String? = occurrenceDao.getEarliest()?.scheduledDate

    suspend fun getActiveSupplementsList(): List<SupplementEntity> = supplementDao.getActive()

    suspend fun getActiveByGroup(groupId: Long): List<SupplementEntity> =
        supplementDao.getActiveByGroup(groupId)

    suspend fun getSupplementById(id: Long): SupplementEntity? = supplementDao.getById(id)

    suspend fun getAllOccurrenceDates(): List<String> = occurrenceDao.getAllDates()

    suspend fun createOccurrencesForNewSupplement(supplement: SupplementEntity, date: String) {
        val group = scheduleGroupDao.getById(supplement.scheduleGroupId) ?: return
        occurrenceDao.insert(
            DailyOccurrenceEntity(
                supplementId = supplement.id,
                scheduledDate = date,
                scheduledHour = group.reminderHour,
                scheduledMinute = group.reminderMinute,
                scheduleGroupId = group.id
            )
        )
    }
}
