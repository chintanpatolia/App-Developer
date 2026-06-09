# ViewModel Factory Boilerplate

## Symptoms
- `IllegalArgumentException: Unknown ViewModel class: com.dailyhealthcoach.ui.X.XViewModel` — factory returns wrong type.
- `ClassCastException` when `viewModel()` is called — factory cast is wrong.
- New ViewModel does not receive its dependencies.

## Diagnostics
Every ViewModel needs a `ViewModelProvider.Factory`. The most common mistake is copy-pasting a factory and forgetting to update the class check or the constructor call:

```kotlin
// WRONG — still checks for DashboardViewModel after copy-paste:
class HabitsViewModelFactory(...) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {  // BUG
            return HabitsViewModel(...) as T
        }
```

```kotlin
// CORRECT:
class HabitsViewModelFactory(
    private val getTodayHabitsUseCase: GetTodayHabitsUseCase,
    private val setHabitStatusForTodayUseCase: SetHabitStatusForTodayUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsViewModel::class.java)) {  // must match
            return HabitsViewModel(getTodayHabitsUseCase, setHabitStatusForTodayUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
```

## Fix — Adding a new ViewModel
1. Create `XViewModel(dep1: Dep1, dep2: Dep2) : ViewModel()`.
2. Add `class XViewModelFactory(private val dep1: Dep1, ...) : ViewModelProvider.Factory` at the bottom of the same file.
3. Check: `modelClass.isAssignableFrom(XViewModel::class.java)` (not some other ViewModel class).
4. Expose the dependency on `AppContainer` if it doesn't exist.
5. In `DailyHealthCoachApp.kt`, call `viewModel(factory = XViewModelFactory(appContainer.dep1))`.

## Affected Versions
All versions — inherent to manual DI pattern.

## Prevention
- Always place the factory at the bottom of the ViewModel file.
- The `throw IllegalArgumentException` line is mandatory — without it, wrong-type requests silently return null.

## Related
- `patterns/viewmodel-factory.md`
- `patterns/manual-di.md`
- `decisions/no-di-framework.md`
