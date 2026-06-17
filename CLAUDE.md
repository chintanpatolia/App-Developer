# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Daily Health Coach** — a local-first Android MVP for health, fitness, recovery, nutrition, and habit tracking. No backend, cloud sync, external APIs, Health Connect, or barcode scanning. All data is stored locally in a Room (SQLite) database.

## Build & Run

Open the project in Android Studio and run the `app` configuration on an emulator or connected device. Gradle syncs automatically.

```bash
# Build debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.dailyhealthcoach.ClassName"

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest
```

## Architecture

Clean architecture in three layers (`data` → `domain` → `ui`), with **no DI framework** — dependency injection is done manually through `AppContainer`.

### Dependency Injection

`AppContainer` (created in `DailyHealthCoachApplication`) is the manual DI root. It instantiates all repositories, domain services, and use cases, then passes them directly to ViewModel factories at composition sites in `DailyHealthCoachApp.kt`.

### Navigation

There is **no Compose Navigation library**. Navigation is a single `mutableStateOf<AppScreen>` in `DailyHealthCoachApp.kt`, switching between screens via a `when` block. `AppScreen` is an enum in `ui/navigation/AppScreen.kt`.

### Domain Layer (`domain/`)

- **Models** — plain Kotlin data classes, no Android dependencies.
- **Repository interfaces** — define the contract; data layer implements them.
- **Use cases** — `GetDashboardSummaryUseCase` is the most complex: it combines 7+ Room Flows using nested `combine()` calls, computes a recovery score and next-day training recommendation on each emission, and persists both back to the database via `onEach`.
- **`RecoveryScoreCalculator`** — pure Kotlin, scores 0–100 from sleep, protein, energy, stress, soreness, and recent workout RPE.
- **`NextDayRecommendationService`** — pure Kotlin, derives one of five recommendation types (REST / WALKING_MOBILITY / ACTIVE_RECOVERY / LOWER_INTENSITY_STRENGTH / STRENGTH) from the recovery score and today's data.

### Data Layer (`data/`)

- **`AppDatabase`** — Room database, version 4, `exportSchema = false`.
- **`AppDatabaseProvider`** — singleton database builder. **All schema migrations live here** (`MIGRATION_1_2` through `MIGRATION_3_4`). When bumping the database version, add the new migration object here and register it in `addMigrations(...)`.
- **`DatabaseSeeder`** — seeds default habits and macro targets on first launch.
- Entities map 1:1 to Room tables; domain models are separate (repositories do the mapping).

### UI Layer (`ui/`)

Each feature has a `Screen.kt`, `ViewModel.kt`, and `UiState.kt`. ViewModels are created with manual factories (e.g., `DashboardViewModelFactory`) so `AppContainer` dependencies can be threaded in without Hilt.

## Project Rules

1. **Explain the plan first** — before editing any file, describe what will be done and why.
2. **List files upfront** — state the exact files that will be changed before making any changes.
3. **Prefer incremental changes** — make small, targeted edits rather than large rewrites.
4. **Build after code changes** — after any code change, verify the build with:
   ```powershell
   $env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug
   ```
5. **No destructive commands without explicit approval** — never run any of the following without being asked:
   - `git reset --hard`
   - `git clean -fd`
   - `del`
   - `rm -rf`
6. **No architecture changes unless requested** — do not alter the layering, DI approach, navigation pattern, or module structure unless explicitly asked.
7. **Stay local-first** — do not introduce networking, remote APIs, cloud sync, or external data sources unless explicitly requested.
8. **Consistent UI styling** — keep styling uniform across Dashboard, Habits, Workout, Nutrition, Body, Recovery, and Profile screens. Don't introduce new color tokens, typography, or layout patterns that diverge from the existing theme.
9. **Pre-commit summary** — before the user commits, list every changed file and a one-line reason for why it changed.

## Bug Triage and Debugging

For bug triage, debugging, issue analysis, or root-cause investigation:

1. **Use memory bank first** — check `.claude/memory_bank/troubleshooting/` and `decisions/` before reading any code.
2. **Use the ponytail skill** — invoke `/ponytail` for structured triage and root-cause analysis before editing.
3. **Understand the symptom** — reproduce or describe the issue precisely before touching code.
4. **Trace the minimal data/UI flow** — identify whether the issue is in UI, ViewModel, repository, database, navigation, or build layer.
5. **Propose smallest safe fix** — state the root cause and fix plan before editing.
6. **List exact files before changing anything** — no edits without upfront file list.
7. **Prefer the smallest safe change** — do not refactor or clean up surrounding code during a bug fix.

## Token and Context Efficiency Rules

- Always query the memory bank before scanning the codebase.
- Prefer /context-query over broad repository searches.
- Do not perform full codebase scans unless explicitly approved.
- Read only the files directly relevant to the current task.
- Before reading many files, explain why they are needed.
- Prefer targeted searches by path, class name, function name, or screen name.
- Do not paste large file contents into the conversation unless requested.
- Summarize findings instead of quoting entire files.
- Keep responses concise unless I ask for deep explanation.
- When planning, provide the smallest safe change first.
- When editing, change the fewest files possible.
- After making code changes, run only the required validation command:
  `$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug`
- Do not run extra tests, formatters, migrations, or broad analysis unless needed or approved.
- Use the memory bank to preserve decisions, architecture notes, troubleshooting notes, and patterns instead of re-discovering them every session.
- After completing a task, update only the relevant memory bank files, not the entire memory bank, unless I explicitly ask for a full refresh.

## Key Constraints

- **No Hilt/Dagger** — adding one requires wiring `AppContainer` into the Hilt component graph or replacing it; don't introduce partial Hilt usage.
- **`exportSchema = false`** — Room schema files are not exported. If you change this, add a schema export directory to `build.gradle.kts`.
- **Steps and sleep on Dashboard are currently hardcoded placeholders** — `steps = 6_400`, `stepGoal = 8_000`, `sleepHours = 6.8` are fallbacks in `GetDashboardSummaryUseCase`; the real values come from `BodyMetricLog` once logged.
- **Dates are `String` (`LocalDate.toString()`)** — the entire codebase passes dates as ISO-8601 strings (e.g., `"2025-06-08"`) rather than `LocalDate` objects across layer boundaries.
