package com.boulderingapp.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boulderingapp.ui.components.AppBackground
import com.boulderingapp.ui.components.CustomButton
import com.boulderingapp.ui.components.CustomTextField
import com.boulderingapp.ui.components.HeaderLogo
import com.boulderingapp.ui.components.PrimaryPurple
import com.boulderingapp.utils.PasswordValidator

//Ekran resetu halsa:
@Composable
fun ResetPasswordScreen(
    onNavigateBack: () -> Unit = {},
    onResetPasswordClick: (String) -> Unit = { _-> }
) {
    var new_password by remember { mutableStateOf("") }
    var confirm_new_password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                fontSize = 28.sp,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomTextField(
                value = new_password,
                onValueChange = { new_password = it },
                placeholder = "New Password",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = confirm_new_password,
                onValueChange = { confirm_new_password = it },
                placeholder = "Confirm New Password",
                isPassword = true
            )

            errorMessage?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            CustomButton(
                text = "Change Password",
                onClick = {
                    val error = PasswordValidator.validate(new_password, confirm_new_password)

                    if(error != null){
                        errorMessage = error
                    } else{
                        errorMessage = null
                        onResetPasswordClick(new_password)
                    }
                }
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
fun ResetPasswordScreenPreview() {
    ResetPasswordScreen()
}