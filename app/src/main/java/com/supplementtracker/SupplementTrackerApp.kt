package com.supplementtracker

import android.app.Application
import com.supplementtracker.data.db.AppDatabase
import com.supplementtracker.data.db.DatabaseSeeder
import com.supplementtracker.data.repository.SupplementRepository
import com.supplementtracker.notifications.AlarmScheduler
import com.supplementtracker.notifications.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

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
            val groups = db.scheduleGroupDao().getAll()
            groups.forEach { group ->
                AlarmScheduler.scheduleGroup(this@SupplementTrackerApp, group)
            }
        }
    }
}
