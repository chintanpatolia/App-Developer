# Habit Name Substring Matching in Recovery & Recommendation

## Symptoms
- Recovery score never awards "Whole foods habit" bonus even when habit is marked complete.
- Breathwork bonus not applied despite high stress + breathwork completion.
- "Late eating" penalty not applied correctly.
- Recommendation service misidentifies habit completion.

## Diagnostics
`RecoveryScoreCalculator` and `NextDayRecommendationService` match habits by name fragment:
```kotlin
// RecoveryScoreCalculator.kt:117, 122, 148, 153
if (input.isHabitComplete("whole")) { score += 5 ... }
if (input.isHabitComplete("sunlight")) { score += 5 ... }
if (input.isHabitNotDone("late eating")) { score -= 5 ... }
if (... input.isHabitComplete("breath")) { score += 5 ... }

private fun RecoveryScoreInput.isHabitComplete(nameFragment: String): Boolean {
    val matchingIds = habitDefinitions
        .filter { it.name.contains(nameFragment, ignoreCase = true) }
        .map { it.id }.toSet()
    return habitLogs.any { it.habitDefinitionId in matchingIds && it.status == "COMPLETE" }
}
```

**Root cause:** If a habit's `name` in `habit_definitions` does not contain the fragment string, matching fails silently (no exception, no score adjustment).

**Fragments relied upon:**
| Fragment | Seeded habit name | Sensitivity |
|----------|------------------|-------------|
| `"whole"` | "Whole foods" | case-insensitive |
| `"sunlight"` | "Morning sunlight" | case-insensitive |
| `"breath"` | "Breathwork" | case-insensitive |
| `"late eating"` | "Avoid late eating" | case-insensitive |

## Fix
1. Do NOT rename the seeded habits in `DatabaseSeeder.kt` without also updating the fragments in `RecoveryScoreCalculator.kt` and `NextDayRecommendationService.kt`.
2. If renaming is necessary, update the `nameFragment` strings in both services.
3. Long-term: replace name matching with a typed `HabitType` enum stored on the `HabitDefinition`.

## Affected Versions
All versions. Seeded habits are set once on first launch; users who installed early have the original names.

## Prevention
- Treat the 4 fragment strings as implicit contracts with the seeded habit names.
- Add a comment in `RecoveryScoreCalculator.kt` above `isHabitComplete` calls noting the dependency on seeded names.

## Related
- `architecture/database-seeder.md`
- `architecture/domain-services.md`
