# ADR: No DI Framework (No Hilt/Dagger)

## Context
Android DI frameworks (Hilt, Dagger 2, Koin) require annotation processing, generated code, and specific component hierarchies. The project is an MVP with a small number of dependencies that change infrequently.

## Options
1. **Hilt** — Android-idiomatic, integrates with ViewModel/Application lifecycle. Adds annotation processing, kapt/ksp build overhead, module/component graph setup.
2. **Koin** — Lightweight DSL-based DI. Still requires a Service Locator pattern at runtime with potential for runtime crashes.
3. **Manual DI via AppContainer** — Explicit constructor injection, no frameworks, no generated code.

## Decision
Manual DI via `AppContainer`. All wiring is explicit in `data/AppContainer.kt`.

## Consequences
**Positive:**
- Zero build-time overhead from annotation processing.
- Wiring is entirely readable in one file — no "magic" injection.
- Adding a dependency means adding one line to `AppContainer` and one parameter to a factory.

**Negative:**
- Every new ViewModel requires a `ViewModelProvider.Factory` subclass (boilerplate).
- No scope management — all repositories are effectively application-scoped singletons.
- If the app grows beyond ~20 screens, `AppContainer` will become verbose.

**Migration path if needed:** Introduce Hilt by creating an `@Module` that delegates to `AppContainer` fields, avoiding a full rewrite.

## Related
- `patterns/manual-di.md`
- `patterns/viewmodel-factory.md`
- `architecture/app-container.md`
- `troubleshooting/viewmodel-factory-boilerplate.md`
