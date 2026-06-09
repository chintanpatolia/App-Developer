# Hardcoded Step Count and Sleep Fallback on Dashboard

## Symptoms
- Dashboard always shows steps = 6,400 / goal = 8,000 regardless of what user has logged.
- Dashboard shows sleep = 6.8h if the user has not logged sleep in the Body screen today.
- `DashboardUiState.steps` never changes from initial value.

## Diagnostics
These are known intentional placeholders. Source locations:
```kotlin
// GetDashboardSummaryUseCase.kt:113-115
DashboardSummary(
    steps = 6_400,                                             // line 113 — hardcoded
    stepGoal = 8_000,                                          // line 114 — hardcoded
    sleepHours = inputs.bodyMetricLog?.sleepHours ?: 6.8,     // line 115 — 6.8 fallback
)
```

The `stepCount` field exists in `BodyMetricLogEntity` (column added in initial schema) but is never surfaced in the Body screen form.

## Fix (when implementing real step tracking)
Option A — Manual step entry via Body screen:
1. Add `stepCount` field to `BodyMetricFormUiState` and the Body screen form.
2. Populate `input.stepCount` in `BodyViewModel.saveMetrics()`.
3. In `GetDashboardSummaryUseCase`, replace `steps = 6_400` with `steps = inputs.bodyMetricLog?.stepCount ?: 0`.

Option B — Health Connect integration:
1. Add Health Connect SDK dependency.
2. Read step count from `StepsRecord` API.
3. Store in `BodyMetricLogEntity.stepCount` and surface on Dashboard.
See `decisions/no-health-connect.md` before proceeding.

## Sleep Fix
Sleep is already connected: `inputs.bodyMetricLog?.sleepHours ?: 6.8`. The fallback 6.8 shows when no Body log exists for today. Users must log sleep via the Body screen to see accurate data.

## Affected Versions
Present since initial commit. DB version 1+.

## Prevention
- Do NOT treat `steps = 6_400` as a real step count in recovery logic — the recovery calculator uses `sleepHours` and `energyLevel`, not step count directly.
- If removing the fallback, display "—" or null instead of 0 to signal missing data.

## Related
- `architecture/get-dashboard-use-case.md`
- `decisions/no-health-connect.md`
