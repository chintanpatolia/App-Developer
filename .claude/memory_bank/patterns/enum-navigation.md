# Enum-Based Navigation Pattern

## Intent
Use a Kotlin enum as the complete navigation model. No navigation graph, no back stack, no argument passing.

## Forces
- No Compose Navigation library (`decisions/no-compose-navigation.md`)
- All screens are top-level (no nested/detail flows in current MVP)
- Bottom nav bar must iterate available screens automatically

## Solution
```kotlin
// AppScreen.kt
enum class AppScreen(val label: String) {
    DASHBOARD("Dashboard"),
    HABITS("Habits"),
    WORKOUT("Workout"),
    NUTRITION("Nutrition"),
    BODY("Body")
}

// DailyHealthCoachApp.kt
var selectedScreen by remember { mutableStateOf(AppScreen.DASHBOARD) }
// ...
when (selectedScreen) {
    AppScreen.DASHBOARD -> DashboardRoute(...)
    AppScreen.HABITS    -> HabitsRoute(...)
    // ...
}
// Bottom nav iterates AppScreen.entries automatically
AppScreen.entries.forEach { screen -> ... }
```

## Anti-Patterns
- Do NOT pass complex arguments between screens via the enum. If data must be shared, lift it to `AppContainer` or a shared repository.
- Do NOT add `@Serializable` or route strings to `AppScreen` — that pattern belongs to Compose Navigation.
- Do NOT handle back navigation with this pattern — there is no stack to pop.

## Related
- `decisions/no-compose-navigation.md`
- `architecture/navigation.md`
