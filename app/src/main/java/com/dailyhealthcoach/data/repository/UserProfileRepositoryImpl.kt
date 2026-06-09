package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.UserProfileDao
import com.dailyhealthcoach.data.local.entity.UserProfileEntity
import com.dailyhealthcoach.domain.model.UserProfile
import com.dailyhealthcoach.domain.repository.UserProfileRepository
import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfileRepositoryImpl(
    private val userProfileDao: UserProfileDao
) : UserProfileRepository {
    override fun observeUserProfile(): Flow<UserProfile?> {
        return userProfileDao.observeUserProfile().map { it?.toDomain() }
    }

    override suspend fun saveProfile(profile: UserProfile) {
        val now = Instant.now().toString()
        val existing = userProfileDao.getProfile()
        userProfileDao.upsert(
            UserProfileEntity(
                id = 1,
                name = profile.name,
                heightInches = profile.heightInches,
                birthDate = profile.birthDate,
                bedtime = profile.bedtime,
                age = profile.age,
                sex = profile.sex,
                weightGoalPounds = profile.weightGoalPounds,
                bodyFatGoalPercent = profile.bodyFatGoalPercent,
                stepMinTarget = profile.stepMinTarget,
                stepMaxTarget = profile.stepMaxTarget,
                sleepTargetHours = profile.sleepTargetHours,
                strengthTrainingDaysPerWeek = profile.strengthTrainingDaysPerWeek,
                createdAt = existing?.createdAt ?: now,
                updatedAt = now
            )
        )
    }
}

private fun UserProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        id = id,
        name = name,
        heightInches = heightInches,
        birthDate = birthDate,
        bedtime = bedtime,
        age = age,
        sex = sex,
        weightGoalPounds = weightGoalPounds,
        bodyFatGoalPercent = bodyFatGoalPercent,
        stepMinTarget = stepMinTarget,
        stepMaxTarget = stepMaxTarget,
        sleepTargetHours = sleepTargetHours,
        strengthTrainingDaysPerWeek = strengthTrainingDaysPerWeek
    )
}
