# GetDashboardSummaryUseCase

## Responsibility
The most complex use case. Combines 7+ Room Flows into a single `Flow<DashboardSummary>`, computes recovery score and next-day recommendation on each emission, and persists both back to the database via `onEach`.

## Interfaces & Dependencies
`domain/usecase/GetDashboardSummaryUseCase.kt`

**Constructor dependencies (all injected by AppContainer):**
- `HabitRepository`, `MacroTargetRepository`, `NutritionRepository`, `BodyMetricRepository`
- `WorkoutRepository`, `ExerciseRepository`
- `RecoveryRepository`, `DailyRecommendationRepository`
- `RecoveryScoreCalculator`, `NextDayRecommendationService`

**Invoked as operator:** `getDashboardSummaryUseCase(date: String): Flow<DashboardSummary>`

## Data Flow
```
invoke(date)
  ├── combine(habits, habitLogs, macroTarget, foodEntries, bodyMetricLog) → DashboardInputs
  └── combine(workouts, workoutSets, exercises) → WorkoutInputs
         ↓
  outer combine(DashboardInputs, WorkoutInputs) {
    compute recovery score (RecoveryScoreCalculator.calculate)
    compute recommendation (NextDayRecommendationService.calculate)
    → DashboardSummary
  }
  .onEach {
    recoveryRepository.saveForDate(...)
    dailyRecommendationRepository.saveForDate(...)
  }
```

**Date windows used:**
- `today` — passed in as ISO-8601 string
- `twoDaysAgo` = today - 2 days — for `recentWorkouts`
- `sixDaysAgo` = today - 6 days — for `weeklyWorkouts` (used in recommendation)

## Known Hardcoded Values
```kotlin
steps = 6_400          // line 113 — not from DB
stepGoal = 8_000       // line 114 — not from DB
sleepHours = inputs.bodyMetricLog?.sleepHours ?: 6.8  // line 115 — fallback
```
See `troubleshooting/hardcoded-step-sleep-placeholders.md`.

## Operational Notes
- The nested `combine()` is required because `combine()` accepts max 5 parameters; the outer call takes two intermediate combined Flows.
- `onEach` runs on whatever dispatcher the collector uses (viewModelScope → Main by default, but Room I/O operations are performed inside the DAO calls which switch to IO internally).
- Emitting to `StateFlow` with `SharingStarted.WhileSubscribed(5_000)` means the use case Flow stays active for 5 seconds after last subscriber — avoids restart on config change.

## Related
- `patterns/combine-flows.md`
- `architecture/domain-services.md`
- `troubleshooting/hardcoded-step-sleep-placeholders.md`
- `troubleshooting/flow-combine-complexity.md`
