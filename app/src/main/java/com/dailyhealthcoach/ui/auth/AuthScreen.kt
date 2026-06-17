package com.dailyhealthcoach.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dailyhealthcoach.domain.repository.AuthRepository
import com.dailyhealthcoach.ui.theme.AccentBlue
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PositiveAccent
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard
import com.dailyhealthcoach.ui.theme.WarningAccent

@Composable
fun AuthRoute(
    authRepository: AuthRepository,
    onBack: () -> Unit,
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) onAuthSuccess()
    }

    AuthScreen(
        uiState = uiState,
        onSignIn = viewModel::signIn,
        onSignUp = viewModel::signUp,
        onBack = onBack,
        onClearMessages = viewModel::clearMessages,
        modifier = modifier
    )
}

@Composable
private fun AuthScreen(
    uiState: AuthUiState,
    onSignIn: (String, String) -> Unit,
    onSignUp: (String, String) -> Unit,
    onBack: () -> Unit,
    onClearMessages: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isSignUpMode by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isSignUpMode) {
        localError = null
        onClearMessages()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("← Back", color = CyanAccent)
                }
                Text(
                    text = if (isSignUpMode) "Create Account" else "Sign In",
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Box(modifier = Modifier.padding(horizontal = 16.dp))
            }

            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Cloud Backup & Sync",
                        color = PrimaryText,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Your data stays on device. Signing in enables optional cloud backup and cross-device sync.",
                        color = MutedText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; localError = null; onClearMessages() },
                        label = { Text("Email", color = MutedText) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentBlue,
                            unfocusedBorderColor = MutedText,
                            focusedTextColor = PrimaryText,
                            unfocusedTextColor = PrimaryText,
                            cursorColor = CyanAccent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; localError = null; onClearMessages() },
                        label = { Text("Password", color = MutedText) },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentBlue,
                            unfocusedBorderColor = MutedText,
                            focusedTextColor = PrimaryText,
                            unfocusedTextColor = PrimaryText,
                            cursorColor = CyanAccent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (isSignUpMode) {
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it; localError = null },
                            label = { Text("Confirm Password", color = MutedText) },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentBlue,
                                unfocusedBorderColor = MutedText,
                                focusedTextColor = PrimaryText,
                                unfocusedTextColor = PrimaryText,
                                cursorColor = CyanAccent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    val displayError = localError ?: uiState.errorMessage
                    if (displayError != null) {
                        Text(
                            text = displayError,
                            color = WarningAccent,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if (uiState.successMessage != null) {
                        Text(
                            text = uiState.successMessage,
                            color = PositiveAccent,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                localError = "Email and password are required."
                                return@Button
                            }
                            if (isSignUpMode) {
                                if (password != confirmPassword) {
                                    localError = "Passwords do not match."
                                    return@Button
                                }
                                if (password.length < 8) {
                                    localError = "Password must be at least 8 characters."
                                    return@Button
                                }
                                onSignUp(email.trim(), password)
                            } else {
                                onSignIn(email.trim(), password)
                            }
                        },
                        enabled = !uiState.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                color = PrimaryText,
                                strokeWidth = 2.dp,
                                modifier = Modifier.height(18.dp)
                            )
                        } else {
                            Text(
                                text = if (isSignUpMode) "Create Account" else "Sign In",
                                color = PrimaryText,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isSignUpMode) "Already have an account?" else "New here?",
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall
                )
                TextButton(onClick = {
                    isSignUpMode = !isSignUpMode
                    password = ""
                    confirmPassword = ""
                }) {
                    Text(
                        text = if (isSignUpMode) "Sign In" else "Create Account",
                        color = CyanAccent,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
