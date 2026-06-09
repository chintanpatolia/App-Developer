# Repository Layer

## Responsibility
Repositories bridge Room DAOs (data layer) and domain interfaces. Each `*RepositoryImpl` in `data/repository/` implements the corresponding interface in `domain/repository/`, performing Entity↔Domain mapping.

## Interfaces & Dependencies
All repository interfaces use `Flow<T>` for observable reads and `suspend fun` for writes.

| Interface | Impl | DAOs used |
|-----------|------|-----------|
| `HabitRepository` | `HabitRepositoryImpl` | `HabitDefinitionDao`, `DailyHabitLogDao` |
| `BodyMetricRepository` | `BodyMetricRepositoryImpl` | `BodyMetricLogDao` |
| `NutritionRepository` | `NutritionRepositoryImpl` | `FoodEntryDao` |
| `RecoveryRepository` | `RecoveryRepositoryImpl` | `RecoveryScoreDao` |
| `DailyRecommendationRepository` | `DailyRecommendationRepositoryImpl` | `DailyRecommendationDao` |
| `WorkoutRepository` | `WorkoutRepositoryImpl` | `WorkoutDao`, `WorkoutExerciseDao` |
| `ExerciseRepository` | `ExerciseRepositoryImpl` | `ExerciseDao` |
| `MacroTargetRepository` | `MacroTargetRepositoryImpl` | `MacroTargetDao` |
| `UserProfileRepository` | `UserProfileRepositoryImpl` | `UserProfileDao` |

## Data Flow
```
DAO (Room Flow) → Repository.map { entity.toDomain() } → Domain Flow
ViewModel/UseCase → Repository.saveX() → Repository.upsert(entity)
```

### Upsert pattern (BodyMetricRepositoryImpl, line 25–51):
1. Call `dao.getForDate(date)` to get existing entity (preserves `id`, `createdAt`).
2. Build new entity with `id = existing?.id ?: 0`.
3. Call `dao.upsert(...)` — Room `@Upsert` handles insert-or-replace.

### Habit log insert-or-update pattern (HabitRepositoryImpl, line 35–53):
1. `insertIgnore` returns `-1L` if row already exists.
2. On `-1L`, call `updateStatus` explicitly.

## Operational Notes
- All `toDomain()` mapping is a private extension function at the bottom of each `*RepositoryImpl` file.
- `newlineToList` split: `DailyRecommendationRepositoryImpl` and `RecoveryRepositoryImpl` store `List<String>` as `"\n"`-joined text and split back with `.lines().filter { it.isNotBlank() }`.
- Dates are always passed as ISO-8601 String, never `LocalDate`.

## Related
- `patterns/repository-pattern.md`
- `patterns/entity-domain-mapping.md`
- `architecture/room-entities.md`
