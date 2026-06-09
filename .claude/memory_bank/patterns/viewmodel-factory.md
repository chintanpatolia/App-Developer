# ViewModel Factory Pattern

## Intent
Construct ViewModels with constructor parameters (use cases/repositories) without Hilt, using `ViewModelProvider.Factory`.

## Forces
- Jetpack `viewModel()` composable uses a factory; without one, only zero-arg ViewModels work.
- No Hilt `@HiltViewModel` allowed.
- Factory must be defined alongside its ViewModel to keep wiring local.

## Solution
```kotlin
class DashboardViewModelFactory(
    private val getDashboardSummaryUseCase: GetDashboardSummaryUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(getDashboardSummaryUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

// Composition site (DailyHealthCoachApp.kt):
val vm: DashboardViewModel = viewModel(
    factory = DashboardViewModelFactory(appContainer.getDashboardSummaryUseCase)
)
```

## Factories in this project
| Factory | ViewModel | Dependencies |
|---------|-----------|-------------|
| `DashboardViewModelFactory` | `DashboardViewModel` | `GetDashboardSummaryUseCase` |
| `HabitsViewModelFactory` | `HabitsViewModel` | `GetTodayHabitsUseCase`, `SetHabitStatusForTodayUseCase` |
| `BodyViewModelFactory` | `BodyViewModel` | `BodyMetricRepository`, `UserProfileRepository` |
| `WorkoutViewModelFactory` | `WorkoutViewModel` | `ExerciseRepository`, `WorkoutRepository` |
| `NutritionViewModelFactory` | `NutritionViewModel` | `NutritionRepository`, `MacroTargetRepository` |

## Anti-Patterns
- Do NOT forget the `isAssignableFrom` check — it throws a meaningful error instead of a cast failure.
- Do NOT put factory in a separate file — keep it at the bottom of the ViewModel file.
- Do NOT pass `AppContainer` itself to the factory — pass only the specific dependency needed.

## Related
- `patterns/manual-di.md`
- `architecture/ui-screens.md`
