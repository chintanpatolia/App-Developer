# Flow → StateFlow UI Pattern

## Intent
Convert a cold domain `Flow` into a hot `StateFlow<UiState>` that survives configuration changes without restarting the upstream query.

## Forces
- Room DAOs emit `Flow`; UI needs `StateFlow` for Compose `collectAsState()`.
- ViewModel must survive config changes; Flow collection must not restart immediately on rotation.
- Initial loading state must be shown until first emission arrives.

## Solution
```kotlin
val uiState: StateFlow<HabitsUiState> = getTodayHabitsUseCase(today)
    .map { habits -> habits.toUiState() }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HabitsUiState()   // isLoading = true by default
    )
```
- `WhileSubscribed(5_000)` keeps the upstream active for 5 seconds after last subscriber — avoids restart on screen rotation.
- `initialValue` is always an empty/loading state.

## Examples
Used in all ViewModels:
- `DashboardViewModel.kt:17` — single `stateIn` on use case Flow
- `HabitsViewModel.kt:23` — `stateIn` on use case Flow
- `BodyViewModel.kt:27` — `combine()` of 4 Flows then `stateIn`

## Anti-Patterns
- Do NOT use `SharingStarted.Eagerly` — wastes resources when no subscriber.
- Do NOT collect in a `LaunchedEffect` inside a composable when the ViewModel already exposes `StateFlow`.
- Do NOT forget `initialValue` — causes `NPE` if UI accesses state before first emission.

## Related
- `patterns/combine-flows.md`
- `patterns/viewmodel-factory.md`
