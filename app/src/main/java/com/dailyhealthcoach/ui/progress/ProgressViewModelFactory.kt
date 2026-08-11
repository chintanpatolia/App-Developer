package com.dailyhealthcoach.ui.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dailyhealthcoach.domain.repository.BodyMetricRepository
import com.dailyhealthcoach.domain.repository.ExerciseRepository
import com.dailyhealthcoach.domain.repository.HabitRepository
import com.dailyhealthcoach.domain.repository.NutritionRepository
import com.dailyhealthcoach.domain.repository.RecoveryRepository
import com.dailyhealthcoach.domain.repository.WorkoutRepository

class ProgressViewModelFactory(
    private val bodyMetricRepository: BodyMetricRepository,
    private val nutritionRepository: NutritionRepository,
    private val habitRepository: HabitRepository,
    private val workoutRepository: WorkoutRepository,
    private val recoveryRepository: RecoveryRepository,
    private val exerciseRepository: ExerciseRepository,
    private val today: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java)) {
            return ProgressViewModel(
                bodyMetricRepository = bodyMetricRepository,
                nutritionRepository = nutritionRepository,
                habitRepository = habitRepository,
                workoutRepository = workoutRepository,
                recoveryRepository = recoveryRepository,
                exerciseRepository = exerciseRepository,
                today = today
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
    }
}
