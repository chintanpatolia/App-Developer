package com.dailyhealthcoach.notifications

import android.content.Context

class ReminderPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("reminders_v1", Context.MODE_PRIVATE)

    fun isEnabled(type: ReminderType): Boolean = prefs.getBoolean("${type.name}_on", false)
    fun getHour(type: ReminderType): Int = prefs.getInt("${type.name}_h", type.defaultHour)
    fun getMinute(type: ReminderType): Int = prefs.getInt("${type.name}_m", type.defaultMinute)

    fun setEnabled(type: ReminderType, enabled: Boolean) {
        prefs.edit().putBoolean("${type.name}_on", enabled).apply()
    }

    fun setTime(type: ReminderType, hour: Int, minute: Int) {
        prefs.edit()
            .putInt("${type.name}_h", hour)
            .putInt("${type.name}_m", minute)
            .apply()
    }
}
