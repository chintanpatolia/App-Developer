package com.dailyhealthcoach.data.repository

import com.dailyhealthcoach.data.local.dao.ExerciseDao
import com.dailyhealthcoach.data.local.entity.ExerciseEntity
import com.dailyhealthcoach.domain.model.Exercise
import com.dailyhealthcoach.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseRepositoryImpl(
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {
    override fun observeExercises(): Flow<List<Exercise>> {
        return exerciseDao.observeExercises().map { exercises -> exercises.map { it.toDomain() } }
    }
}

private fun ExerciseEntity.toDomain(): Exercise {
    return Exercise(
        id = id,
        name = name,
        muscleGroup = muscleGroup,
        equipmentType = equipmentType,
        movementPattern = movementPattern,
        notes = notes,
        isCustom = isCustom
    )
}
