package com.dailyhealthcoach.notifications

enum class ReminderType(
    val title: String,
    val body: String,
    val defaultHour: Int,
    val defaultMinute: Int,
    val isDaily: Boolean = true
) {
    MORNING_SUNLIGHT("Morning Sunlight", "Get a few minutes of morning sunlight.", 8, 0),
    PROTEIN("Protein Check-In", "Check your protein progress for the day.", 14, 0),
    STEPS("Steps Reminder", "Take a walk and move toward your step goal.", 17, 0),
    WORKOUT("Workout Reminder", "Time for your planned workout.", 18, 0),
    BREATHWORK("Breathwork", "Take 2 minutes to breathe and reset.", 20, 0),
    LATE_EATING("Late Eating", "Try to finish eating 2–3 hours before bed.", 20, 30),
    BLOODWORK("Bloodwork Reminder", "Consider scheduling routine bloodwork.", 9, 0, isDaily = false)
}
