package com.dailyhealthcoach.data

import android.content.Context
import com.dailyhealthcoach.data.local.AppDatabaseProvider
import com.dailyhealthcoach.data.repository.BodyMetricRepositoryImpl
import com.dailyhealthcoach.data.repository.DailyRecommendationRepositoryImpl
import com.dailyhealthcoach.data.repository.ExerciseRepositoryImpl
import com.dailyhealthcoach.data.repository.HabitRepositoryImpl
import com.dailyhealthcoach.data.repository.MacroTargetRepositoryImpl
import com.dailyhealthcoach.data.repository.NutritionRepositoryImpl
import com.dailyhealthcoach.data.repository.RecoveryActivityRepositoryImpl
import com.dailyhealthcoach.data.repository.RecoveryRepositoryImpl
import com.dailyhealthcoach.data.repository.UserProfileRepositoryImpl
import com.dailyhealthcoach.data.repository.WorkoutRepositoryImpl
import com.dailyhealthcoach.domain.recommendation.NextDayRecommendationService
import com.dailyhealthcoach.domain.recovery.RecoveryScoreCalculator
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import com.dailyhealthcoach.domain.repository.DailyRecommendationRepository
import com.dailyhealthcoach.domain.repository.ExerciseRepository
import com.dailyhealthcoach.domain.repository.HabitRepository
import com.dailyhealthcoach.domain.repository.MacroTargetRepository
import com.dailyhealthcoach.domain.repository.NutritionRepository
import com.dailyhealthcoach.domain.repository.RecoveryActivityRepository
import com.dailyhealthcoach.domain.repository.RecoveryRepository
import com.dailyhealthcoach.domain.repository.UserProfileRepository
import com.dailyhealthcoach.domain.repository.WorkoutRepository
import com.dailyhealthcoach.barcode.FoodLookupService
import com.dailyhealthcoach.barcode.MockFoodLookupService
import com.dailyhealthcoach.data.export.DataExportService
import com.dailyhealthcoach.domain.usecase.GenerateWorkoutPlanUseCase
import com.dailyhealthcoach.domain.usecase.GetDashboardSummaryUseCase
import com.dailyhealthcoach.domain.usecase.GetTodayHabitsUseCase
import com.dailyhealthcoach.domain.usecase.SetHabitStatusForTodayUseCase

class AppContainer(context: Context) {
    private val database = AppDatabaseProvider.getDatabase(context)

    val habitRepository: HabitRepository = HabitRepositoryImpl(
        habitDefinitionDao = database.habitDefinitionDao(),
        dailyHabitLogDao = database.dailyHabitLogDao()
    )

    val macroTargetRepository: MacroTargetRepository = MacroTargetRepositoryImpl(
        macroTargetDao = database.macroTargetDao()
    )

    val userProfileRepository: UserProfileRepository = UserProfileRepositoryImpl(
        userProfileDao = database.userProfileDao()
    )

    val exerciseRepository: ExerciseRepository = ExerciseRepositoryImpl(
        exerciseDao = database.exerciseDao()
    )

    val workoutRepository: WorkoutRepository = WorkoutRepositoryImpl(
        workoutDao = database.workoutDao(),
        workoutExerciseDao = database.workoutExerciseDao()
    )

    val bodyMetricRepository: BodyMetricRepository = BodyMetricRepositoryImpl(
        bodyMetricLogDao = database.bodyMetricLogDao()
    )

    val nutritionRepository: NutritionRepository = NutritionRepositoryImpl(
        foodEntryDao = database.foodEntryDao()
    )

    val recoveryRepository: RecoveryRepository = RecoveryRepositoryImpl(
        recoveryScoreDao = database.recoveryScoreDao()
    )

    val dailyRecommendationRepository: DailyRecommendationRepository = DailyRecommendationRepositoryImpl(
        dailyRecommendationDao = database.dailyRecommendationDao()
    )

    val getDashboardSummaryUseCase = GetDashboardSummaryUseCase(
        habitRepository = habitRepository,
        macroTargetRepository = macroTargetRepository,
        nutritionRepository = nutritionRepository,
        bodyMetricRepository = bodyMetricRepository,
        workoutRepository = workoutRepository,
        exerciseRepository = exerciseRepository,
        recoveryRepository = recoveryRepository,
        dailyRecommendationRepository = dailyRecommendationRepository,
        recoveryScoreCalculator = RecoveryScoreCalculator(),
        recommendationService = NextDayRecommendationService(),
        userProfileRepository = userProfileRepository
    )

    val getTodayHabitsUseCase = GetTodayHabitsUseCase(
        habitRepository = habitRepository
    )

    val setHabitStatusForTodayUseCase = SetHabitStatusForTodayUseCase(
        habitRepository = habitRepository
    )

    val recoveryActivityRepository: RecoveryActivityRepository = RecoveryActivityRepositoryImpl(
        dao = database.recoveryActivityDao()
    )

    val generateWorkoutPlanUseCase = GenerateWorkoutPlanUseCase()

    val foodLookupService: FoodLookupService = MockFoodLookupService()

    val dataExportService: DataExportService = DataExportService(database)
}
