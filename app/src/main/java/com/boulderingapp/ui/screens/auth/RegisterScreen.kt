package com.boulderingapp.ui.screens.auth

import com.boulderingapp.ui.components.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Ekran rejestracji:
@Composable
fun RegisterScreen(
    onNavigateToStart: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
                text = "Create Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirm Password",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(40.dp))

            CustomButton(
                text = "Sign Up",
                onClick = onRegisterSuccess
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Already have an account",
                color = PrimaryPurple,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateToLogin() }
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}