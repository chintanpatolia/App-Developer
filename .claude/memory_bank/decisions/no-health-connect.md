# ADR: No Health Connect Integration

## Context
Android Health Connect (formerly Google Fit) provides a unified API for reading step counts, sleep data, heart rate, and other health metrics from wearables and health apps. Integrating it would automate data that is currently entered manually (steps, sleep).

## Options
1. **Health Connect integration** — Read steps, sleep, HRV from device/wearables automatically.
2. **Manual entry only** — Users log all metrics in the Body screen.

## Decision
No Health Connect. All data is entered manually via the Body screen (`BodyViewModel`, `BodyMetricRepositoryImpl`).

## Constraints
- Steps and sleep are currently **hardcoded placeholders** on the Dashboard:
  - `steps = 6_400` (`GetDashboardSummaryUseCase.kt:113`)
  - `stepGoal = 8_000` (`GetDashboardSummaryUseCase.kt:114`)
  - `sleepHours = inputs.bodyMetricLog?.sleepHours ?: 6.8` (fallback if not logged)
- The real `stepCount` field exists in `BodyMetricLogEntity` but is never populated from user input in the current Body screen.

## Consequences
**Positive:**
- No Health Connect permission dialog.
- No dependency on Health Connect SDK.
- No complexity around data sourcing or conflict resolution between logged and synced values.

**Negative:**
- Steps are always a hardcoded placeholder unless logged manually.
- Sleep is a fallback (6.8h) unless the user logs it in the Body screen.
- Wearable data integration requires future work.

## Related
- `decisions/local-first.md`
- `troubleshooting/hardcoded-step-sleep-placeholders.md`
