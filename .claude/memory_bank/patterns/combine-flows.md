# Nested combine() for Multi-Source Flows

## Intent
Merge more than 5 Room Flows into a single emission by nesting `combine()` calls, since the Kotlin coroutines `combine` overload accepts a maximum of 5 sources at once.

## Forces
- `GetDashboardSummaryUseCase` needs 8 independent Flows.
- `combine()` (5-arg variant) is the maximum single call.
- All sources must re-emit when any one changes.

## Solution
```kotlin
return combine(
    combine(                        // inner: 5 flows → DashboardInputs
        habitRepository.observeActiveHabits(),
        habitRepository.observeLogsForDate(date),
        macroTargetRepository.observeActiveTarget(),
        nutritionRepository.observeFoodEntriesForDate(date),
        bodyMetricRepository.observeForDate(date)
    ) { habits, logs, macroTarget, foodEntries, bodyMetricLog ->
        DashboardInputs(habits, logs, macroTarget, foodEntries, bodyMetricLog)
    },
    combine(                        // inner: 3 flows → WorkoutInputs
        workoutRepository.observeWorkouts(),
        workoutRepository.observeWorkoutSets(),
        exerciseRepository.observeExercises()
    ) { workouts, workoutSets, exercises ->
        WorkoutInputs(workouts, workoutSets, exercises)
    }
) { inputs, workoutInputs -> /* compute DashboardSummary */ }
```
Private data classes (`DashboardInputs`, `WorkoutInputs`) act as intermediate aggregates.

## Anti-Patterns
- Do NOT use `zip` — it waits for both sources to emit once each, not on every change.
- Do NOT use `flatMapLatest` to chain Flows — it cancels the inner Flow on each outer emission, causing missed updates.
- Do NOT add a 6th Flow to a single `combine()` call — use this nesting pattern.

## Related
- `architecture/get-dashboard-use-case.md`
- `patterns/flow-stateflow-ui.md`
- `troubleshooting/flow-combine-complexity.md`
