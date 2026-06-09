# DatabaseSeeder

## Responsibility
Seeds default data on first app launch. Runs once at startup; guards itself with `count > 0` checks so re-runs are no-ops.

## Interfaces & Dependencies
`data/local/seed/DatabaseSeeder.kt`

- **Constructed by:** `DailyHealthCoachApplication.onCreate()`, receives `AppDatabase` directly.
- **Called on:** `applicationScope` (SupervisorJob + Dispatchers.IO) via `launch { DatabaseSeeder(database).seedIfNeeded() }`.
- **DAOs used:** `userProfileDao`, `habitDefinitionDao`, `exerciseDao`, `macroTargetDao`

## Data Flow
```
DailyHealthCoachApplication.onCreate()
  → DatabaseSeeder(database).seedIfNeeded()
      ├── seedUserProfile()         — upserts row id=1 (always)
      ├── seedDefaultHabits()       — skips if countHabits() > 0
      ├── seedDefaultExercises()    — skips if countExercises() > 0
      └── seedDefaultMacroTarget()  — skips if countTargets() > 0
```

## Seeded Data
**10 default habits** (frequencyType: DAILY / WEEKLY_TARGET / INTERVAL / AS_NEEDED):
Morning sunlight, Protein goal, Strength training (4x/week target), Steps, Avoid late eating, Sleep, Bloodwork reminder (INTERVAL), Breathwork (AS_NEEDED), Whole foods, Fermented foods (3x/week target)

**16 default exercises** with muscleGroup and equipmentType:
Chest (3), Back (3), Legs (3), Shoulders (2), Arms (2), Core (2), Cardio (1)

**1 default macro target:** proteinMin=170g, proteinMax=200g

## Operational Notes
- `seedUserProfile()` always upserts (not guarded) — ensures a row with `id = 1` exists every launch. Safe because it's an upsert.
- Habit seeding is idempotent — if habits already exist, the whole `seedDefaultHabits()` function returns early.
- `INTERVAL` habits (Bloodwork reminder) are excluded from today's trackable list by `HabitFrequency.isTodayTrackable()`.
- Recovery score uses habit name substring matching (`"whole"`, `"sunlight"`, `"breath"`, `"late eating"`) — if default habit names change, recovery logic breaks. See `troubleshooting/habit-name-matching.md`.

## Related
- `architecture/app-container.md`
- `architecture/room-entities.md`
- `decisions/local-first.md`
