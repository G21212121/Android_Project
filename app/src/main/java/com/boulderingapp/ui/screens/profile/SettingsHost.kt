package com.boulderingapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Stable
class SettingsState(
    initialDarkTheme: Boolean = true,
    initialVolume: Float = 0.75f
) {
    var isVisible by mutableStateOf(false)
        private set

    var isDarkTheme by mutableStateOf(initialDarkTheme)
        private set

    var volume by mutableFloatStateOf(initialVolume)
        private set

    fun open() {
        isVisible = true
    }

    fun dismiss() {
        isVisible = false
    }

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
        // TODO: Zapisz wybór motywu lokalnie w DataStore
    }

    fun updateVolume(newVolume: Float) {
        volume = newVolume
        // TODO: Zapisz głośność w DataStore / zaktualizuj AudioManager
    }
}

@Composable
fun rememberSettingsState(
    initialDarkTheme: Boolean = true,
    initialVolume: Float = 0.75f
): SettingsState = remember {
    SettingsState(
        initialDarkTheme = initialDarkTheme,
        initialVolume = initialVolume
    )
}

/**
 * Pojedyncza funkcja wyświetlająca popout Settings nad całą aplikacją.
 */
@Composable
fun SettingsHost(
    state: SettingsState,
    onLogout: () -> Unit
) {
    if (state.isVisible) {
        SettingsDialog(
            isDarkTheme = state.isDarkTheme,
            volume = state.volume,
            onDismiss = state::dismiss,
            onToggleTheme = state::toggleTheme,
            onVolumeChange = state::updateVolume,
            onChangePasswordClick = {
                // TODO: Otwórz dialog zmiany hasła (PasswordValidator + FirebaseAuth)
            },
            onLogOutClick = {
                state.dismiss()
                // TODO: FirebaseAuth.getInstance().signOut()
                onLogout()
            }
        )
    }
}

/**
 * Opcjonalny gotowy pasek z ikoną zębatki.
 */
@Composable
fun SettingsTopBar(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(onClick = onSettingsClick) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                tint = Color(0xFFBDBDBD),
                modifier = Modifier.size(34.dp)
            )
        }
    }
}
