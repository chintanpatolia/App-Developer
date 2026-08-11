package com.dailyhealthcoach.domain.repository

interface AuthRepository {
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
    fun currentUserId(): String?
    fun isAuthenticated(): Boolean
}
