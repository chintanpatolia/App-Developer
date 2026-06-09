# Memory Bank — Daily Health Coach

Local-first Android MVP. Clean architecture (data → domain → ui), Room/SQLite, Kotlin, Jetpack Compose, manual DI via AppContainer. No networking, no Hilt, no Compose Navigation.

**Stack:** Kotlin · Jetpack Compose · Room 2.5+ · Kotlin Coroutines/Flow · Material3 · Single `:app` module

---

## Architecture (8 files)

| File | Description |
|------|-------------|
| `architecture/app-container.md` | Manual DI root — owns all repositories, use cases, domain services |
| `architecture/app-database.md` | Room database v4, 11 entities, singleton builder, migration history |
| `architecture/room-entities.md` | All 11 Room entities, their tables, unique indices, and migration-added fields |
| `architecture/repositories.md` | Repository layer — interface→impl mapping, upsert strategy, newline-string list encoding |
| `architecture/domain-services.md` | RecoveryScoreCalculator (scoring algorithm) + NextDayRecommendationService (decision tree) |
| `architecture/get-dashboard-use-case.md` | GetDashboardSummaryUseCase — nested combine, onEach persistence, hardcoded placeholders |
| `architecture/ui-screens.md` | 5 screens, ViewModel factory pattern, UiState convention, theme color tokens |
| `architecture/navigation.md` | Enum-based navigation — AppScreen, selectedScreen state, BottomNavCapsule |
| `architecture/database-seeder.md` | First-launch seed — 10 habits, 16 exercises, 1 macro target, 1 user profile |

---

## Patterns (12 files)

| File | Description |
|------|-------------|
| `patterns/manual-di.md` | AppContainer wiring, Application→Activity→Composable dependency threading |
| `patterns/repository-pattern.md` | Interface in domain, impl in data, private toDomain() extension functions |
| `patterns/flow-stateflow-ui.md` | Flow → stateIn(WhileSubscribed(5_000)) → StateFlow in ViewModel |
| `patterns/enum-navigation.md` | AppScreen enum + mutableStateOf + when block, no nav library |
| `patterns/viewmodel-factory.md` | ViewModelProvider.Factory subclass, isAssignableFrom check, placement convention |
| `patterns/entity-domain-mapping.md` | Private toDomain() per repo, List<String>↔newline string, upsert field preservation |
| `patterns/combine-flows.md` | Nested combine() for >5 sources — inner aggregates, DashboardInputs/WorkoutInputs |
| `patterns/upsert-pattern.md` | @Upsert with read-first for createdAt/id preservation; habit log insert-ignore+update |
| `patterns/recovery-score-calculation.md` | Baseline 50 + additive deltas + floor rule + reasons.take(4) |
| `patterns/room-migration.md` | Manual Migration objects in AppDatabaseProvider, ALTER TABLE ADD COLUMN DEFAULT |
| `patterns/string-date-convention.md` | ISO-8601 String throughout; LocalDate only for arithmetic inside domain services |
| `patterns/habit-status-enum.md` | storageValue + fromStorageValue(null) fallback; no ordinal use |
| `patterns/dao-design.md` | observe*=Flow, get*=suspend, upsert, count guards; no logic in DAOs |

---

## Claude Tooling

| File | Description |
|------|-------------|
| `decisions/claude-skills-setup.md` | Installed Claude skills — `agent-skills-context-engineering` meta-skill, activation rules, commit recommendation |

---

## Decisions (10 ADRs)

| File | Decision |
|------|---------|
| `decisions/no-di-framework.md` | Manual DI over Hilt/Dagger — explicit wiring, no generated code |
| `decisions/no-compose-navigation.md` | Enum state over NavController — no backstack, no deep links needed for MVP |
| `decisions/local-first.md` | No backend, no cloud sync, no networking |
| `decisions/room-over-realm.md` | Room/SQLite — Jetpack-first, compile-time query validation, native Flow |
| `decisions/export-schema-false.md` | exportSchema=false — no JSON schema files, manual migrations only |
| `decisions/string-dates-vs-localdate.md` | ISO-8601 String at all boundaries — no TypeConverter needed, lexicographically sortable |
| `decisions/manual-migration-strategy.md` | Hand-written Migration objects — all in AppDatabaseProvider, additive ALTER TABLE only |
| `decisions/no-health-connect.md` | No Health Connect — steps/sleep are manual entry; current step count is placeholder |
| `decisions/single-module-structure.md` | Single :app module — sufficient for MVP, layers enforced by package convention |
| `decisions/kotlin-flows-reactivity.md` | Flow + StateFlow everywhere — no LiveData, no RxJava |

---

## Troubleshooting (7 KEDB items)

| File | Problem |
|------|---------|
| `troubleshooting/room-migration-failure.md` | IllegalStateException at startup — version bump without migration, NOT NULL without DEFAULT |
| `troubleshooting/hardcoded-step-sleep-placeholders.md` | Steps always 6,400 / sleep fallback 6.8h — known placeholders, not bugs |
| `troubleshooting/flow-combine-complexity.md` | Dashboard stale data, >5 Flow sources, nested combine structure |
| `troubleshooting/viewmodel-factory-boilerplate.md` | IllegalArgumentException from factory — wrong isAssignableFrom class after copy-paste |
| `troubleshooting/gradle-build-issues.md` | Windows JAVA_HOME, KSP/Room compile errors, daemon issues |
| `troubleshooting/habit-name-matching.md` | Recovery bonus not applied — fragment string must match seeded habit names exactly |

---

## Key Source Locations

| Symbol | File |
|--------|------|
| `AppContainer` | `data/AppContainer.kt` |
| `AppDatabase` | `data/local/AppDatabase.kt` |
| `AppDatabaseProvider` (migrations) | `data/local/AppDatabaseProvider.kt` |
| `DatabaseSeeder` | `data/local/seed/DatabaseSeeder.kt` |
| `GetDashboardSummaryUseCase` | `domain/usecase/GetDashboardSummaryUseCase.kt` |
| `RecoveryScoreCalculator` | `domain/recovery/RecoveryScoreCalculator.kt` |
| `NextDayRecommendationService` | `domain/recommendation/NextDayRecommendationService.kt` |
| `AppScreen` enum | `ui/navigation/AppScreen.kt` |
| `DailyHealthCoachApp` (nav root) | `ui/navigation/DailyHealthCoachApp.kt` |
| `DailyHealthCoachApplication` | `DailyHealthCoachApplication.kt` |
| Hardcoded step/sleep | `domain/usecase/GetDashboardSummaryUseCase.kt:113-115` |

---

## Build Command (Windows)
```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug
```
