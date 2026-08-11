package com.dailyhealthcoach.ui.auth

data class AuthUiState(
    val isAuthenticated: Boolean = false,
    val userEmail: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
