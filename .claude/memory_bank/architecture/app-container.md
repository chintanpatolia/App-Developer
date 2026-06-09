# AppContainer — Manual DI Root

## Responsibility
Single object that owns all repository instances, domain services, and use cases. Passed from `Application` to `MainActivity` to `DailyHealthCoachApp`, then threaded into individual ViewModel factories at each composition site.

## Interfaces & Dependencies
- **Constructed by:** `DailyHealthCoachApplication.onCreate()`
- **Consumed by:** `DailyHealthCoachApp(appContainer)` — one per screen switch case
- **Owns (repositories):** `HabitRepository`, `MacroTargetRepository`, `UserProfileRepository`, `ExerciseRepository`, `WorkoutRepository`, `BodyMetricRepository`, `NutritionRepository`, `RecoveryRepository`, `DailyRecommendationRepository`
- **Owns (use cases):** `GetDashboardSummaryUseCase`, `GetTodayHabitsUseCase`, `SetHabitStatusForTodayUseCase`
- **Source:** `data/AppContainer.kt:29`

## Data Flow
```
Application.onCreate()
  → AppDatabaseProvider.getDatabase(context)   // singleton db
  → AppContainer(context)                       // wires all impls to interfaces
  → DatabaseSeeder(database).seedIfNeeded()     // async on IO dispatcher
```

In `DailyHealthCoachApp`, the factory receives only the use case/repo it needs:
```kotlin
DashboardViewModelFactory(getDashboardSummaryUseCase = appContainer.getDashboardSummaryUseCase)
```

## Operational Notes
- All repository fields are `val` (effectively singletons for the app lifetime).
- Domain services (`RecoveryScoreCalculator`, `NextDayRecommendationService`) are instantiated inline inside `getDashboardSummaryUseCase`; they are stateless pure-Kotlin classes.
- To add a new feature: add a DAO accessor to `AppDatabase`, create the `*RepositoryImpl`, expose a `val` on `AppContainer`, pass it to a new ViewModel factory.

## Related
- `architecture/app-database.md` — what `AppDatabaseProvider.getDatabase()` returns
- `patterns/manual-di.md` — full pattern description
- `patterns/viewmodel-factory.md` — how factories consume AppContainer fields
