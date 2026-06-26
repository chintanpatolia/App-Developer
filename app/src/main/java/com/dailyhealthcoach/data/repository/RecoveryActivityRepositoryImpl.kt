package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.RecoveryActivityDao
import com.dailyhealthcoach.data.local.entity.RecoveryActivityEntity
import com.dailyhealthcoach.domain.model.RecoveryActivity
import com.dailyhealthcoach.domain.repository.RecoveryActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecoveryActivityRepositoryImpl(
    private val dao: RecoveryActivityDao
) : RecoveryActivityRepository {

    override fun observeAll(): Flow<List<RecoveryActivity>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun saveAll(workoutId: Long, activities: List<RecoveryActivity>) {
        dao.insertAll(activities.map { activity ->
            RecoveryActivityEntity(
                workoutId = workoutId,
                activityName = activity.name,
                status = activity.status,
                durationSeconds = activity.durationSeconds,
                rpe = activity.rpe,
                notes = activity.notes
            )
        })
    }
}

private fun RecoveryActivityEntity.toDomain() = RecoveryActivity(
    id = id,
    workoutId = workoutId,
    name = activityName,
    status = status,
    durationSeconds = durationSeconds ?: durationMinutes?.let { it * 60 },
    rpe = rpe,
    notes = notes
)
