# Room Migration Failure

## Symptoms
- App crashes at startup with `java.lang.IllegalStateException: Migration didn't properly handle`
- Logcat: `A migration from X to Y was required but not found` — crash if no migration registered
- Logcat: `Expected: TableInfo{...} Found: TableInfo{...}` — migration ran but schema still differs from entity

## Diagnostics
1. Check `AppDatabase.kt` version number matches the latest `MIGRATION_N_M` target.
2. Check `AppDatabaseProvider.getDatabase()` includes the new migration in `.addMigrations(...)`.
3. Check that every new entity field has a matching `ALTER TABLE ADD COLUMN` in the migration.
4. For `NOT NULL` columns: verify SQL includes `DEFAULT ''` or `DEFAULT 0`.

```powershell
# Build and check for Room schema errors
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug 2>&1 | Select-String "Migration"
```

## Fix
### Checklist for a version bump:
1. Update entity class — add field with default value.
2. `AppDatabase.kt` — bump `version` integer.
3. `AppDatabaseProvider.kt` — add `private val MIGRATION_N_M = object : Migration(N, M) { ... }`.
4. `AppDatabaseProvider.getDatabase()` — add new migration to `.addMigrations(...)`.

### SQLite NOT NULL column syntax (required):
```sql
db.execSQL("ALTER TABLE recovery_scores ADD COLUMN label TEXT NOT NULL DEFAULT ''")
-- NOT: ADD COLUMN label TEXT NOT NULL   ← crashes SQLite
```

### If testing on a fresh install (no existing DB):
Room creates the full schema from entities directly — migration code is not run. The schema-vs-entity diff check still applies.

## Affected Versions
- Any DB version bump without matching migration.
- Current DB version: 4. Last migration: `MIGRATION_3_4` in `AppDatabaseProvider.kt:43`.

## Prevention
- Add the migration and entity change in the same commit.
- Build immediately after the change (`.\gradlew.bat assembleDebug`) — Room reports schema mismatches at compile time.
- Do NOT use `fallbackToDestructiveMigration()` — it silently wipes user data.

## Related
- `patterns/room-migration.md`
- `architecture/app-database.md`
- `decisions/export-schema-false.md`
