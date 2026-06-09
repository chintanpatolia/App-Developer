# Room Migration Pattern

## Intent
Incrementally evolve the database schema while preserving existing user data, using raw SQL `Migration` objects registered in `AppDatabaseProvider`.

## Forces
- `exportSchema = false` means no auto-generated migration SQL — must write all SQL by hand.
- Migrations must be additive (ALTER TABLE ADD COLUMN) to avoid data loss.
- All migrations live in one place (`AppDatabaseProvider`) for easy audit.

## Solution
```kotlin
// AppDatabaseProvider.kt
private val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE daily_recommendations ADD COLUMN title TEXT NOT NULL DEFAULT ''")
        db.execSQL("ALTER TABLE daily_recommendations ADD COLUMN explanation TEXT NOT NULL DEFAULT ''")
        // ... more columns
    }
}

fun getDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(...)
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
        .build()
}
```

## Step-by-Step: Adding a New Migration
1. Update entity class — add the new field(s) with a default value.
2. Bump `version` in `@Database` annotation in `AppDatabase.kt`.
3. Add a new `MIGRATION_N_M` private val in `AppDatabaseProvider`.
4. Register it in `.addMigrations(...)`.
5. Build and run — Room will validate the schema matches the entity.

## SQL Defaults Required
SQLite `ALTER TABLE ADD COLUMN` requires `NOT NULL` columns to have a `DEFAULT`:
```sql
-- CORRECT:
ADD COLUMN label TEXT NOT NULL DEFAULT ''
-- WRONG (will crash at migration time):
ADD COLUMN label TEXT NOT NULL
```

## Anti-Patterns
- Do NOT use `fallbackToDestructiveMigration()` in production — it wipes user data.
- Do NOT skip version numbers (e.g., jump from 3 to 5) — Room validates the chain.
- Do NOT add a migration without also updating the entity — the schema diff will fail at runtime.

## Related
- `architecture/app-database.md`
- `decisions/export-schema-false.md`
- `troubleshooting/room-migration-failure.md`
