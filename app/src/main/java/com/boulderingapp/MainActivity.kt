package com.boulderingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boulderingapp.ui.screens.auth.ForgotPasswordScreen
import com.boulderingapp.ui.screens.auth.LoginScreen
import com.boulderingapp.ui.screens.auth.RegisterScreen
import com.boulderingapp.ui.screens.auth.StartingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

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
                        onNavigateToForgotPassword = {navController.navigate("forgot_password_screen")},
                        onNavigateToStart = {navController.navigate("starting_screen")}
                    )
                }
                composable("register_screen") {
                    RegisterScreen(
                        onNavigateToLogin = { navController.navigate("login_screen") },
                        onNavigateToStart = {navController.navigate("starting_screen")}
                    )
                }
                composable("forgot_password_screen") {
                    ForgotPasswordScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onSendCodeClick = { email ->
                        }
                    )
                }
            }
        }
    }
}