# Manual Dependency Injection via AppContainer

## Intent
Provide application-scoped singleton dependencies without a DI framework. Keep wiring explicit and traceable.

## Forces
- No Hilt/Dagger allowed (would require partial graph integration)
- Android `ViewModel` creation requires `ViewModelProvider.Factory`
- All repositories must be singletons sharing the same Room database instance

## Solution
1. `AppDatabaseProvider` (object singleton) builds/returns the Room database.
2. `AppContainer(context)` (plain class, instantiated once in `Application.onCreate()`) wires all `*RepositoryImpl` to their interfaces using constructor injection.
3. `DailyHealthCoachApplication` holds `lateinit var appContainer: AppContainer`.
4. `MainActivity` casts `application as DailyHealthCoachApplication` to retrieve `appContainer`.
5. `DailyHealthCoachApp(appContainer)` passes specific use cases/repos to ViewModel factories in each `when` branch.

## Examples
```kotlin
// AppContainer.kt — wiring
val habitRepository: HabitRepository = HabitRepositoryImpl(
    habitDefinitionDao = database.habitDefinitionDao(),
    dailyHabitLogDao = database.dailyHabitLogDao()
)

// DailyHealthCoachApp.kt — passing to factory
val vm: HabitsViewModel = viewModel(
    factory = HabitsViewModelFactory(
        getTodayHabitsUseCase = appContainer.getTodayHabitsUseCase,
        setHabitStatusForTodayUseCase = appContainer.setHabitStatusForTodayUseCase
    )
)
```

## Anti-Patterns
- Do NOT access `AppContainer` from inside a `ViewModel` or domain class — pass only what is needed.
- Do NOT create multiple `AppContainer` instances — only `Application.onCreate()` creates it.
- Do NOT add partial Hilt annotations (`@HiltViewModel`, `@Inject`) — this breaks the existing wiring without full migration.

## Related
- `decisions/no-di-framework.md`
- `patterns/viewmodel-factory.md`
- `architecture/app-container.md`
