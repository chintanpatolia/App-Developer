# UI Screens Architecture

## Responsibility
Each feature screen follows a consistent three-file pattern: `Screen.kt` (Compose UI), `ViewModel.kt` (state + logic), `UiState.kt` (plain data class). ViewModels are created via manual factories at composition sites in `DailyHealthCoachApp.kt`.

## Screen Inventory

| Screen | AppScreen enum | ViewModel | Route composable |
|--------|---------------|-----------|-----------------|
| Dashboard | `DASHBOARD` | `DashboardViewModel` | `DashboardRoute` |
| Habits | `HABITS` | `HabitsViewModel` | `HabitsRoute` |
| Workout | `WORKOUT` | `WorkoutViewModel` | `PremiumWorkoutRoute` |
| Nutrition | `NUTRITION` | `NutritionViewModel` | `NutritionRoute` |
| Body | `BODY` | `BodyViewModel` | `BodyRoute` |

## ViewModel Construction Pattern
```kotlin
// In DailyHealthCoachApp.kt when block:
AppScreen.DASHBOARD -> {
    val vm: DashboardViewModel = viewModel(
        factory = DashboardViewModelFactory(appContainer.getDashboardSummaryUseCase)
    )
    DashboardRoute(viewModel = vm, modifier = ...)
}
```
Each factory is a `ViewModelProvider.Factory` subclass defined at the bottom of the ViewModel file.

## UiState Pattern
All UiStates are plain `data class` with defaults for loading state:
```kotlin
data class DashboardUiState(
    val isLoading: Boolean = true,
    ...
)
```
ViewModels expose a single `StateFlow<*UiState>` collected via `stateIn(WhileSubscribed(5_000))`.

## Theme — Color Tokens (`ui/theme/Theme.kt`)
```
AppBackground  = 0xFF1E214B (deep navy)
MainCard       = 0xFF38388C
SecondaryCard  = 0xFF4B4BB2
AccentBlue     = 0xFF168BFF
CyanAccent     = 0xFF19C6D3
PrimaryText    = 0xFFF5F7FF
MutedText      = 0xFFB7B9D9
PositiveAccent = 0xFF36D987
WarningAccent  = 0xFFFFB15C
```
Dark theme only. `DailyHealthCoachTheme` wraps Material3 `darkColorScheme`.

## Navigation
Screen switching is a `var selectedScreen by remember { mutableStateOf(AppScreen.DASHBOARD) }` in `DailyHealthCoachApp.kt:59`. No Compose Navigation. See `decisions/no-compose-navigation.md`.

## Workout & Nutrition — MainShellContent
WORKOUT and NUTRITION render inside `MainShellContent` (shared scaffold with `ScreenHeader` + vertical scroll). DASHBOARD, HABITS, BODY have their own full-screen routes.

## Related
- `architecture/navigation.md`
- `patterns/viewmodel-factory.md`
- `patterns/flow-stateflow-ui.md`
- `decisions/no-compose-navigation.md`
