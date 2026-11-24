package com.boulderingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boulderingapp.ui.components.SettingsHost
import com.boulderingapp.ui.components.rememberSettingsState
import com.boulderingapp.ui.screens.auth.ForgotPasswordScreen
import com.boulderingapp.ui.screens.auth.LoginScreen
import com.boulderingapp.ui.screens.auth.RegisterScreen
import com.boulderingapp.ui.screens.auth.ResetPasswordScreen
import com.boulderingapp.ui.screens.auth.StartingScreen
import com.boulderingapp.ui.screens.profile.ProfileScreen
import com.boulderingapp.ui.screens.profile.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val settingsState = rememberSettingsState()

            NavHost(
                navController = navController,
                startDestination = "starting_screen"
            ) {
                composable("starting_screen") {
                    StartingScreen(
                        onNavigateToLogin = { navController.navigate("login_screen") },
                        onNavigateToRegister = { navController.navigate("register_screen") }
                    )
                }
                composable("login_screen") {
                    LoginScreen(
                        onNavigateToRegister = { navController.navigate("register_screen") },
                        onNavigateToForgotPassword = { navController.navigate("forgot_password_screen") },
                        onNavigateToStart = { navController.navigate("starting_screen") },
                        onLoginClick = { email, password ->
                            // TODO: Tutaj w przyszłości podepniesz FirebaseAuth.signInWithEmailAndPassword(email, password)
                            // Po przejściu walidacji z LoginScreen na razie przenosimy od razu na profil:
                            navController.navigate("profile_screen") {
                                popUpTo("starting_screen") { inclusive = true }
                            }
                        }
                    )
                }
                composable("register_screen") {
                    RegisterScreen(
                        onNavigateToLogin = { navController.navigate("login_screen") },
                        onNavigateToStart = { navController.navigate("starting_screen") }
                    )
                }
                composable("forgot_password_screen") {
                    ForgotPasswordScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onSendCodeClick = { email -> },
                        onNavigateToResetPassword = { navController.navigate("reset_password_screen") }
                    )
                }
                composable("reset_password_screen") {
                    ResetPasswordScreen(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable("profile_screen") {
                    val viewModel: ProfileViewModel = viewModel()
                    val uiState by viewModel.uiState.collectAsState()

                    ProfileScreen(
                        uiState = uiState,
                        onPeriodSelected = viewModel::onPeriodSelected,
                        onEditProfileClick = {
                            // TODO: navController.navigate("edit_profile_screen")
                        },
                        onSettingsClick = settingsState::open
                    )
                }
            }

            SettingsHost(
                state = settingsState,
                onLogout = {
                    navController.navigate("starting_screen") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}