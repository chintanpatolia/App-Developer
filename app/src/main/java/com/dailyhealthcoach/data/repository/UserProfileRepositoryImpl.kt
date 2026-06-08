package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.UserProfileDao
import com.dailyhealthcoach.data.local.entity.UserProfileEntity
import com.dailyhealthcoach.domain.model.UserProfile
import com.dailyhealthcoach.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfileRepositoryImpl(
    private val userProfileDao: UserProfileDao
) : UserProfileRepository {
    override fun observeUserProfile(): Flow<UserProfile?> {
        return userProfileDao.observeUserProfile().map { it?.toDomain() }
    }
}

private fun UserProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        id = id,
        name = name,
        heightInches = heightInches,
        birthDate = birthDate,
        bedtime = bedtime
    )
}
