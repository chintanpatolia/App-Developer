package com.supplementtracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.supplementtracker.ReconcilePrefs
import com.supplementtracker.data.db.AppDatabase
import com.supplementtracker.data.repository.SupplementRepository
import com.supplementtracker.domain.OccurrenceReconciler
import com.supplementtracker.domain.SystemDateProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = AppDatabase.getInstance(context)
                val repo = SupplementRepository(db.supplementDao(), db.scheduleGroupDao(), db.dailyOccurrenceDao())
                val today = SystemDateProvider.today()

                OccurrenceReconciler.reconcile(
                    repo = repo,
                    today = today,
                    getLastReconciled = { ReconcilePrefs(context).getLastDate() },
                    setLastReconciled = { ReconcilePrefs(context).setLastDate(it) }
                )

                db.scheduleGroupDao().getAll().forEach { group ->
                    AlarmScheduler.scheduleGroup(context, group)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
