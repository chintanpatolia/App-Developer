package com.supplementtracker.data.db

import com.supplementtracker.data.dao.ScheduleGroupDao
import com.supplementtracker.data.dao.SupplementDao
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity

object DatabaseSeeder {

    suspend fun seedIfEmpty(scheduleGroupDao: ScheduleGroupDao, supplementDao: SupplementDao) {
        if (supplementDao.count() > 0) return

        val morningId = scheduleGroupDao.insert(
            ScheduleGroupEntity(name = "MORNING", label = "Morning", reminderHour = 7, reminderMinute = 0, sortOrder = 0)
        )
        val lunchId = scheduleGroupDao.insert(
            ScheduleGroupEntity(name = "LUNCH", label = "Lunch", reminderHour = 12, reminderMinute = 30, groupNote = "Take with lunch / protein shake", sortOrder = 1)
        )
        val eveningId = scheduleGroupDao.insert(
            ScheduleGroupEntity(name = "EVENING", label = "Evening", reminderHour = 22, reminderMinute = 0, sortOrder = 2)
        )
        scheduleGroupDao.insert(
            ScheduleGroupEntity(name = "BEDTIME", label = "Bedtime", reminderHour = 22, reminderMinute = 30, sortOrder = 3)
        )
        scheduleGroupDao.insert(
            ScheduleGroupEntity(name = "CUSTOM", label = "Custom", reminderHour = 9, reminderMinute = 0, sortOrder = 4)
        )

        supplementDao.insertAll(listOf(
            SupplementEntity(name = "FeelGood NAD+ / NMN", dose = "1 capsule", scheduleGroupId = morningId, sortOrder = 0),
            SupplementEntity(name = "Creatine Monohydrate", dose = "5 g", scheduleGroupId = lunchId, sortOrder = 0),
            SupplementEntity(name = "L-Carnitine Tartrate", dose = "2 capsules", scheduleGroupId = lunchId, sortOrder = 1),
            SupplementEntity(name = "L-Leucine", dose = "2.5 g", scheduleGroupId = lunchId, sortOrder = 2),
            SupplementEntity(name = "Sports Research D3 + K2", dose = "1 softgel", scheduleGroupId = lunchId, sortOrder = 3),
            SupplementEntity(name = "Vegan Omega-3", dose = "2 softgels", scheduleGroupId = lunchId, sortOrder = 4),
            SupplementEntity(name = "FeelGood Magnesium Glycinate", dose = "3 capsules", scheduleGroupId = eveningId, sortOrder = 0),
        ))
    }
}
