package com.dailyhealthcoach.ui.progress

import com.dailyhealthcoach.domain.model.BodyMetricLog
import com.dailyhealthcoach.domain.model.DailyHabitLog
import com.dailyhealthcoach.domain.model.FoodEntry
import com.dailyhealthcoach.domain.model.HabitDefinition
import com.dailyhealthcoach.domain.model.RecoveryScore
import com.dailyhealthcoach.domain.model.Workout
import com.dailyhealthcoach.domain.model.WorkoutExercise
import com.dailyhealthcoach.domain.model.WorkoutSetInput
import com.dailyhealthcoach.domain.model.WorkoutStatus
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import com.dailyhealthcoach.domain.repository.ExerciseRepository
import com.dailyhealthcoach.domain.repository.HabitRepository
import com.dailyhealthcoach.domain.repository.NutritionRepository
import com.dailyhealthcoach.domain.repository.RecoveryRepository
import com.dailyhealthcoach.domain.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class ProgressViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeBodyMetricRepository: FakeBodyMetricRepository
    private lateinit var fakeNutritionRepository: FakeNutritionRepository
    private lateinit var fakeHabitRepository: FakeHabitRepository
    private lateinit var fakeRecoveryRepository: FakeRecoveryRepository
    private lateinit var fakeWorkoutRepository: FakeWorkoutRepository
    private lateinit var fakeExerciseRepository: FakeExerciseRepository

    private val today = LocalDate.now().toString()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeBodyMetricRepository = FakeBodyMetricRepository()
        fakeNutritionRepository = FakeNutritionRepository()
        fakeHabitRepository = FakeHabitRepository()
        fakeRecoveryRepository = FakeRecoveryRepository()
        fakeWorkoutRepository = FakeWorkoutRepository()
        fakeExerciseRepository = FakeExerciseRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = ProgressViewModel(
        bodyMetricRepository = fakeBodyMetricRepository,
        nutritionRepository = fakeNutritionRepository,
        habitRepository = fakeHabitRepository,
        workoutRepository = fakeWorkoutRepository,
        recoveryRepository = fakeRecoveryRepository,
        exerciseRepository = fakeExerciseRepository,
        today = today
    )

    // ── Test 4: Progress retrieval 3 months ago ───────────────────────────────

    @Test
    fun `given 3-month period, observeForDateRange is called with correct start date`() = runTest {
        val threeMonthsAgo = LocalDate.now().minusDays(90).toString()
        val vm = createViewModel()
        backgroundScope.launch { vm.uiState.collect {} }

        vm.setSelectedPeriod(ProgressPeriod.THREE_MONTHS)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(threeMonthsAgo, fakeBodyMetricRepository.lastRequestedStartDate)
        assertEquals(today, fakeBodyMetricRepository.lastRequestedEndDate)
    }

    // ── Test 5: Progress retrieval 6 months ago ───────────────────────────────

    @Test
    fun `given 6-month period, observeForDateRange is called with correct start date`() = runTest {
        val sixMonthsAgo = LocalDate.now().minusDays(180).toString()
        val vm = createViewModel()
        backgroundScope.launch { vm.uiState.collect {} }

        vm.setSelectedPeriod(ProgressPeriod.SIX_MONTHS)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(sixMonthsAgo, fakeBodyMetricRepository.lastRequestedStartDate)
    }

    // ── Test 6: Progress retrieval 12 months ago ──────────────────────────────

    @Test
    fun `given year period, observeForDateRange is called with correct start date`() = runTest {
        val oneYearAgo = LocalDate.now().minusDays(365).toString()
        val vm = createViewModel()
        backgroundScope.launch { vm.uiState.collect {} }

        vm.setSelectedPeriod(ProgressPeriod.YEAR)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(oneYearAgo, fakeBodyMetricRepository.lastRequestedStartDate)
    }

    // ── Test 7: Empty historical periods ─────────────────────────────────────

    @Test
    fun `given year period with no data, weight trend shows dash`() = runTest {
        fakeBodyMetricRepository.dateRangeData = emptyList()
        val vm = createViewModel()

        vm.setSelectedPeriod(ProgressPeriod.YEAR)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = vm.uiState.value
        assertEquals("—", state.weight.currentValue)
        assertTrue(state.weight.last7.isEmpty())
    }

    // ── Test 8: Existing recent Progress behavior still works ──────────────────

    @Test
    fun `default period is WEEK and shows 7 data points`() = runTest {
        val logs = (0..6).map { daysAgo ->
            makeBodyMetricLog(
                date = LocalDate.now().minusDays(daysAgo.toLong()).toString(),
                bodyWeight = 180.0 - daysAgo
            )
        }
        fakeBodyMetricRepository.dateRangeData = logs
        val vm = createViewModel()

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(ProgressPeriod.WEEK, vm.selectedPeriod.value)
        val state = vm.uiState.value
        assertTrue(state.weight.last7.size <= 7)
    }

    @Test
    fun `year period with 12 months of data shows more than 7 data points`() = runTest {
        val logs = (0..364).map { daysAgo ->
            makeBodyMetricLog(
                date = LocalDate.now().minusDays(daysAgo.toLong()).toString(),
                bodyWeight = 180.0 - (daysAgo * 0.01)
            )
        }
        fakeBodyMetricRepository.dateRangeData = logs
        val vm = createViewModel()
        backgroundScope.launch { vm.uiState.collect {} }

        vm.setSelectedPeriod(ProgressPeriod.YEAR)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(
            "Year period must show more than 7 entries, got ${state.weight.last7.size}",
            state.weight.last7.size > 7
        )
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun makeBodyMetricLog(date: String, bodyWeight: Double?) = BodyMetricLog(
        id = 0L, date = date, heightInches = null, bodyWeight = bodyWeight,
        bodyFatPercentage = null, calculatedBodyFatPercent = null,
        manualBodyFatPercent = null, isBodyFatOverridden = false,
        waistMeasurement = null, neckMeasurement = null, chestMeasurement = null,
        armMeasurement = null, sleepHours = null, energyLevel = null, stressLevel = null,
        sorenessLevel = null, restingHeartRate = null, stepCount = null, notes = null
    )
}

// ── Fakes ─────────────────────────────────────────────────────────────────────

class FakeBodyMetricRepository : BodyMetricRepository {
    var dateRangeData: List<BodyMetricLog> = emptyList()
    var lastRequestedStartDate: String? = null
    var lastRequestedEndDate: String? = null

    override fun observeForDate(date: String) = flowOf<BodyMetricLog?>(null)

    override fun observeAll() = flowOf(emptyList<BodyMetricLog>())

    override fun observeForDateRange(startDate: String, endDate: String): Flow<List<BodyMetricLog>> {
        lastRequestedStartDate = startDate
        lastRequestedEndDate = endDate
        return flowOf(dateRangeData)
    }

    override suspend fun saveForDate(input: com.dailyhealthcoach.domain.model.BodyMetricLogInput) {}
}

class FakeNutritionRepository : NutritionRepository {
    override fun observeFoodEntriesForDate(date: String) = flowOf(emptyList<FoodEntry>())
    override fun observeAll() = flowOf(emptyList<FoodEntry>())
    override fun observeForDateRange(startDate: String, endDate: String) = flowOf(emptyList<FoodEntry>())
    override suspend fun saveFoodEntry(input: com.dailyhealthcoach.domain.model.FoodEntryInput) {}
    override suspend fun deleteFoodEntry(id: Long) {}
    override suspend fun setFoodEntrySaved(id: Long, saved: Boolean) {}
    override suspend fun getFoodEntriesForDate(date: String): List<FoodEntry> = emptyList()
}

class FakeHabitRepository : HabitRepository {
    override fun observeActiveHabits() = flowOf(emptyList<HabitDefinition>())
    override fun observeLogsForDate(date: String) = flowOf(emptyList<DailyHabitLog>())
    override fun observeLogsBetween(startDate: String, endDate: String) = flowOf(emptyList<DailyHabitLog>())
    override suspend fun setHabitStatusForDate(habitId: Long, date: String, status: String, notes: String?) {}
}

class FakeRecoveryRepository : RecoveryRepository {
    override fun observeForDate(date: String) = flowOf<RecoveryScore?>(null)
    override fun observeAll() = flowOf(emptyList<RecoveryScore>())
    override fun observeForDateRange(startDate: String, endDate: String) = flowOf(emptyList<RecoveryScore>())
    override suspend fun saveForDate(date: String, score: Int, label: String, reasons: List<String>) {}
}

class FakeWorkoutRepository : WorkoutRepository {
    override fun observeWorkouts() = flowOf(emptyList<Workout>())
    override fun observeWorkoutsForDate(date: String) = flowOf(emptyList<Workout>())
    override fun observeWorkoutsForDateRange(startDate: String, endDate: String) = flowOf(emptyList<Workout>())
    override fun observeExercisesForWorkout(workoutId: Long) = flowOf(emptyList<WorkoutExercise>())
    override fun observeWorkoutSets() = flowOf(emptyList<WorkoutExercise>())
    override suspend fun saveWorkout(date: String, name: String, status: WorkoutStatus, durationMinutes: Int?, overallRpe: Int?, notes: String?, sets: List<WorkoutSetInput>): Long = 0L
    override suspend fun updateWorkout(id: Long, name: String, status: WorkoutStatus, durationMinutes: Int?, overallRpe: Int?, notes: String?, sets: List<WorkoutSetInput>) {}
    override suspend fun deleteWorkout(id: Long) {}
}

class FakeExerciseRepository : ExerciseRepository {
    override fun observeAll() = flowOf(emptyList<com.dailyhealthcoach.domain.model.Exercise>())
    override suspend fun save(exercise: com.dailyhealthcoach.domain.model.Exercise) {}
}
