package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.domain.repository.AuthRepository

// TODO: Supabase backend disabled — local-first build. Re-enable in backend integration phase.
class AuthRepositoryImpl : AuthRepository {
    override suspend fun signUp(email: String, password: String): Result<Unit> =
        Result.failure(UnsupportedOperationException("Auth disabled"))
    override suspend fun signIn(email: String, password: String): Result<Unit> =
        Result.failure(UnsupportedOperationException("Auth disabled"))
    override suspend fun signOut(): Result<Unit> =
        Result.failure(UnsupportedOperationException("Auth disabled"))
    override fun currentUserId(): String? = null
    override fun isAuthenticated(): Boolean = false
}
