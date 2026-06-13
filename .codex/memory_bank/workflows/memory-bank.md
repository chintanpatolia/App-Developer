# Memory Bank Workflow

1. **Query memory first** — check relevant `decisions/`, `patterns/`, `architecture/` before reading source files.
2. **Add decisions** to `decisions/` — one file per ADR or key trade-off.
3. **Add conventions** to `patterns/` — naming, layer rules, repeated implementation patterns.
4. **Add structure notes** to `architecture/` — module layout, DI wiring, navigation, DB schema notes.
5. **Add fixes** to `troubleshooting/` — symptom, root cause, resolution; one file per issue.
6. **Avoid full refresh** unless the user explicitly requests it — update only what changed.

## Staleness

If a memory file references a function, file, or flag that may have changed, verify it exists before acting on it (`Grep` or `Glob`). Trust the live codebase over the memory file if they conflict; update the stale entry.
