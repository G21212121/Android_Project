package com.boulderingapp.ui.screens.auth

import com.boulderingapp.ui.components.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boulderingapp.utils.PasswordValidator
// Ekran logowania:
@Composable
fun LoginScreen(
    onNavigateToStart: () -> Unit = {},
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onNavigateToRegister: () -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {},
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Box(modifier = Modifier.clickable { onNavigateToStart() }) {
                HeaderLogo()
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Log In",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                isPassword = true,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Forgot the password",
                color = PrimaryPurple,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateToForgotPassword() }
            )

            errorMessage?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(
                text = "Login",
                onClick = {
                    val error = PasswordValidator.validateLogin(email, password)

                    if (error != null) {
                        errorMessage = error
                    } else {
                        errorMessage = null
                        onLoginClick(email, password)
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Don't have an account",
                color = PrimaryPurple,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateToRegister() }
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}