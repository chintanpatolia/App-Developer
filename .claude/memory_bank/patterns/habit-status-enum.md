# Habit Status & Frequency — Enum Storage Pattern

## Intent
Store enum-like values as strings in Room (not as Int ordinals), using a companion `storageValue` string and explicit conversion methods.

## Forces
- Integer ordinals break when enum entries are reordered.
- String values are human-readable in SQLite and debuggable without a lookup table.
- Conversion must handle unknown/null values gracefully (new app versions reading old data).

## Solution
```kotlin
// HabitStatus.kt
enum class HabitStatus(val storageValue: String) {
    COMPLETE("COMPLETE"),
    SKIPPED("SKIPPED"),
    NOT_DONE("NOT_DONE");

    companion object {
        fun fromStorageValue(value: String?): HabitStatus {
            return entries.firstOrNull { it.storageValue == value } ?: NOT_DONE
        }
    }
}

// HabitFrequency.kt
enum class HabitFrequency(val storageValue: String, val label: String) {
    DAILY("DAILY", "Daily"),
    WEEKLY_TARGET("WEEKLY_TARGET", "Weekly"),
    INTERVAL("INTERVAL", "Every 6-12 months"),
    AS_NEEDED("AS_NEEDED", "As needed");
    ...
}
```

Recovery score and recommendation logic use raw string comparisons against storage values:
```kotlin
it.status == "COMPLETE"   // in RecoveryScoreCalculator
workout.status == "COMPLETED"  // in NextDayRecommendationService
```

## Anti-Patterns
- Do NOT use `ordinal` — reordering entries breaks persisted data.
- Do NOT forget `?: NOT_DONE` fallback — `null` in the DB (no log row yet) must return a sensible default.

## Related
- `architecture/database-seeder.md`
- `patterns/entity-domain-mapping.md`
