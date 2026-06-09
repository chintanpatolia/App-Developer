# ADR: No Compose Navigation Library

## Context
Jetpack Compose Navigation (`androidx.navigation.compose`) provides type-safe routes, a backstack, deep links, and animated transitions. The app has 5 top-level screens with no detail flows or nested navigation.

## Options
1. **Compose Navigation** — Route strings or typed routes, NavController, NavHost, backstack.
2. **Enum-based state** — Single `mutableStateOf<AppScreen>` in root composable, `when` block for switching.

## Decision
Enum-based state (`AppScreen` enum + `remember { mutableStateOf(...) }` in `DailyHealthCoachApp`).

## Consequences
**Positive:**
- Zero library dependency for navigation.
- No route string management or argument serialization.
- Trivially readable: adding a screen = add enum entry + `when` branch.

**Negative:**
- No backstack — pressing a nav item always resets to that screen's initial state.
- No deep links from notifications or external intents.
- Argument passing between screens requires shared state (repository or ViewModel lift).
- Screen-to-screen transitions (animations) require custom implementation.

**Constraint:** All 5 screens are currently top-level (DASHBOARD, HABITS, WORKOUT, NUTRITION, BODY). If a detail screen is needed (e.g., workout detail), this decision must be revisited.

## Related
- `patterns/enum-navigation.md`
- `architecture/navigation.md`
