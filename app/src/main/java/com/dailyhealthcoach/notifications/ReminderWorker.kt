package com.dailyhealthcoach.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val typeName = intent.getStringExtra("type") ?: return
        val type = ReminderType.entries.find { it.name == typeName } ?: return
        NotificationHelper.postNotification(context, type)
    }
}
