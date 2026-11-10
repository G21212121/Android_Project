package com.boulderingapp.ui.components

import android.graphics.RuntimeShader
import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import com.boulderingapp.R

// --- Kolory ---
val PrimaryPurple = Color(0xFF4A44A5)
val InputFieldBg = Color(0xFFF7F7F7)

// --- Tło z obsługą Shadera AGSL ---
val WaveBackground = Color(0xFFEAF2FB) // Kolor tła z Twoich suwaków z Figmy

@Composable
fun AppBackground(content: @Composable BoxScope.() -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val context = LocalContext.current
            val rawShader = remember {
                try {
                    context.resources.openRawResource(R.raw.groovy_waves)
                        .bufferedReader().use { it.readText() }
                } catch (e: Exception) {
                    null
                }
            }

            if (rawShader != null) {
                val shader = remember(rawShader) { RuntimeShader(rawShader) }
                var time by remember { mutableFloatStateOf(0f) }

                LaunchedEffect(Unit) {
                    val startTime = System.currentTimeMillis()
                    while (true) {
                        time = (System.currentTimeMillis() - startTime) / 1000f
                        withFrameNanos { }
                    }
                }

                Canvas(modifier = Modifier.fillMaxSize()) {
                    shader.setFloatUniform("iResolution", size.width, size.height)
                    shader.setFloatUniform("iTime", time)
                    shader.setFloatUniform("darkColor", 0.106f, 0.294f, 0.478f, 1.0f)
                    shader.setFloatUniform("medColor", 0.357f, 0.608f, 0.835f, 1.0f)
                    shader.setFloatUniform("lightColor", 0.659f, 0.816f, 0.941f, 1.0f)
                    shader.setFloatUniform("bgColor", 0.918f, 0.949f, 0.984f, 1.0f)
                    shader.setFloatUniform("waveScale", 1.5f)
                    shader.setFloatUniform("waviness", 1.0f)
                    shader.setFloatUniform("angle", 180.0f)
                    shader.setIntUniform("direction", 2)

                    drawRect(brush = ShaderBrush(shader))
                }
            } else {
                // Tło jednolite w razie błędu shadera
                Box(modifier = Modifier.fillMaxSize().background(WaveBackground))
            }
        } else {
            // Tło jednolite dla urządzeń ze starszym Androidem
            Box(modifier = Modifier.fillMaxSize().background(WaveBackground))
        }

        content()
    }
}

// --- Logo na górze ---
@Composable
fun HeaderLogo() {
    Box(
        modifier = Modifier
            .padding(top = 40.dp)
            .size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.boulder_logo ),
            contentDescription = "Climber Logo",
            modifier = Modifier.fillMaxSize()
        )
    }
}

// --- Przycisk ---
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth(0.65f)
            .height(48.dp)
    ) {
        Text(
            text = text,
            color = PrimaryPurple,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// --- Pole tekstowe ---
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = InputFieldBg,
            unfocusedContainerColor = InputFieldBg,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .height(52.dp)
    )
}