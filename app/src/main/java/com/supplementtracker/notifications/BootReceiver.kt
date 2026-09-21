package com.supplementtracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.supplementtracker.data.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = AppDatabase.getInstance(context)
                val groups = db.scheduleGroupDao().getAll()
                groups.forEach { group ->
                    AlarmScheduler.scheduleGroup(context, group)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
