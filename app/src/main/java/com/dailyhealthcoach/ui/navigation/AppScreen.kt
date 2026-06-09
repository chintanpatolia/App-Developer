package com.dailyhealthcoach.ui.navigation

enum class AppScreen(val label: String, val showInNav: Boolean = true) {
    DASHBOARD("Dashboard"),
    HABITS("Habits"),
    WORKOUT("Workout"),
    NUTRITION("Nutrition"),
    BODY("Body"),
    PROGRESS("Progress", showInNav = false),
    SETTINGS("Settings", showInNav = false),
    REMINDERS("Reminders", showInNav = false)
}
