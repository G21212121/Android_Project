package com.boulderingapp.ui.screens.profile

enum class TimePeriod(val label: String) {
    TOTAL("Total"),
    LAST_WEEK("Last week"),
    LAST_MONTH("Last month"),
    LAST_YEAR("Last year")
}

data class GradePyramidItem(
    val grade: String,
    val count: Int
)

data class ProfileUiState(
    val username: String = "Profile name",
    val avatarUrl: String? = null,
    val highestGrade: String = "6A+",
    val selectedPeriod: TimePeriod = TimePeriod.TOTAL,
    val topCount: Int = 303,
    val flashCount: Int = 147,
    val gradePyramid: List<GradePyramidItem> = defaultGradePyramid(),
    val isLoading: Boolean = false
)

fun defaultGradePyramid(): List<GradePyramidItem> = listOf(
    GradePyramidItem("4C", 3),
    GradePyramidItem("5A", 16),
    GradePyramidItem("5A+", 8),
    GradePyramidItem("5B", 24),
    GradePyramidItem("5B+", 12),
    GradePyramidItem("5C", 29),
    GradePyramidItem("5C+", 43),
    GradePyramidItem("6A", 51),
    GradePyramidItem("6A+", 29),
    GradePyramidItem("6B", 9),
    GradePyramidItem("6B+", 0)
)