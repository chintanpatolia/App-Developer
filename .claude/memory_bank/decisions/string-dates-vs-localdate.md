# ADR: String Dates vs LocalDate at Layer Boundaries

## Context
Room cannot store `java.time.LocalDate` without a `TypeConverter`. The codebase needs consistent date handling across entities, domain models, repository interfaces, use cases, and ViewModels.

## Options
1. **`LocalDate` everywhere with TypeConverter** — Type-safe, IDE auto-complete, no parsing errors. Requires a `TypeConverter` in Room and conversion in every repository.
2. **ISO-8601 `String` everywhere** — No TypeConverter needed, lexicographically sortable in SQL, consistent across all layers.
3. **`Long` epoch days** — Compact, fast for arithmetic. Less readable in debug tools.

## Decision
ISO-8601 `String` (`"YYYY-MM-DD"` from `LocalDate.toString()`) across all layer boundaries. `LocalDate` used only internally within domain logic for arithmetic.

## Consequences
**Positive:**
- No `TypeConverter` required.
- Room `@Query` range comparisons work: `date BETWEEN :start AND :end`.
- In-memory list filtering also works: `workouts.filter { it.date in twoDaysAgo..date }`.
- Human-readable in SQLite database inspector.

**Negative:**
- No compile-time date validity — invalid date strings crash at `LocalDate.parse()`.
- All callers must pass `LocalDate.now().toString()` instead of `LocalDate.now()`.
- Timezone not captured — dates are always the local calendar date.

## Usage Pattern
```kotlin
// ViewModels:
private val today = LocalDate.now().toString()

// Domain services needing arithmetic:
val currentDate = LocalDate.parse(date)  // then back to string for output
```

## Related
- `patterns/string-date-convention.md`
