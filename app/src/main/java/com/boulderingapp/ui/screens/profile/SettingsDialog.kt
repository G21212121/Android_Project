package com.boulderingapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val DialogBg = Color(0xFF2B2B2B)
private val DialogBorder = Color(0xFF555555)
private val ActionRed = Color(0xFFA81212)
private val ThemeAccentBlue = Color(0xFF3B49DF)
private val LightGreyButton = Color(0xFFE0E0E0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(
    isDarkTheme: Boolean,
    volume: Float,
    onDismiss: () -> Unit,
    onToggleTheme: () -> Unit,
    onVolumeChange: (Float) -> Unit,
    onChangePasswordClick: () -> Unit,
    onLogOutClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp),
            shape = RoundedCornerShape(20.dp),
            color = DialogBg,
            border = BorderStroke(1.dp, DialogBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header: "Settings" + red "X" button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Settings",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(ActionRed)
                            .clickable(onClick = onDismiss),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close settings",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 1. Theme Button
                Button(
                    onClick = onToggleTheme,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = ThemeAccentBlue
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    ThemeIcon(
                        isDarkTheme = isDarkTheme,
                        tint = ThemeAccentBlue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Theme",
                        color = ThemeAccentBlue,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 2. Volume Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Volume",
                        color = Color(0xFFE0E0E0),
                        fontSize = 14.sp
                    )
                    Text(
                        text = "${(volume * 100).toInt()}%",
                        color = Color(0xFFE0E0E0),
                        fontSize = 14.sp
                    )
                }

                Slider(
                    value = volume,
                    onValueChange = onVolumeChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color(0xFF555555)
                    ),
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color.White, CircleShape)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 3. CHANGE PASSWORD Button
                Button(
                    onClick = onChangePasswordClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGreyButton,
                        contentColor = Color(0xFF1A1A1A)
                    )
                ) {
                    Text(
                        text = "CHANGE PASSWORD",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 4. LOG OUT Button
                Button(
                    onClick = onLogOutClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ActionRed,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "LOG OUT",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeIcon(
    isDarkTheme: Boolean,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        if (isDarkTheme) {
            // Sun icon
            val coreRadius = size.minDimension * 0.22f
            drawCircle(color = tint, radius = coreRadius, center = center)
            val innerRay = size.minDimension * 0.33f
            val outerRay = size.minDimension * 0.46f
            for (i in 0 until 8) {
                val angle = (i * 45.0) * (PI / 180.0)
                val start = Offset(
                    x = center.x + (innerRay * cos(angle)).toFloat(),
                    y = center.y + (innerRay * sin(angle)).toFloat()
                )
                val end = Offset(
                    x = center.x + (outerRay * cos(angle)).toFloat(),
                    y = center.y + (outerRay * sin(angle)).toFloat()
                )
                drawLine(
                    color = tint,
                    start = start,
                    end = end,
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        } else {
            // Crescent moon icon
            val moonRadius = size.minDimension * 0.4f
            drawCircle(color = tint, radius = moonRadius, center = center)
            drawCircle(
                color = Color.White,
                radius = moonRadius * 0.85f,
                center = Offset(center.x + moonRadius * 0.45f, center.y - moonRadius * 0.25f)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsDialogPreview() {
    SettingsDialog(
        isDarkTheme = true,
        volume = 0.75f,
        onDismiss = {},
        onToggleTheme = {},
        onVolumeChange = {},
        onChangePasswordClick = {},
        onLogOutClick = {}
    )
}