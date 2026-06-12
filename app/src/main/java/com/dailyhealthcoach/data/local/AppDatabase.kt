package com.dailyhealthcoach.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dailyhealthcoach.data.local.dao.BodyMetricLogDao
import com.dailyhealthcoach.data.local.dao.DailyHabitLogDao
import com.dailyhealthcoach.data.local.dao.DailyRecommendationDao
import com.dailyhealthcoach.data.local.dao.ExerciseDao
import com.dailyhealthcoach.data.local.dao.FoodEntryDao
import com.dailyhealthcoach.data.local.dao.HabitDefinitionDao
import com.dailyhealthcoach.data.local.dao.MacroTargetDao
import com.dailyhealthcoach.data.local.dao.RecoveryScoreDao
import com.dailyhealthcoach.data.local.dao.UserProfileDao
import com.dailyhealthcoach.data.local.dao.RecoveryActivityDao
import com.dailyhealthcoach.data.local.dao.WorkoutDao
import com.dailyhealthcoach.data.local.dao.WorkoutExerciseDao
import com.dailyhealthcoach.data.local.entity.BodyMetricLogEntity
import com.dailyhealthcoach.data.local.entity.DailyHabitLogEntity
import com.dailyhealthcoach.data.local.entity.DailyRecommendationEntity
import com.dailyhealthcoach.data.local.entity.ExerciseEntity
import com.dailyhealthcoach.data.local.entity.FoodEntryEntity
import com.dailyhealthcoach.data.local.entity.HabitDefinitionEntity
import com.dailyhealthcoach.data.local.entity.MacroTargetEntity
import com.dailyhealthcoach.data.local.entity.RecoveryActivityEntity
import com.dailyhealthcoach.data.local.entity.RecoveryScoreEntity
import com.dailyhealthcoach.data.local.entity.UserProfileEntity
import com.dailyhealthcoach.data.local.entity.WorkoutEntity
import com.dailyhealthcoach.data.local.entity.WorkoutExerciseEntity

@Database(
    entities = [
        UserProfileEntity::class,
        HabitDefinitionEntity::class,
        DailyHabitLogEntity::class,
        ExerciseEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class,
        BodyMetricLogEntity::class,
        FoodEntryEntity::class,
        MacroTargetEntity::class,
        RecoveryScoreEntity::class,
        DailyRecommendationEntity::class,
        RecoveryActivityEntity::class
    ],
    version = 11,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun habitDefinitionDao(): HabitDefinitionDao
    abstract fun dailyHabitLogDao(): DailyHabitLogDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao
    abstract fun bodyMetricLogDao(): BodyMetricLogDao
    abstract fun foodEntryDao(): FoodEntryDao
    abstract fun macroTargetDao(): MacroTargetDao
    abstract fun recoveryScoreDao(): RecoveryScoreDao
    abstract fun dailyRecommendationDao(): DailyRecommendationDao
    abstract fun recoveryActivityDao(): RecoveryActivityDao
}
