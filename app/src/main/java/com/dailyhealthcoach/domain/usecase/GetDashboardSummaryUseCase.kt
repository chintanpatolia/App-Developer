package com.dailyhealthcoach.domain.usecase

import com.dailyhealthcoach.domain.model.DashboardSummary
import com.dailyhealthcoach.domain.model.WorkoutStatus
import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.DailyHabitLog
import com.dailyhealthcoach.domain.model.Exercise
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.HabitDefinition
import com.dailyhealthcoach.domain.model.MacroTarget
import com.dailyhealthcoach.domain.recommendation.NextDayRecommendationService
import com.dailyhealthcoach.domain.recommendation.RecommendationInput
import com.dailyhealthcoach.domain.recovery.RecoveryScoreCalculator
import com.dailyhealthcoach.domain.recovery.RecoveryScoreInput
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
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach

class GetDashboardSummaryUseCase(
    private val habitRepository: HabitRepository,
    private val macroTargetRepository: MacroTargetRepository,
    private val nutritionRepository: NutritionRepository,
    private val bodyMetricRepository: BodyMetricRepository,
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val recoveryRepository: RecoveryRepository,
    private val dailyRecommendationRepository: DailyRecommendationRepository,
    private val recoveryScoreCalculator: RecoveryScoreCalculator,
    private val recommendationService: NextDayRecommendationService,
    private val userProfileRepository: UserProfileRepository,
    private val recoveryActivityRepository: RecoveryActivityRepository
) {
    operator fun invoke(date: String): Flow<DashboardSummary> {
        val today = LocalDate.parse(date)
        val twoDaysAgo = today.minusDays(2).toString()
        val sixDaysAgo = today.minusDays(6).toString()
        return combine(
            combine(
                habitRepository.observeActiveHabits(),
                habitRepository.observeLogsForDate(date),
                macroTargetRepository.observeActiveTarget(),
                nutritionRepository.observeFoodEntriesForDate(date),
                bodyMetricRepository.observeForDate(date)
            ) { habits, logs, macroTarget, foodEntries, bodyMetricLog ->
                DashboardInputs(
                    habits = habits,
                    logs = logs,
                    macroTarget = macroTarget,
                    foodEntries = foodEntries,
                    bodyMetricLog = bodyMetricLog
                )
            },
            combine(
                workoutRepository.observeWorkouts(),
                workoutRepository.observeWorkoutSets(),
                exerciseRepository.observeExercises(),
                recoveryActivityRepository.observeAll()
            ) { workouts, workoutSets, exercises, recoveryActivities ->
                WorkoutInputs(
                    workouts = workouts,
                    workoutSets = workoutSets,
                    exercises = exercises,
                    recoveryActivities = recoveryActivities
                )
            },
            userProfileRepository.observeUserProfile()
        ) { inputs, workoutInputs, userProfile ->
            val todayHabits = inputs.habits.filter { it.frequencyType != "INTERVAL" }
            val completedHabitIds = inputs.logs
                .filter { it.status == "COMPLETE" }
                .map { it.habitDefinitionId }
                .toSet()
            val proteinConsumed = inputs.foodEntries.sumOf { it.proteinGrams ?: 0.0 }.toInt()
            val caloriesToday = inputs.foodEntries.sumOf { it.calories ?: 0 }
            val calorieGoal = inputs.macroTarget?.calorieTarget ?: 2000
            val todayWorkouts = workoutInputs.workouts.filter { it.date == date }
            val workoutCompletedToday = todayWorkouts.any { WorkoutStatus.fromStorageValue(it.status) != WorkoutStatus.SKIPPED }
            val todayActiveWorkout = todayWorkouts.firstOrNull { WorkoutStatus.fromStorageValue(it.status) != WorkoutStatus.SKIPPED }
            val recentWorkouts = workoutInputs.workouts.filter { it.date in twoDaysAgo..date }
            val weeklyWorkouts = workoutInputs.workouts.filter { it.date in sixDaysAgo..date }
            val recovery = recoveryScoreCalculator.calculate(
                RecoveryScoreInput(
                    bodyMetricLog = inputs.bodyMetricLog,
                    foodEntries = inputs.foodEntries,
                    habitDefinitions = inputs.habits,
                    habitLogs = inputs.logs,
                    todayWorkouts = todayWorkouts,
                    recentWorkouts = recentWorkouts,
                    workoutSets = workoutInputs.workoutSets,
                    recoveryActivities = workoutInputs.recoveryActivities,
                    weeklyWorkouts = weeklyWorkouts
                )
            )
            val recommendation = recommendationService.calculate(
                RecommendationInput(
                    date = date,
                    recoveryScore = recovery?.score,
                    bodyMetricLog = inputs.bodyMetricLog,
                    foodEntries = inputs.foodEntries,
                    habitDefinitions = inputs.habits,
                    habitLogs = inputs.logs,
                    todayWorkouts = todayWorkouts,
                    recentWorkouts = recentWorkouts,
                    weeklyWorkouts = weeklyWorkouts,
                    workoutSets = workoutInputs.workoutSets,
                    exercises = workoutInputs.exercises
                )
            )

            val completedHabits = todayHabits.count { it.id in completedHabitIds }
            val sleepHours = inputs.bodyMetricLog?.sleepHours ?: 0.0
            val healthScore = computeHealthScore(
                recoveryScore = recovery?.score,
                caloriesToday = caloriesToday,
                calorieGoal = calorieGoal,
                completedHabits = completedHabits,
                totalHabits = todayHabits.size,
                sleepHours = sleepHours
            )
            DashboardSummary(
                totalHabits = todayHabits.size,
                completedHabits = completedHabits,
                proteinConsumedGrams = proteinConsumed,
                proteinMinGoalGrams = inputs.macroTarget?.proteinMinGrams ?: 170,
                proteinMaxGoalGrams = inputs.macroTarget?.proteinMaxGrams ?: 200,
                steps = inputs.bodyMetricLog?.stepCount ?: 0,
                stepGoal = userProfile?.stepMinTarget ?: 8_000,
                sleepHours = sleepHours,
                recoveryScore = recovery?.score,
                recoveryLabel = recovery?.label ?: "Recovery not calculated",
                recoveryReasons = recovery?.reasons.orEmpty(),
                recoveryContributors = recovery?.contributors.orEmpty(),
                nextDayRecommendation = recommendation,
                workoutCompletedToday = workoutCompletedToday,
                caloriesToday = caloriesToday,
                calorieGoal = calorieGoal,
                weightKg = inputs.bodyMetricLog?.bodyWeight,
                bodyFatPercent = inputs.bodyMetricLog?.bodyFatPercentage,
                todayWorkoutName = todayActiveWorkout?.name,
                todayWorkoutStatusLabel = todayActiveWorkout?.let { WorkoutStatus.fromStorageValue(it.status).label },
                healthScore = healthScore,
                coachLine = selectCoachLine(
                    recoveryScore = recovery?.score,
                    workoutCompletedToday = workoutCompletedToday,
                    proteinConsumed = proteinConsumed,
                    proteinGoal = inputs.macroTarget?.proteinMinGrams ?: 170,
                    totalHabits = todayHabits.size,
                    completedHabits = completedHabits,
                    sleepHours = sleepHours
                )
            )
        }.onEach { summary ->
            val score = summary.recoveryScore ?: return@onEach
            recoveryRepository.saveForDate(
                date = date,
                score = score,
                label = summary.recoveryLabel,
                reasons = summary.recoveryReasons
            )
            summary.nextDayRecommendation?.let { recommendation ->
                dailyRecommendationRepository.saveForDate(recommendation)
            }
        }
    }
}

private fun computeHealthScore(
    recoveryScore: Int?,
    caloriesToday: Int,
    calorieGoal: Int,
    completedHabits: Int,
    totalHabits: Int,
    sleepHours: Double
): Int {
    val recoveryPart = ((recoveryScore ?: 0) / 100.0) * 40
    val nutritionPart = if (calorieGoal > 0 && caloriesToday > 0)
        (caloriesToday.toDouble() / calorieGoal).coerceIn(0.0, 1.0) * 25 else 0.0
    val habitPart = if (totalHabits > 0)
        (completedHabits.toDouble() / totalHabits) * 20 else 0.0
    val sleepPart = (sleepHours / 8.0).coerceIn(0.0, 1.0) * 15
    return (recoveryPart + nutritionPart + habitPart + sleepPart).toInt()
}

private fun selectCoachLine(
    recoveryScore: Int?,
    workoutCompletedToday: Boolean,
    proteinConsumed: Int,
    proteinGoal: Int,
    totalHabits: Int,
    completedHabits: Int,
    sleepHours: Double
): String? = when {
    recoveryScore != null && recoveryScore >= 80 -> "You're well recovered. Good day to push hard."
    recoveryScore != null && recoveryScore <= 40 -> "Your body needs rest today. Honor that."
    workoutCompletedToday -> "Workout logged. Recovery window starts now."
    totalHabits > 0 && completedHabits == totalHabits -> "Clean sweep today. Rare."
    proteinGoal > 0 && proteinConsumed >= proteinGoal -> "Protein dialed in. Recovery starts here."
    sleepHours in 0.01..5.99 -> "Sleep debt showing. Protect tonight."
    else -> null
}

private data class DashboardInputs(
    val habits: List<HabitDefinition>,
    val logs: List<DailyHabitLog>,
    val macroTarget: MacroTarget?,
    val foodEntries: List<FoodEntry>,
    val bodyMetricLog: BodyMetricLog?
)

private data class WorkoutInputs(
    val workouts: List<com.dailyhealthcoach.domain.model.Workout>,
    val workoutSets: List<com.dailyhealthcoach.domain.model.WorkoutExercise>,
    val exercises: List<Exercise>,
    val recoveryActivities: List<com.dailyhealthcoach.domain.model.RecoveryActivity> = emptyList()
)
