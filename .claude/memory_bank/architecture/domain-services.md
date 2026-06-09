# Domain Services — RecoveryScoreCalculator & NextDayRecommendationService

## Responsibility
Two pure-Kotlin services that compute derived health metrics from raw data snapshots. No Android dependencies, no coroutines, no side effects.

## RecoveryScoreCalculator
`domain/recovery/RecoveryScoreCalculator.kt`

**Input:** `RecoveryScoreInput` (bodyMetricLog, foodEntries, habitDefinitions, habitLogs, todayWorkouts, recentWorkouts, workoutSets)
**Output:** `RecoveryScoreResult?` (score 0–100, label, reasons List<String>) — null if `hasEnoughData()` returns false.

**Scoring algorithm (starts at 50):**
| Factor | Range | Delta |
|--------|-------|-------|
| Sleep | ≥7.5h | +20 |
| Sleep | 7.0–7.4h | +15 |
| Sleep | 6.0–6.9h | +5 |
| Sleep | 5.0–5.9h | -10 |
| Sleep | <5h | -20 |
| Protein | ≥170g | +15 |
| Protein | 140–169g | +5 |
| Protein | 100–139g (with data) | -5 |
| Protein | <100g (with data) | -15 |
| Energy | 8–10 | +10 |
| Energy | 6–7 | +5 |
| Energy | 4–5 | -5 |
| Energy | 1–3 | -10 |
| Stress | 1–3 | +10 |
| Stress | 7–8 | -10 |
| Stress | 9–10 | -20 |
| Soreness | 1–3 | +10 |
| Soreness | 7–8 | -10 |
| Soreness | 9–10 | -20 |
| Whole foods habit complete | — | +5 |
| Sunlight habit complete | — | +5 |
| Breathwork during high stress | stress≥7 | +5 |
| Late eating NOT_DONE | — | -5 |
| Recent RPE ≥9 + sets≥20 | — | -15 |
| Recent RPE ≥8 | — | -10 |
| Any recent workout | — | -5 |

**Floor rule:** Score is clamped to `coerceIn(0, 100)`. Score floored at 25 unless `isExtremeLowRecoveryDay()` (sleep<5h AND protein<100g AND stress≥9 AND soreness≥9).
**Labels:** 80–100=High recovery, 60–79=Moderate, 40–59=Low, 0–39=Very low.
**Reasons:** First 4 non-empty reasons emitted; default message if none.

## NextDayRecommendationService
`domain/recommendation/NextDayRecommendationService.kt`

**Input:** `RecommendationInput` (all RecoveryScoreInput fields + date, recoveryScore, weeklyWorkouts, exercises)
**Output:** `DailyRecommendation?` — null if `hasEnoughData()` returns false.

**`shouldRest` gate (evaluated first):**
```
shouldRest = recoveryScore < 25
          OR (recoveryScore < 35 AND restDayFatigueSignalCount >= 2 AND hasStrongRestSignal)
```
- `restDayFatigueSignalCount` counts: sleep<6h, soreness≥8, energy≤3, stress≥8, hardWorkoutTodayOrYesterday, recentAvgRpe≥8
- `hasStrongRestSignal` = soreness≥8 OR energy≤3 OR hardWorkoutTodayOrYesterday OR recentAvgRpe≥8
- **Stress alone does not trigger Rest.** Protein below target is not a fatigue signal at all.
- Sleep 5–6h alone (count=1) does not reach the ≥2 threshold.

**Decision tree:**
```
shouldRest                                                   → REST
score < 40                                                   → WALKING_MOBILITY
score < 50 AND weeklyStrengthCount < 3 AND soreness ≤ 6     → LOWER_INTENSITY_STRENGTH
score < 50                                                   → ACTIVE_RECOVERY
score < 70 AND (protein<140 OR sleep<6.5 OR hard workout today) → LOWER_INTENSITY_STRENGTH
score < 70                                                   → STRENGTH
weeklyStrengthCount < 4 AND no hard workout today            → STRENGTH
else                                                         → ACTIVE_RECOVERY
cap: weeklyStrengthCount ≥ 4 AND STRENGTH recommended        → demote to ACTIVE_RECOVERY
```

**5 recommendation types:** REST, WALKING_MOBILITY, ACTIVE_RECOVERY, LOWER_INTENSITY_STRENGTH, STRENGTH

**Validated scenarios (2026-06-09):**
- Score 25, stress 8, soreness <8 → Walking / mobility (hasStrongRestSignal=false blocks rest)
- Score 25, stress 8, soreness 8 → Rest (soreness≥8 triggers hasStrongRestSignal)
- Score 18 → Rest (score < 25 unconditional)
- Score 45, moderate soreness → Active recovery or Lower intensity strength

## Interfaces & Dependencies
- Both instantiated in `AppContainer.kt:79-81` (stateless, no constructor params)
- Called inline inside `GetDashboardSummaryUseCase` on each Flow emission
- Results persisted via `recoveryRepository.saveForDate` and `dailyRecommendationRepository.saveForDate` in `onEach`

## Related
- `architecture/get-dashboard-use-case.md`
- `patterns/recovery-score-calculation.md`
- `decisions/local-first.md`
