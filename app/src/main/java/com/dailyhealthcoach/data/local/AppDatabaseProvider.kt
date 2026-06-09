package com.dailyhealthcoach.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object AppDatabaseProvider {
    @Volatile
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            database ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "daily_health_coach.db"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7)
                .build()
                .also { database = it }
        }
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE body_metric_logs ADD COLUMN heightInches REAL")
            db.execSQL("ALTER TABLE body_metric_logs ADD COLUMN calculatedBodyFatPercent REAL")
            db.execSQL("ALTER TABLE body_metric_logs ADD COLUMN manualBodyFatPercent REAL")
            db.execSQL("ALTER TABLE body_metric_logs ADD COLUMN isBodyFatOverridden INTEGER NOT NULL DEFAULT 0")
            db.execSQL("ALTER TABLE body_metric_logs ADD COLUMN neckMeasurement REAL")
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE recovery_scores ADD COLUMN label TEXT NOT NULL DEFAULT ''")
            db.execSQL("ALTER TABLE recovery_scores ADD COLUMN reasonText TEXT")
            db.execSQL("ALTER TABLE recovery_scores ADD COLUMN updatedAt TEXT NOT NULL DEFAULT ''")
        }
    }

    private val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE daily_recommendations ADD COLUMN title TEXT NOT NULL DEFAULT ''")
            db.execSQL("ALTER TABLE daily_recommendations ADD COLUMN explanation TEXT NOT NULL DEFAULT ''")
            db.execSQL("ALTER TABLE daily_recommendations ADD COLUMN suggestedFocus TEXT NOT NULL DEFAULT ''")
            db.execSQL("ALTER TABLE daily_recommendations ADD COLUMN reasonBullets TEXT")
            db.execSQL("ALTER TABLE daily_recommendations ADD COLUMN updatedAt TEXT NOT NULL DEFAULT ''")
        }
    }

    private val MIGRATION_6_7 = object : Migration(6, 7) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE user_profiles ADD COLUMN age INTEGER")
            db.execSQL("ALTER TABLE user_profiles ADD COLUMN sex TEXT")
            db.execSQL("ALTER TABLE user_profiles ADD COLUMN weightGoalPounds REAL")
            db.execSQL("ALTER TABLE user_profiles ADD COLUMN bodyFatGoalPercent REAL")
            db.execSQL("ALTER TABLE user_profiles ADD COLUMN stepMinTarget INTEGER")
            db.execSQL("ALTER TABLE user_profiles ADD COLUMN stepMaxTarget INTEGER")
            db.execSQL("ALTER TABLE user_profiles ADD COLUMN sleepTargetHours REAL")
            db.execSQL("ALTER TABLE user_profiles ADD COLUMN strengthTrainingDaysPerWeek INTEGER")
        }
    }

    private val MIGRATION_5_6 = object : Migration(5, 6) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE food_entries ADD COLUMN isSaved INTEGER NOT NULL DEFAULT 0")
        }
    }

    private val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """CREATE TABLE IF NOT EXISTS recovery_activity_logs (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    workout_id INTEGER NOT NULL,
                    activity_name TEXT NOT NULL,
                    status TEXT NOT NULL DEFAULT 'COMPLETED',
                    duration_minutes INTEGER,
                    rpe INTEGER,
                    notes TEXT
                )""".trimIndent()
            )
        }
    }
}
