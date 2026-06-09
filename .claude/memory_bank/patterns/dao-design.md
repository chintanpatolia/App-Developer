# DAO Design Pattern

## Intent
Keep DAOs thin — only SQL queries and `@Upsert`/`@Insert`. No business logic, no mapping, no coroutine management.

## Forces
- Room generates DAOs at compile time; complex logic in them is untestable without a real DB.
- All observable queries should return `Flow<T>` for reactive updates.
- Suspend functions for one-shot reads/writes.

## Solution
```kotlin
@Dao
interface BodyMetricLogDao {
    // Observable read — returns Flow<T?>
    @Query("SELECT * FROM body_metric_logs WHERE date = :date LIMIT 1")
    fun observeForDate(date: String): Flow<BodyMetricLogEntity?>

    // One-shot read for upsert logic
    @Query("SELECT * FROM body_metric_logs WHERE date = :date LIMIT 1")
    suspend fun getForDate(date: String): BodyMetricLogEntity?

    // Observable list
    @Query("SELECT * FROM body_metric_logs ORDER BY date DESC")
    fun observeAll(): Flow<List<BodyMetricLogEntity>>

    // Write
    @Upsert
    suspend fun upsert(log: BodyMetricLogEntity)
}
```

### Count guards (for seeding)
```kotlin
@Query("SELECT COUNT(*) FROM habit_definitions")
suspend fun countHabits(): Int
```

### Habit log special case
```kotlin
@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun insertIgnore(log: DailyHabitLogEntity): Long

@Query("UPDATE daily_habit_logs SET status = :status ... WHERE habitDefinitionId = :habitDefinitionId AND date = :date")
suspend fun updateStatus(...)
```

## Naming Conventions
- `observe*` — returns `Flow<T>` (live, reactive)
- `get*` — returns `T?` or `List<T>` (one-shot, suspend)
- `upsert` / `insert` / `update` — write operations (suspend)
- `count*` — count queries for guard checks (suspend)

## Anti-Patterns
- Do NOT put business logic in `@Query` SQL beyond simple filtering/ordering.
- Do NOT return `Entity` types from anything other than DAOs.
- Do NOT use `LiveData` — the project uses Flows exclusively.

## Related
- `patterns/upsert-pattern.md`
- `patterns/repository-pattern.md`
