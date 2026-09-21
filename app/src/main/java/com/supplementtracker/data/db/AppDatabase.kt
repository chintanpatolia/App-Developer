package com.supplementtracker.data.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.supplementtracker.data.dao.DailyOccurrenceDao
import com.supplementtracker.data.dao.ScheduleGroupDao
import com.supplementtracker.data.dao.SupplementDao
import com.supplementtracker.data.entity.DailyOccurrenceEntity
import com.supplementtracker.data.entity.ScheduleGroupEntity
import com.supplementtracker.data.entity.SupplementEntity

@Database(
    entities = [ScheduleGroupEntity::class, SupplementEntity::class, DailyOccurrenceEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleGroupDao(): ScheduleGroupDao
    abstract fun supplementDao(): SupplementDao
    abstract fun dailyOccurrenceDao(): DailyOccurrenceDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE supplements ADD COLUMN groupAssignedAt INTEGER NOT NULL DEFAULT 0")
                // Backfill: treat current group as having always been assigned at creation time
                db.execSQL("UPDATE supplements SET groupAssignedAt = createdAt")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "supplement_tracker.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
