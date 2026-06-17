# Supabase Integration — Implementation Log

## Phase 1 — Schema (Complete)
Supabase SQL executed manually in the Supabase SQL Editor. No Android files changed.

- 12 tables created mirroring all Room entities
- 1 sync_metadata table added
- RLS policies active on all 13 tables
- Email Auth configured in Supabase dashboard

## Phase 2 — Android SDK + Auth Foundation (In Progress)

### Files Modified
| File | Change |
|---|---|
| `app/build.gradle.kts` | Added Supabase BOM, gotrue-kt, postgrest-kt, ktor-client-okhttp deps; added `buildConfig = true`; added `buildConfigField` entries for URL/key/enabled flag |
| `data/AppContainer.kt` | Added `authRepository: AuthRepository?` wired behind feature flag |

### Files Created
| File | Purpose |
|---|---|
| `local.properties` | Holds `SUPABASE_URL`, `SUPABASE_ANON_KEY`, `SUPABASE_ENABLED` — gitignored |
| `data/remote/SupabaseFeatureFlags.kt` | Runtime gate: all Supabase code checks this before executing |
| `data/remote/SupabaseClientProvider.kt` | Lazy singleton Supabase client |
| `domain/repository/AuthRepository.kt` | Interface: signUp, signIn, signOut, currentUserId, isAuthenticated |
| `data/repository/AuthRepositoryImpl.kt` | Supabase Auth implementation of AuthRepository |

### Dependencies Added
```kotlin
// app/build.gradle.kts
implementation(platform("io.github.jan-tennermann:supabase-bom:2.6.1"))
implementation("io.github.jan-tennermann:gotrue-kt")
implementation("io.github.jan-tennermann:postgrest-kt")
implementation("io.ktor:ktor-client-okhttp:2.3.12")
```

### Environment Variables Required
Add to `local.properties` (file is gitignored — never commit these values):
```
SUPABASE_URL=https://your-project-ref.supabase.co
SUPABASE_ANON_KEY=your-anon-key-here
SUPABASE_ENABLED=false
```
Set `SUPABASE_ENABLED=true` only when keys are configured and ready to test.

### Feature Flag Behaviour
`SupabaseFeatureFlags.isEnabled` is `true` only when ALL three conditions hold:
1. `SUPABASE_ENABLED=true` in `local.properties`
2. `SUPABASE_URL` is non-blank
3. `SUPABASE_ANON_KEY` is non-blank

If any condition is false, `authRepository` in `AppContainer` is `null` and no Supabase
code executes. The app runs in guest/offline mode exactly as before.

### Guest Mode Guarantee
- No login screen is shown unless Supabase is enabled
- All Room repositories are unchanged
- App is fully functional offline with no Supabase dependency at runtime

### Rollback Steps (Phase 2)
1. Delete `local.properties`
2. Revert `app/build.gradle.kts` (remove 4 dependency lines + 3 buildConfigField lines + `buildConfig = true`)
3. Revert `data/AppContainer.kt` (remove `authRepository` property and 3 imports)
4. Delete `data/remote/SupabaseFeatureFlags.kt`
5. Delete `data/remote/SupabaseClientProvider.kt`
6. Delete `domain/repository/AuthRepository.kt`
7. Delete `data/repository/AuthRepositoryImpl.kt`
8. Clean build: `.\gradlew.bat clean assembleDebug`

---

## Phase 3 — Auth UI (Planned)
- Sign in / sign up screen (optional, shown only when Supabase enabled)
- Session persistence via Supabase SDK token storage
- Auth state exposed to Profile screen

## Phase 4 — SyncMetadataEntity + WorkManager Sync Engine (Planned)
- `SyncMetadataEntity` added to Room (DB migration required)
- `InitialUploadWorker` — uploads all existing Room data on first login
- `IncrementalSyncWorker` — pushes rows where `updatedAt > lastPushedAt`
- Table upload order respects soft FK dependencies

## Phase 5 — Soft Delete + Full Bidirectional Sync (Planned)
- `deleted_at` column added to all 12 Supabase tables
- `isDeleted` + `deletedAt` fields added to Room entities (migration required)
- Pull delta sync on app foreground
- 90-day purge policy via Supabase scheduled function

## Conflict Resolution Reference
| Tables | Strategy |
|---|---|
| user_profiles, body_metric_logs, workouts, food_entries, macro_targets, daily_habit_logs, recovery_scores, daily_recommendations | Last `updated_at` wins |
| workout_exercises, exercises, habit_definitions, recovery_activity_logs | Server wins |

## Multi-Device Key
Every Supabase row carries `(user_id, device_id, local_id)`.
`device_id` = `Settings.Secure.ANDROID_ID` — stable per physical device.
`local_id` = Room auto-generated PK — never modified.
`UNIQUE(user_id, device_id, local_id)` enables safe upserts from any device.
