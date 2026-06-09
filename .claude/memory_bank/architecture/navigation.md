# Navigation Architecture

## Responsibility
App navigation is a single `mutableStateOf<AppScreen>` — no Compose Navigation library, no backstack, no deep links. Screen switching is a `when` block in `DailyHealthCoachApp.kt`.

## Interfaces & Dependencies
- **`AppScreen`** — `ui/navigation/AppScreen.kt:3` — enum with 5 entries: DASHBOARD, HABITS, WORKOUT, NUTRITION, BODY. Each entry has a `label: String`.
- **`DailyHealthCoachApp`** — `ui/navigation/DailyHealthCoachApp.kt:58` — composition root. Holds `selectedScreen` state and renders the matching screen.
- **`BottomNavCapsule`** — `ui/navigation/DailyHealthCoachApp.kt:171` — pill-shaped bottom nav. Iterates `AppScreen.entries`, calls `onScreenSelected(screen)` on tap.

## Data Flow
```
User taps nav item
  → BottomNavCapsule.onScreenSelected(screen)
  → DailyHealthCoachApp.selectedScreen = screen   (recomposition)
  → when(selectedScreen) { ... }                   (correct screen renders)
```

## Adding a New Screen
1. Add entry to `AppScreen` enum (`ui/navigation/AppScreen.kt`).
2. Add `when` branch in `DailyHealthCoachApp` to instantiate ViewModel + render Route.
3. No navigation graph, no routes, no arguments needed.

## Operational Notes
- No backstack — pressing nav item always navigates to that screen's fresh state. Screen state is preserved only while the ViewModels are alive (i.e., while that screen is composed; `WhileSubscribed(5_000)` provides a 5-second grace period on config change).
- `BottomNavCapsule` is aligned to `Alignment.BottomCenter` in a `Box` overlay — screens must add `padding(bottom = 112.dp)` to avoid being obscured.
- `AppScreen.entries` drives the nav bar automatically; adding a new enum entry will auto-appear in nav.

## Related
- `decisions/no-compose-navigation.md`
- `architecture/ui-screens.md`
