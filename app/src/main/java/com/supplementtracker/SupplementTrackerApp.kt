package com.supplementtracker

import android.app.Application
import android.content.Context
import com.supplementtracker.data.db.AppDatabase
import com.supplementtracker.data.db.DatabaseSeeder
import com.supplementtracker.data.repository.SupplementRepository
import com.supplementtracker.domain.OccurrenceReconciler
import com.supplementtracker.domain.SystemDateProvider
import com.supplementtracker.notifications.AlarmScheduler
import com.supplementtracker.notifications.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.LocalDate

class SupplementTrackerApp : Application() {

    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var db: AppDatabase
        private set

    lateinit var repository: SupplementRepository
        private set

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getInstance(this)
        repository = SupplementRepository(
            db.supplementDao(),
            db.scheduleGroupDao(),
            db.dailyOccurrenceDao()
        )

        NotificationHelper.createChannel(this)

        appScope.launch {
            DatabaseSeeder.seedIfEmpty(db.scheduleGroupDao(), db.supplementDao())

            val today = SystemDateProvider.today()

            // Reconcile any missed past occurrences before showing the user their data
            OccurrenceReconciler.reconcile(
                repo = repository,
                today = today,
                getLastReconciled = { reconcilePrefs(this@SupplementTrackerApp).getLastDate() },
                setLastReconciled = { reconcilePrefs(this@SupplementTrackerApp).setLastDate(it) }
            )

            val groups = db.scheduleGroupDao().getAll()
            groups.forEach { group ->
                AlarmScheduler.scheduleGroup(this@SupplementTrackerApp, group)
            }
        }
    }
}

/** Simple SharedPreferences wrapper for last-reconciled date persistence. */
internal fun reconcilePrefs(context: Context) = ReconcilePrefs(context)

internal class ReconcilePrefs(context: Context) {
    private val prefs = context.getSharedPreferences("reconciler", Context.MODE_PRIVATE)

    fun getLastDate(): LocalDate? =
        prefs.getString("last_reconcile_date", null)?.let { LocalDate.parse(it) }

    fun setLastDate(date: LocalDate) =
        prefs.edit().putString("last_reconcile_date", date.toString()).apply()
}
