package com.dailyhealthcoach.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dailyhealthcoach.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AuthUiState(isAuthenticated = authRepository.isAuthenticated())
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, successMessage = null) }
            authRepository.signIn(email, password)
                .onSuccess {
                    _uiState.update { it.copy(
                        isLoading = false,
                        isAuthenticated = true,
                        userEmail = email,
                        successMessage = "Signed in successfully"
                    )}
                }
                .onFailure { e ->
                    _uiState.update { it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Sign in failed. Check your credentials."
                    )}
                }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, successMessage = null) }
            authRepository.signUp(email, password)
                .onSuccess {
                    _uiState.update { it.copy(
                        isLoading = false,
                        isAuthenticated = authRepository.isAuthenticated(),
                        userEmail = email,
                        successMessage = "Account created. You can now sign in."
                    )}
                }
                .onFailure { e ->
                    _uiState.update { it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Sign up failed. Try a different email."
                    )}
                }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _uiState.update { it.copy(
                isAuthenticated = false,
                userEmail = null,
                errorMessage = null,
                successMessage = null
            )}
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }
}

class AuthViewModelFactory(
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
