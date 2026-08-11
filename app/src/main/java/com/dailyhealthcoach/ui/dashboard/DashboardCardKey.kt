package com.dailyhealthcoach.ui.dashboard

enum class DashboardCardKey(val displayName: String) {
    HEALTH_SCORE("Health Score"),   // required, always fixed at top
    RECOVERY("Recovery"),
    WORKOUT("Workout"),
    NUTRITION("Nutrition"),
    HABITS("Habits"),
    STEPS("Steps"),
    SLEEP("Sleep"),
    WEIGHT("Weight"),
    BODY_FAT("Body Fat"),
    TOMORROW("Tomorrow's Plan"),
    VIEW_PROGRESS("Progress & Trends")
}

data class DashboardCardConfig(
    val key: DashboardCardKey,
    val visible: Boolean,
    val order: Int
)
