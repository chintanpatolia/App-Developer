# Flow combine() Complexity in GetDashboardSummaryUseCase

## Symptoms
- Dashboard data appears stale or doesn't update when a specific source changes.
- ViewModel does not re-emit after a workout or food entry is added.
- Compile error: `None of the following functions can be called with the arguments supplied` on `combine(...)` with more than 5 arguments.

## Diagnostics
`GetDashboardSummaryUseCase` uses a nested `combine()` structure because Kotlin's `combine` accepts a maximum of 5 sources:

```kotlin
// Outer combine:
combine(
    innerCombine1(habits, habitLogs, macroTarget, foodEntries, bodyMetricLog) { ... → DashboardInputs },
    innerCombine2(workouts, workoutSets, exercises) { ... → WorkoutInputs }
) { inputs, workoutInputs -> DashboardSummary }
.onEach { summary -> /* persist recovery + recommendation */ }
```

If a new Flow source is needed:
- If adding to the 5-source inner combine → not possible, must restructure.
- Correct approach: add a third inner `combine()` group or add the new source to the outer `combine()`.

## Fix
### Adding a 9th data source (e.g., userProfile Flow):
```kotlin
combine(
    innerCombine1(...),  // max 5
    innerCombine2(...),  // max 3
    userProfileRepository.observeUserProfile()  // 3rd arg to outer combine
) { inputs, workoutInputs, userProfile -> ... }
```
This expands the outer `combine` to 3 args.

### Debugging stale data:
1. Verify the DAO method returns `Flow<T>` (not a one-shot `suspend fun`).
2. Check that `observeActiveHabits()` uses `@Query` with no typo in table name.
3. Confirm the ViewModel's `stateIn` is using `WhileSubscribed` not `Lazily` — `Lazily` starts only on first collection and never restarts.

## Affected Versions
Present throughout codebase. Impacts any work adding new reactive data sources to the dashboard.

## Prevention
- Document the inner combine groupings when adding a new source.
- Prefer adding to the second inner combine (WorkoutInputs) if the data is workout-related; first inner combine (DashboardInputs) for daily health metrics.
- Run `assembleDebug` after adding a source — compile-time Flow type mismatches are caught.

## Related
- `patterns/combine-flows.md`
- `architecture/get-dashboard-use-case.md`
