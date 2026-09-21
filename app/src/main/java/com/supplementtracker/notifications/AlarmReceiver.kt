package com.supplementtracker.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.supplementtracker.data.db.AppDatabase
import com.supplementtracker.data.repository.SupplementRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_ALARM = "com.supplementtracker.ACTION_ALARM"
        const val ACTION_COMPLETE = "com.supplementtracker.ACTION_COMPLETE"
        const val ACTION_SNOOZE = "com.supplementtracker.ACTION_SNOOZE"
        const val EXTRA_GROUP_ID = "group_id"
        const val EXTRA_DATE = "date"
        const val EXTRA_IS_SNOOZE = "is_snooze"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val groupId = intent.getLongExtra(EXTRA_GROUP_ID, -1L)
        if (groupId == -1L) return

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = AppDatabase.getInstance(context)
                val repo = SupplementRepository(db.supplementDao(), db.scheduleGroupDao(), db.dailyOccurrenceDao())
                val today = LocalDate.now().toString()
                val date = intent.getStringExtra(EXTRA_DATE) ?: today

                when (intent.action) {
                    ACTION_ALARM -> handleAlarm(context, repo, groupId, date)
                    ACTION_COMPLETE -> handleComplete(context, repo, groupId, date)
                    ACTION_SNOOZE -> handleSnooze(context, repo, groupId, date)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }

    private suspend fun handleAlarm(context: Context, repo: SupplementRepository, groupId: Long, date: String) {
        val group = repo.getGroupById(groupId) ?: return
        // Ensure today's occurrences exist before checking outstanding
        repo.ensureTodayOccurrences(date)

        val outstanding = repo.getOutstandingInGroup(groupId, date)
        if (outstanding.isEmpty()) {
            // All already completed — skip notification
            // Re-schedule for next day
            AlarmScheduler.scheduleGroup(context, group)
            return
        }

        val supplements = repo.getActiveSupplementsList()
        val nameMap = supplements.associate { it.id to it.name }

        NotificationHelper.createChannel(context)
        val notification = NotificationHelper.buildGroupNotification(context, group, outstanding, nameMap, date)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(NotificationHelper.notificationId(groupId), notification)

        // Re-schedule for next day
        AlarmScheduler.scheduleGroup(context, group)
    }

    private suspend fun handleComplete(context: Context, repo: SupplementRepository, groupId: Long, date: String) {
        repo.markGroupCompleted(groupId, date)
        NotificationHelper.cancelNotification(context, groupId)
        AlarmScheduler.cancelSnooze(context, groupId, date)
    }

    private suspend fun handleSnooze(context: Context, repo: SupplementRepository, groupId: Long, date: String) {
        NotificationHelper.cancelNotification(context, groupId)
        AlarmScheduler.scheduleSnooze(context, groupId, date)
    }
}
