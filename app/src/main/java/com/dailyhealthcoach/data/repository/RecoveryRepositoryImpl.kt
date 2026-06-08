package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.RecoveryScoreDao
import com.dailyhealthcoach.data.local.entity.RecoveryScoreEntity
import com.dailyhealthcoach.domain.model.RecoveryScore
import com.dailyhealthcoach.domain.repository.RecoveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecoveryRepositoryImpl(
    private val recoveryScoreDao: RecoveryScoreDao
) : RecoveryRepository {
    override fun observeForDate(date: String): Flow<RecoveryScore?> {
        return recoveryScoreDao.observeForDate(date).map { it?.toDomain() }
    }
}

private fun RecoveryScoreEntity.toDomain(): RecoveryScore {
    return RecoveryScore(
        id = id,
        date = date,
        score = score,
        notes = notes
    )
}
