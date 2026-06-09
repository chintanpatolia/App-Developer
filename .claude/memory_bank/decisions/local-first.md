# ADR: Local-First, No Backend

## Context
Health data is personal and sensitive. Cloud sync, remote APIs, and external data sources add authentication, network error handling, privacy concerns, and backend infrastructure costs.

## Options
1. **Cloud-synced** — Firebase/Supabase backend, remote auth, REST/GraphQL APIs.
2. **Local-first** — All data in Room (SQLite) on device, no network calls.
3. **Hybrid** — Local with optional sync (offline-first).

## Decision
Fully local-first. No networking, no remote APIs, no cloud sync. All data is stored in `daily_health_coach.db` on the device.

## Constraints Enforced
- No `okhttp`, `retrofit`, `ktor`, or `apollo` dependencies.
- No Health Connect integration.
- No barcode scanning (no camera permission).
- No `INTERNET` permission in `AndroidManifest.xml`.

## Consequences
**Positive:**
- No auth, no network error handling, no rate limits.
- Full privacy by default — data never leaves device.
- Works offline, no connectivity required.
- Simple architecture — no remote data sources or sync queues.

**Negative:**
- No cross-device sync.
- Data is lost if device is cleared without a backup.
- Cannot integrate wearable/sensor data automatically.

## Related
- `decisions/no-health-connect.md`
- `decisions/single-module-structure.md`
