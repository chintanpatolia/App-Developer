package com.supplementtracker.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.supplementtracker.MainActivity
import com.supplementtracker.data.entity.DailyOccurrenceEntity
import com.supplementtracker.data.entity.ScheduleGroupEntity

object NotificationHelper {

    const val CHANNEL_ID = "supplement_reminders"
    private const val CHANNEL_NAME = "Supplement Reminders"

    fun createChannel(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (nm.getNotificationChannel(CHANNEL_ID) == null) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Daily supplement reminder notifications"
                enableVibration(true)
            }
            nm.createNotificationChannel(channel)
        }
    }

    fun buildGroupNotification(
        context: Context,
        group: ScheduleGroupEntity,
        outstandingOccurrences: List<DailyOccurrenceEntity>,
        supplementNames: Map<Long, String>,
        date: String
    ): Notification {
        val title = "${group.label} Supplements"
        val body = outstandingOccurrences
            .mapNotNull { supplementNames[it.supplementId] }
            .joinToString(" · ")
            .ifEmpty { "All supplements" }

        val openIntent = PendingIntent.getActivity(
            context, 0,
            Intent(context, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val completeIntent = PendingIntent.getBroadcast(
            context,
            (group.id * 100 + 1).toInt(),
            Intent(context, AlarmReceiver::class.java).apply {
                action = AlarmReceiver.ACTION_COMPLETE
                putExtra(AlarmReceiver.EXTRA_GROUP_ID, group.id)
                putExtra(AlarmReceiver.EXTRA_DATE, date)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val snoozeIntent = PendingIntent.getBroadcast(
            context,
            (group.id * 100 + 2).toInt(),
            Intent(context, AlarmReceiver::class.java).apply {
                action = AlarmReceiver.ACTION_SNOOZE
                putExtra(AlarmReceiver.EXTRA_GROUP_ID, group.id)
                putExtra(AlarmReceiver.EXTRA_DATE, date)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentIntent(openIntent)
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(0, "Snooze 15 min", snoozeIntent)
            .addAction(0, "Complete", completeIntent)
            .build()
    }

    fun notificationId(groupId: Long): Int = (groupId % 10_000).toInt() + 1000

    fun cancelNotification(context: Context, groupId: Long) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancel(notificationId(groupId))
    }
}
