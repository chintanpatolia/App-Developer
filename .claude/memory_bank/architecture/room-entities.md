# Room Entities — Data Layer Schema

## Responsibility
Entity classes map 1:1 to SQLite tables. Each entity is in `data/local/entity/`. Domain models (in `domain/model/`) are separate; repositories perform the mapping.

## Entity Inventory

### `HabitDefinitionEntity` — `habit_definitions`
`data/local/entity/HabitDefinitionEntity.kt`
- Fields: `id`, `name`, `description`, `frequencyType` (STRING enum), `targetPerWeek?`, `reminderWindow?`, `sortOrder`, `isActive`
- Filtered by `isActive = 1` in `HabitDefinitionDao.observeActiveHabits()`

### `DailyHabitLogEntity` — `daily_habit_logs`
- Fields: `id`, `habitDefinitionId`, `date` (ISO-8601 String), `status` (STRING enum), `notes?`, `updatedAt`
- Insert-or-ignore pattern followed by update on conflict (see `HabitRepositoryImpl`)

### `BodyMetricLogEntity` — `body_metric_logs`
`data/local/entity/BodyMetricLogEntity.kt`
- Unique index on `date`. One row per calendar day.
- Tracks: body weight, body fat (calculated + manual + override flag), measurements (waist, neck, chest, arm), subjective metrics (sleep, energy 1-10, stress 1-10, soreness 1-10, RHR, stepCount)
- Fields added in migration 1→2: `heightInches`, `calculatedBodyFatPercent`, `manualBodyFatPercent`, `isBodyFatOverridden`, `neckMeasurement`

### `RecoveryScoreEntity` — `recovery_scores`
`data/local/entity/RecoveryScoreEntity.kt`
- Unique index on `date`. Persisted by `GetDashboardSummaryUseCase.onEach`.
- Stores computed score + contribution breakdown columns (sleep, protein, soreness, stress, workout, steps, restDay)
- Fields added in migration 2→3: `label`, `reasonText`, `updatedAt`

### `DailyRecommendationEntity` — `daily_recommendations`
`data/local/entity/DailyRecommendationEntity.kt`
- Unique index on `date`. Persisted by `GetDashboardSummaryUseCase.onEach`.
- `reasonBullets` stored as newline-joined string; split back to `List<String>` in `toDomain()`
- Fields added in migration 3→4: `title`, `explanation`, `suggestedFocus`, `reasonBullets`, `updatedAt`

### Other Entities (stable, not recently migrated)
- `UserProfileEntity` — `user_profiles`, seeded on first launch
- `ExerciseEntity` — `exercises`, seeded with 16 default exercises
- `WorkoutEntity` — `workouts`, tracks date, status, overallRpe
- `WorkoutExerciseEntity` — `workout_exercises`, per-set data with exerciseId, rpe
- `FoodEntryEntity` — `food_entries`, per-meal macros
- `MacroTargetEntity` — `macro_targets`, default 170–200g protein

## Interfaces & Dependencies
- All entities imported in `AppDatabase.kt:16-27`
- Mapping functions (Entity→Domain) live in repository `*Impl` files as private extension functions

## Related
- `architecture/app-database.md`
- `patterns/entity-domain-mapping.md`
- `patterns/upsert-pattern.md`
