package com.dailyhealthcoach.healthconnect

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dailyhealthcoach.DailyHealthCoachApplication

class HealthConnectSyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val app = applicationContext as DailyHealthCoachApplication
        HealthConnectManager.syncToday(
            applicationContext,
            app.appContainer.bodyMetricRepository,
            app.appContainer.habitAutoUpdateUseCase
        )
        return Result.success()
    }
}
