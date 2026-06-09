package com.dailyhealthcoach

import android.app.Application
import com.dailyhealthcoach.data.AppContainer
import com.dailyhealthcoach.data.local.AppDatabaseProvider
import com.dailyhealthcoach.data.local.seed.DatabaseSeeder
import com.dailyhealthcoach.notifications.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DailyHealthCoachApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()

        NotificationHelper.createChannel(this)
        val database = AppDatabaseProvider.getDatabase(this)
        appContainer = AppContainer(this)
        applicationScope.launch {
            DatabaseSeeder(database).seedIfNeeded()
        }
    }
}
