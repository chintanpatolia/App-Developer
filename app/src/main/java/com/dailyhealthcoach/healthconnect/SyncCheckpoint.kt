package com.dailyhealthcoach.healthconnect

import android.content.Context

/**
 * Durable watermark for Health Connect background sync.
 * Only advances after a date's data is confirmed persisted — prevents
 * marking unsynced dates as complete on partial failure.
 */
class SyncCheckpoint(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getLastSyncDate(): String? = prefs.getString(KEY_LAST_SYNC, null)

    fun setLastSyncDate(date: String) {
        prefs.edit().putString(KEY_LAST_SYNC, date).apply()
    }

    companion object {
        private const val PREFS_NAME = "hc_sync_v1"
        private const val KEY_LAST_SYNC = "last_sync_date"
        const val MAX_BACKFILL_DAYS = 30L
    }
}
