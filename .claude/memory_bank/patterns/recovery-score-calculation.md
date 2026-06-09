# Recovery Score Calculation Pattern

## Intent
Compute a bounded integer score (0–100) from multiple independent health signals, with additive adjustments from a baseline, a minimum floor, and human-readable reason bullets.

## Forces
- Multiple health signals (sleep, protein, energy, stress, soreness, habits, workout RPE) must combine into a single actionable score.
- Score should degrade gracefully when data is missing (null-safe via early returns).
- Reasons must be useful to the user — limit to 4 most relevant.
- Score should never appear artificially low on extreme data days (floor at 25 unless truly extreme).

## Solution
```kotlin
// RecoveryScoreCalculator.kt
var score = 50           // baseline
val reasons = mutableListOf<String>()

// Each signal mutates score and appends a reason
when {
    sleep >= 7.5 -> { score += 20; reasons += "Sleep was ${sleep.oneDecimal()} hours" }
    ...
}
// Final
val clamped = score.recoveryFloor(input).coerceIn(0, 100)
return RecoveryScoreResult(score = clamped, label = recoveryLabel(clamped), reasons = reasons.take(4))
```

Key design decisions:
- `hasEnoughData()` gates calculation — returns `null` if no signals logged at all.
- Floor at 25 unless `isExtremeLowRecoveryDay()` (sleep<5h AND protein<100g AND stress≥9 AND soreness≥9 all at once).
- Labels: 80–100 = "High recovery", 60–79 = "Moderate recovery", 40–59 = "Low recovery", else "Very low recovery".

## Input Struct
`RecoveryScoreInput` (co-located in `RecoveryScoreCalculator.kt:224`):
- `bodyMetricLog: BodyMetricLog?`
- `foodEntries: List<FoodEntry>`
- `habitDefinitions: List<HabitDefinition>` + `habitLogs: List<DailyHabitLog>`
- `todayWorkouts`, `recentWorkouts: List<Workout>`
- `workoutSets: List<WorkoutExercise>`

## Anti-Patterns
- Do NOT call `calculate()` without first checking that `hasEnoughData()` would return true — the public API already returns null, but don't display a "0" score when data is absent.
- Do NOT hardcode habit IDs — use name fragment matching (`isHabitComplete("whole")`) which is fragile but consistent with seeded names.

## Related
- `architecture/domain-services.md`
- `architecture/get-dashboard-use-case.md`
- `troubleshooting/habit-name-matching.md`
