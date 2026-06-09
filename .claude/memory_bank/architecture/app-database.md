# AppDatabase & AppDatabaseProvider

## Responsibility
`AppDatabase` is the Room database definition (version 4). `AppDatabaseProvider` is the thread-safe singleton builder that registers all migrations.

## Interfaces & Dependencies
- **Source:** `data/local/AppDatabase.kt`, `data/local/AppDatabaseProvider.kt`
- **Entities (11 tables):**
  - `UserProfileEntity` — `user_profiles`
  - `HabitDefinitionEntity` — `habit_definitions`
  - `DailyHabitLogEntity` — `daily_habit_logs`
  - `ExerciseEntity` — `exercises`
  - `WorkoutEntity` — `workouts`
  - `WorkoutExerciseEntity` — `workout_exercises`
  - `BodyMetricLogEntity` — `body_metric_logs` (unique index on `date`)
  - `FoodEntryEntity` — `food_entries`
  - `MacroTargetEntity` — `macro_targets`
  - `RecoveryScoreEntity` — `recovery_scores` (unique index on `date`)
  - `DailyRecommendationEntity` — `daily_recommendations` (unique index on `date`)
- **DAOs exposed:** one abstract function per entity (11 total)
- **DB file name:** `daily_health_coach.db`

## Data Flow
```
AppDatabaseProvider.getDatabase(context)
  → Room.databaseBuilder(...)
      .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
      .build()
  → AppDatabase (singleton, @Volatile double-checked locking)
```

## Operational Notes
- `exportSchema = false` — no schema JSON files are generated. See `decisions/export-schema-false.md`.
- **Current version: 4.** All migrations are raw SQL `ALTER TABLE` in `AppDatabaseProvider`.
- Unique indices on `date` in `body_metric_logs`, `recovery_scores`, `daily_recommendations` enforce one-row-per-day semantics.
- `AppDatabase` is accessed only via `AppContainer`; never access it directly from UI layer.

## Migration History
| Version | Change |
|---------|--------|
| 1→2 | Added 5 columns to `body_metric_logs` (heightInches, body fat fields, neckMeasurement) |
| 2→3 | Added 3 columns to `recovery_scores` (label, reasonText, updatedAt) |
| 3→4 | Added 5 columns to `daily_recommendations` (title, explanation, suggestedFocus, reasonBullets, updatedAt) |

## Related
- `architecture/app-container.md`
- `patterns/room-migration.md`
- `troubleshooting/room-migration-failure.md`
- `decisions/export-schema-false.md`
