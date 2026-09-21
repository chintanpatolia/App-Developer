package com.supplementtracker.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.supplementtracker.data.entity.ScheduleGroupEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

object AlarmScheduler {

    fun scheduleGroup(context: Context, group: ScheduleGroupEntity) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pi = pendingIntentForGroup(context, group.id)
        val triggerAt = nextTriggerMillis(group.reminderHour, group.reminderMinute)
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && am.canScheduleExactAlarms() ->
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pi)
            else ->
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pi)
        }
    }

    fun cancelGroup(context: Context, groupId: Long) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.cancel(pendingIntentForGroup(context, groupId))
    }

    fun scheduleSnooze(context: Context, groupId: Long, date: String, delayMinutes: Int = 15) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pi = pendingIntentForSnooze(context, groupId, date)
        val triggerAt = System.currentTimeMillis() + delayMinutes * 60_000L
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && am.canScheduleExactAlarms() ->
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pi)
            else ->
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pi)
        }
    }

    fun cancelSnooze(context: Context, groupId: Long, date: String) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.cancel(pendingIntentForSnooze(context, groupId, date))
    }

    fun canScheduleExact(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return am.canScheduleExactAlarms()
    }

    private fun pendingIntentForGroup(context: Context, groupId: Long): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_ALARM
            putExtra(AlarmReceiver.EXTRA_GROUP_ID, groupId)
        }
        return PendingIntent.getBroadcast(
            context, groupId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun pendingIntentForSnooze(context: Context, groupId: Long, date: String): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_ALARM
            putExtra(AlarmReceiver.EXTRA_GROUP_ID, groupId)
            putExtra(AlarmReceiver.EXTRA_DATE, date)
            putExtra(AlarmReceiver.EXTRA_IS_SNOOZE, true)
        }
        return PendingIntent.getBroadcast(
            context, (groupId + 5000).toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun nextTriggerMillis(hour: Int, minute: Int): Long {
        val zone = ZoneId.systemDefault()
        val now = LocalDateTime.now(zone)
        var target = LocalDateTime.of(LocalDate.now(zone), LocalTime.of(hour, minute))
        if (!target.isAfter(now)) target = target.plusDays(1)
        return target.atZone(zone).toInstant().toEpochMilli()
    }
}
