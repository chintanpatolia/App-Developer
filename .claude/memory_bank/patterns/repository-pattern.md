# Repository Pattern

## Intent
Decouple domain logic from Room-specific types. Domain layer depends only on interfaces returning domain models; data layer provides the implementations.

## Forces
- Room entities contain Android/Room annotations — domain models must be annotation-free.
- Repository interface must be testable with fakes.
- Each repository should own its mapping logic without leaking it to callers.

## Solution
1. Define interface in `domain/repository/` with `Flow<DomainModel>` reads and `suspend fun` writes.
2. Implement in `data/repository/` with private `Entity.toDomain()` extension function.
3. Wire in `AppContainer` — interface type on the property, impl as the RHS.

## Examples
```kotlin
// domain/repository/HabitRepository.kt
interface HabitRepository {
    fun observeActiveHabits(): Flow<List<HabitDefinition>>
    fun observeLogsForDate(date: String): Flow<List<DailyHabitLog>>
    suspend fun setHabitStatusForDate(habitDefinitionId: Long, date: String, status: String, notes: String? = null)
}

// data/repository/HabitRepositoryImpl.kt — mapping
private fun HabitDefinitionEntity.toDomain(): HabitDefinition = HabitDefinition(
    id = id, name = name, description = description,
    frequencyType = frequencyType, targetPerWeek = targetPerWeek, sortOrder = sortOrder
)
```

## Anti-Patterns
- Do NOT return `Flow<HabitDefinitionEntity>` from a repository — always map to domain type.
- Do NOT put mapping logic in the DAO or in the ViewModel.
- Do NOT expose Room `@Transaction` objects as domain models.

## Related
- `patterns/entity-domain-mapping.md`
- `patterns/upsert-pattern.md`
- `architecture/repositories.md`
