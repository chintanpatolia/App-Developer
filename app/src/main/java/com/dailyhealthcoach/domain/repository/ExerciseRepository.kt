package com.dailyhealthcoach.domain.repository

import com.dailyhealthcoach.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun observeExercises(): Flow<List<Exercise>>
}
