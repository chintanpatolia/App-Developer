package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.RecoveryScoreDao
import com.dailyhealthcoach.data.local.entity.RecoveryScoreEntity
import com.dailyhealthcoach.domain.model.RecoveryScore
import com.dailyhealthcoach.domain.repository.RecoveryRepository
import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecoveryRepositoryImpl(
    private val recoveryScoreDao: RecoveryScoreDao
) : RecoveryRepository {
    override fun observeForDate(date: String): Flow<RecoveryScore?> {
        return recoveryScoreDao.observeForDate(date).map { it?.toDomain() }
    }

    override suspend fun saveForDate(date: String, score: Int, label: String, reasons: List<String>) {
        val now = Instant.now().toString()
        val existing = recoveryScoreDao.getForDate(date)
        recoveryScoreDao.upsert(
            RecoveryScoreEntity(
                id = existing?.id ?: 0,
                date = date,
                score = score,
                label = label,
                reasonText = reasons.joinToString("\n"),
                sleepContribution = existing?.sleepContribution ?: 0,
                proteinContribution = existing?.proteinContribution ?: 0,
                sorenessContribution = existing?.sorenessContribution ?: 0,
                stressContribution = existing?.stressContribution ?: 0,
                workoutContribution = existing?.workoutContribution ?: 0,
                stepsContribution = existing?.stepsContribution ?: 0,
                restDayContribution = existing?.restDayContribution ?: 0,
                notes = existing?.notes,
                createdAt = existing?.createdAt ?: now,
                updatedAt = now
            )
        )
    }
}

private fun RecoveryScoreEntity.toDomain(): RecoveryScore {
    return RecoveryScore(
        id = id,
        date = date,
        score = score,
        label = label,
        reasons = reasonText?.lines()?.filter { it.isNotBlank() }.orEmpty(),
        notes = notes
    )
}
