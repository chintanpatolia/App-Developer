# ADR: exportSchema = false

## Context
Room's `@Database` annotation supports `exportSchema = true`, which generates JSON schema files under a configured directory. These files can be checked in to version control to track schema changes over time and to generate automatic migration scripts.

## Options
1. **exportSchema = true** — JSON schema exported to a directory; requires configuring `room.schemaLocation` in `build.gradle.kts`.
2. **exportSchema = false** — No JSON files generated; schema only exists in compiled Room annotations.

## Decision
`exportSchema = false` (`AppDatabase.kt:43`).

## Consequences
**Positive:**
- No schema export directory to configure or maintain.
- No JSON files checked in to version control.
- Simpler `build.gradle.kts` (no `room.schemaLocation` argument provider).

**Negative:**
- Cannot use Room's auto-migration scripts that read from schema JSON.
- No historical diff of schema changes in version control — rely on migration code in `AppDatabaseProvider` instead.
- All migrations must be written manually (which this project already does).

**If changed:** Add to `build.gradle.kts` (app module):
```kotlin
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
```
And create `app/schemas/` directory.

## Related
- `architecture/app-database.md`
- `patterns/room-migration.md`
- `troubleshooting/room-migration-failure.md`
