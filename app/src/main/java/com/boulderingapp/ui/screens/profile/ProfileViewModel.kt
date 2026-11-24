package com.boulderingapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    // TODO: Wstrzyknij tutaj swoje repozytorium (np. private val profileRepository: ProfileRepository)
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData(TimePeriod.TOTAL)
    }

    fun onPeriodSelected(period: TimePeriod) {
        _uiState.update { it.copy(selectedPeriod = period) }
        loadProfileData(period)
    }

    private fun loadProfileData(period: TimePeriod) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // TODO: Zastąp poniższe dane wywołaniem Firebase / Room dla wybranego `period`
            val mockPyramid = when (period) {
                TimePeriod.TOTAL -> defaultGradePyramid()
                TimePeriod.LAST_WEEK -> listOf(
                    GradePyramidItem("4C", 0),
                    GradePyramidItem("5A", 2),
                    GradePyramidItem("5A+", 1),
                    GradePyramidItem("5B", 3),
                    GradePyramidItem("5B+", 1),
                    GradePyramidItem("5C", 4),
                    GradePyramidItem("5C+", 2),
                    GradePyramidItem("6A", 1),
                    GradePyramidItem("6A+", 0),
                    GradePyramidItem("6B", 0),
                    GradePyramidItem("6B+", 0)
                )
                TimePeriod.LAST_MONTH -> listOf(
                    GradePyramidItem("4C", 1),
                    GradePyramidItem("5A", 4),
                    GradePyramidItem("5A+", 2),
                    GradePyramidItem("5B", 6),
                    GradePyramidItem("5B+", 3),
                    GradePyramidItem("5C", 8),
                    GradePyramidItem("5C+", 10),
                    GradePyramidItem("6A", 7),
                    GradePyramidItem("6A+", 3),
                    GradePyramidItem("6B", 1),
                    GradePyramidItem("6B+", 0)
                )
                TimePeriod.LAST_YEAR -> defaultGradePyramid()
            }

            val highest = mockPyramid.lastOrNull { it.count > 0 }?.grade ?: "-"

            _uiState.update {
                it.copy(
                    username = "Profile name",
                    highestGrade = highest,
                    topCount = when (period) {
                        TimePeriod.TOTAL -> 303
                        TimePeriod.LAST_WEEK -> 14
                        TimePeriod.LAST_MONTH -> 45
                        TimePeriod.LAST_YEAR -> 224
                    },
                    flashCount = when (period) {
                        TimePeriod.TOTAL -> 147
                        TimePeriod.LAST_WEEK -> 6
                        TimePeriod.LAST_MONTH -> 21
                        TimePeriod.LAST_YEAR -> 109
                    },
                    gradePyramid = mockPyramid,
                    isLoading = false
                )
            }
        }
    }
}