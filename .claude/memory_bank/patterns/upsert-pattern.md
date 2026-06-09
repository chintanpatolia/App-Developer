# Room Upsert Pattern

## Intent
Insert-or-replace a row that has a unique constraint (e.g., one row per date), preserving immutable fields (`id`, `createdAt`) while updating mutable ones.

## Forces
- Entities with `@Index(value = ["date"], unique = true)` need upsert semantics.
- `createdAt` must remain unchanged after first insert.
- `@Upsert` annotation handles the SQL but cannot merge fields — caller must provide the full entity.

## Solution
```kotlin
// 1. Read existing row (suspend, not a Flow)
val existing = bodyMetricLogDao.getForDate(input.date)

// 2. Build entity, preserving id and createdAt
bodyMetricLogDao.upsert(
    BodyMetricLogEntity(
        id = existing?.id ?: 0,         // 0 = new insert; non-zero = update
        createdAt = existing?.createdAt ?: now,
        updatedAt = now,
        // ... all other fields from input
    )
)
```
The DAO uses `@Upsert` (Room 2.5+):
```kotlin
@Upsert
suspend fun upsert(log: BodyMetricLogEntity)
```

## Habit Log Exception
`DailyHabitLogEntity` does not use `@Upsert` — it uses insert-ignore + explicit update:
```kotlin
val insertedId = dailyHabitLogDao.insertIgnore(entity)
if (insertedId == -1L) {
    dailyHabitLogDao.updateStatus(habitDefinitionId, date, status, notes, updatedAt)
}
```
Reason: `@OnConflictStrategy.IGNORE` is more explicit about the "do nothing on first conflict" intent here.

## Entities using @Upsert
- `BodyMetricLogDao.upsert`
- `RecoveryScoreDao.upsert`
- `DailyRecommendationDao.upsert`
- `UserProfileDao.upsert`

## Anti-Patterns
- Do NOT use `@Insert(onConflict = REPLACE)` — it deletes and re-inserts, changing the `id` and breaking foreign keys.
- Do NOT forget to read the existing row before upserting — `createdAt` will be lost.

## Related
- `patterns/entity-domain-mapping.md`
- `architecture/repositories.md`
