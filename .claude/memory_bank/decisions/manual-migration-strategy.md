# ADR: Manual Migration Strategy

## Context
Room supports three migration approaches: manual `Migration` objects, `AutoMigration` (requires `exportSchema = true`), and destructive migrations. The project has `exportSchema = false`.

## Options
1. **AutoMigration** — Room generates SQL from schema diff JSON files. Requires `exportSchema = true` and annotation on `@Database`.
2. **Manual Migration** — Developer writes raw SQL in `Migration(from, to)` objects.
3. **Destructive** — Drop and recreate database on version bump. All user data is lost.

## Decision
Manual migrations defined as private `val MIGRATION_N_M` objects in `AppDatabaseProvider.kt`. Registered via `.addMigrations(...)`.

## Migration Conventions
- All migrations are `ALTER TABLE ADD COLUMN` (additive only).
- `NOT NULL` columns must have `DEFAULT ''` or `DEFAULT 0` in SQLite.
- Migration objects are named `MIGRATION_N_M` where N is the old version and M is the new version.
- All migrations live in `AppDatabaseProvider` — never scattered across files.

## Consequences
**Positive:**
- Full control over SQL — can handle complex schema changes if needed.
- Migrations are readable and auditable in one location.
- No dependency on exported schema files.

**Negative:**
- Developer must not forget to write the migration when bumping the version.
- If entity and migration are out of sync, app crashes with `IllegalStateException: Migration didn't properly handle` at startup.

## Related
- `patterns/room-migration.md`
- `architecture/app-database.md`
- `decisions/export-schema-false.md`
- `troubleshooting/room-migration-failure.md`
