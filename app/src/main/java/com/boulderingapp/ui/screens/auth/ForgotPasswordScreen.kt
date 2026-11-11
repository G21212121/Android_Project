package com.boulderingapp.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boulderingapp.ui.components.AppBackground
import com.boulderingapp.ui.components.CustomButton
import com.boulderingapp.ui.components.HeaderLogo

@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit = {},
    onSendCodeClick: (String) -> Unit = {},
    onResetPasswordClick: (String, String) -> Unit = { _, _ -> }
) {
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var isCodeSent by remember { mutableStateOf(false) }

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            HeaderLogo()

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Password Reset",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your email address to receive a code to reset your password.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email address") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxHeight()
                )

                Spacer(modifier = Modifier.width(8.dp))

                CustomButton(
                    text = if (isCodeSent) "Send again" else "Send code",
                    onClick = {
                        isCodeSent = true
                        onSendCodeClick(email)
                    },
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Code") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                text = "Create new password",
                onClick = { onResetPasswordClick(email, code) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "Back",
                onClick = onNavigateBack
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen()
}