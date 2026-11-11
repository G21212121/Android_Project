package com.boulderingapp.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.boulderingapp.ui.components.*

//Ekran Startowy
@Composable
fun StartingScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            HeaderLogo()

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Bouldering\nTraining App",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                lineHeight = 36.sp,
                modifier = Modifier.padding(bottom = 60.dp)
            )

            CustomButton(text = "Login", onClick = onNavigateToLogin)

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(text = "Register", onClick = onNavigateToRegister)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "for new users only",
                color = PrimaryPurple,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Funkcja podglądu do testowania w edytorze
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartingScreenPreview() {
    StartingScreen()
}