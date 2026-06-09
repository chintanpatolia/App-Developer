package com.dailyhealthcoach.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        val prefs = ReminderPreferences(context)
        ReminderType.entries.forEach { type ->
            if (prefs.isEnabled(type)) {
                ReminderScheduler.schedule(context, type, prefs.getHour(type), prefs.getMinute(type))
            }
        }
    }
}
