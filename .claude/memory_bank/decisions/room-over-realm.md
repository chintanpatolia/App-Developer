# ADR: Room (SQLite) Over Realm or Other Databases

## Context
Android has several local database options: Room (SQLite), Realm, SQLDelight, ObjectBox, and DataStore (for key-value only). The app requires relational queries and reactive Flows.

## Options
1. **Room** — Google's SQLite ORM, first-party Jetpack, native Flow support, compile-time query verification.
2. **Realm** — Object database with MongoDB ecosystem, reactive, but non-relational and large dependency.
3. **SQLDelight** — Kotlin-first SQL with type-safe queries and multiplatform support.
4. **DataStore** — Protocol Buffers key-value store, not suited for relational data.

## Decision
Room with SQLite.

## Consequences
**Positive:**
- First-party Jetpack library — well-supported, well-documented.
- Compile-time SQL query validation.
- Native `Flow<T>` emission from queries.
- `@Upsert` (Room 2.5+) simplifies insert-or-update.
- No additional SQLite setup — uses Android's built-in SQLite.

**Negative:**
- Schema changes require manually written migrations.
- `@Entity` annotations couple data classes to Android.
- No multiplatform support (not needed here).

## Related
- `architecture/app-database.md`
- `patterns/room-migration.md`
- `decisions/export-schema-false.md`
