package com.supplementtracker.data.db

import android.content.Context
import androidx.room.*
import com.supplementtracker.data.dao.DailyOccurrenceDao
import com.supplementtracker.data.dao.ScheduleGroupDao
import com.supplementtracker.data.dao.SupplementDao
import com.supplementtracker.data.entity.DailyOccurrenceEntity
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity

@Database(
    entities = [ScheduleGroupEntity::class, SupplementEntity::class, DailyOccurrenceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleGroupDao(): ScheduleGroupDao
    abstract fun supplementDao(): SupplementDao
    abstract fun dailyOccurrenceDao(): DailyOccurrenceDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "supplement_tracker.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
