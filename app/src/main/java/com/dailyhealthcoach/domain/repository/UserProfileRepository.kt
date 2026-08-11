package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun observeUserProfile(): Flow<UserProfile?>
    suspend fun saveProfile(profile: UserProfile)
}
