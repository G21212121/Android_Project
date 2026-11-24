package com.boulderingapp.ui.screens.profile

import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.boulderingapp.R
import com.boulderingapp.ui.components.AppBackground

private val CardDarkBg = Color(0xFF262728).copy(alpha = 0.94f)
private val InnerSurfaceGrey = Color(0xFF5A5A5A)
private val BadgeRed = Color(0xFFB91C1C)
private val BarRed = Color(0xFFFF1515)
private val MutedText = Color(0xFFBDBDBD)

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onPeriodSelected: (TimePeriod) -> Unit,
    onEditProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit = {}
) {
    AppBackground(
        darkColor = Color(0xFF191818),
        medColor = Color(0xFF1C1D1E),
        lightColor = Color(0xFF212326),
        bgColor = Color(0xFF000000)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = bottomBar,
            modifier = modifier.fillMaxSize()
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = 240.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // Górny pasek z zębatką po prawej stronie
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        IconButton(onClick = onSettingsClick) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "Settings",
                                tint = MutedText,
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }
                }

                // 1. Karta profilu (Avatar + odznaka stopnia + nazwa + EDIT PROFILE)
                item {
                    ProfileHeaderCard(
                        username = uiState.username,
                        avatarUrl = uiState.avatarUrl,
                        highestGrade = uiState.highestGrade,
                        onEditProfileClick = onEditProfileClick
                    )
                }

                // 2. Piramida stopni (pionowe słupki)
                item {
                    GradePyramidCard(
                        items = uiState.gradePyramid,
                        isLoading = uiState.isLoading
                    )
                }

                // 3. Sekcja Total (rozwijana lista + Top i Flash)
                item {
                    StatsSectionCard(
                        selectedPeriod = uiState.selectedPeriod,
                        topCount = uiState.topCount,
                        flashCount = uiState.flashCount,
                        onPeriodSelected = onPeriodSelected
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileHeaderCard(
    username: String,
    avatarUrl: String?,
    highestGrade: String,
    onEditProfileClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardDarkBg)
            .padding(vertical = 28.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar z nachodzącą od dołu czerwoną pigułką ze stopniem
        Box(
            modifier = Modifier.padding(bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(164.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "User Avatar",
                    placeholder = painterResource(id = R.drawable.default_avatar),
                    error = painterResource(id = R.drawable.default_avatar),
                    fallback = painterResource(id = R.drawable.default_avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(164.dp)
                        .clip(CircleShape)
                )
            }

            if (highestGrade.isNotBlank()) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 12.dp),
                    color = BadgeRed,
                    shape = CircleShape
                ) {
                    Text(
                        text = highestGrade,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = username,
            color = Color(0xFF9E9E9E),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = onEditProfileClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = InnerSurfaceGrey,
                contentColor = MutedText
            ),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp)
        ) {
            Text(
                text = "EDIT PROFILE",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
private fun GradePyramidCard(
    items: List<GradePyramidItem>,
    isLoading: Boolean
) {
    val maxCount = remember(items) { (items.maxOfOrNull { it.count } ?: 1).coerceAtLeast(1) }
    val maxBarHeight = 165.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardDarkBg)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = "Grade Pyramid",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxBarHeight + 48.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = BarRed)
            }
        } else if (items.isEmpty()) {
            Text(
                text = "No climbs recorded for this period.",
                color = MutedText,
                fontSize = 14.sp
            )
        } else {
            // Pionowe słupki + czerwone pigułki z liczbami
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxBarHeight + 28.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                items.forEach { item ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        if (item.count > 0) {
                            Box(
                                modifier = Modifier
                                    .background(BadgeRed, CircleShape)
                                    .padding(horizontal = 5.dp, vertical = 1.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.count.toString(),
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    softWrap = false
                                )
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            val heightFraction = (item.count.toFloat() / maxCount.toFloat())
                                .coerceIn(0.04f, 1f)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(maxBarHeight * heightFraction)
                                    .background(
                                        color = BarRed,
                                        shape = RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)
                                    )
                            )
                        }
                    }
                }
            }

            // Linia bazowa wykresu
            HorizontalDivider(
                thickness = 1.5.dp,
                color = Color(0xFF6E6E6E)
            )

            // Etykiety stopni pod osią
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items.forEach { item ->
                    Text(
                        text = item.grade,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        color = Color(0xFFD6D6D6),
                        fontSize = 10.sp,
                        maxLines = 1,
                        softWrap = false
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsSectionCard(
    selectedPeriod: TimePeriod,
    topCount: Int,
    flashCount: Int,
    onPeriodSelected: (TimePeriod) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardDarkBg)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        // Rozwijany przycisk wyboru okresu ("Total")
        Column {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(InnerSurfaceGrey)
                    .clickable { expanded = true }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedPeriod.label,
                    color = Color(0xFFD6D6D6),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Select period",
                    tint = MutedText,
                    modifier = Modifier.size(18.dp)
                )
            }

            Box {
                if (expanded) {
                    Popup(
                        alignment = Alignment.TopStart,
                        onDismissRequest = { expanded = false },
                        properties = PopupProperties(focusable = true)
                    ) {
                        Surface(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .width(150.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF333333),
                            shadowElevation = 8.dp
                        ) {
                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                TimePeriod.entries.forEach { period ->
                                    Text(
                                        text = period.label,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                expanded = false
                                                onPeriodSelected(period)
                                            }
                                            .padding(horizontal = 14.dp, vertical = 9.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Wewnętrzny boks ze statystykami Top i Flash
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = InnerSurfaceGrey,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Top: $topCount",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Flash: $flashCount",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        uiState = ProfileUiState(
            username = "Profile name",
            avatarUrl = null,
            highestGrade = "6A+",
            selectedPeriod = TimePeriod.TOTAL,
            topCount = 303,
            flashCount = 147,
            gradePyramid = defaultGradePyramid(),
            isLoading = false
        ),
        onPeriodSelected = {},
        onEditProfileClick = {},
        onSettingsClick = {}
    )
}