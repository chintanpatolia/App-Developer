# String Date Convention

## Intent
Pass dates as ISO-8601 strings (`"YYYY-MM-DD"`) across all layer boundaries — entities, domain models, repository signatures, use cases, and ViewModels all use `String` for dates.

## Forces
- `LocalDate` is not storable in Room without a `TypeConverter`.
- Consistency: mixing `LocalDate` and `String` across layers causes repeated conversion bugs.
- `LocalDate.toString()` produces ISO-8601 by default — safe for string comparison and range queries.

## Solution
```kotlin
// All date fields in entities:
val date: String   // e.g., "2025-06-08"

// All repository interfaces:
fun observeForDate(date: String): Flow<...>

// All use cases:
operator fun invoke(date: String): Flow<...>

// ViewModels always call:
private val today = LocalDate.now().toString()
```

`LocalDate` is only used internally within `GetDashboardSummaryUseCase` and `NextDayRecommendationService` for date arithmetic (minusDays, ChronoUnit.DAYS.between), then immediately converted back to String for comparisons.

## String Range Queries
Because ISO-8601 sorts lexicographically, Room string range queries work:
```kotlin
val recentWorkouts = workouts.filter { it.date in twoDaysAgo..date }
```

## Anti-Patterns
- Do NOT store `LocalDate` in a Room entity without a `TypeConverter`.
- Do NOT convert to `LocalDate` at the repository boundary — keep as String.
- Do NOT use non-ISO date formats (e.g., "06/08/2025") — lexicographic range queries will break.

## Related
- `decisions/string-dates-vs-localdate.md`
