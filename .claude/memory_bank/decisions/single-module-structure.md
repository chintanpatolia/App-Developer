# ADR: Single Module Structure

## Context
Android projects can be split into multiple Gradle modules (`:app`, `:domain`, `:data`, `:ui-*`) for build isolation, faster incremental builds, and enforced layer separation. The alternative is a single `:app` module with package-level separation.

## Options
1. **Multi-module** — Separate Gradle modules per layer. Enforces layer boundaries at the compiler level; faster incremental builds at scale.
2. **Single module** — All code in `:app`. Layers enforced by package structure and team convention.

## Decision
Single `:app` module. Layers are `com.dailyhealthcoach.data`, `com.dailyhealthcoach.domain`, `com.dailyhealthcoach.ui`.

## Consequences
**Positive:**
- Simpler Gradle configuration.
- No inter-module dependency management.
- Faster initial setup and prototyping.
- Sufficient for MVP scale (< 100 Kotlin files).

**Negative:**
- No compile-time enforcement of layer boundaries — a ViewModel could technically import a DAO directly (team convention prevents this).
- Build times will not benefit from module parallelism as the project grows.

**Migration path if needed:** Extract `domain` module first (pure Kotlin, no Android deps), then `data`, keeping `:app` as the thin orchestration layer.

## Related
- `architecture/app-container.md`
- `decisions/no-di-framework.md`
