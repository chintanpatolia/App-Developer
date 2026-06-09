# ADR: Kotlin Flows for Reactivity (No LiveData, No RxJava)

## Context
Android has historically used `LiveData` for lifecycle-aware reactive data. Kotlin coroutines and Flow offer a more idiomatic, testable, and powerful alternative. RxJava is a third-party reactive library with a larger API surface.

## Options
1. **LiveData** — Lifecycle-aware, simple, but limited operators. Android-specific.
2. **RxJava** — Rich operator set, but heavy dependency and steep learning curve.
3. **Kotlin Flow + StateFlow** — Coroutines-native, testable, rich operators, Compose-native.

## Decision
Kotlin Flow everywhere. Room DAOs return `Flow<T>`. ViewModels expose `StateFlow<UiState>`. No `LiveData`, no `RxJava`.

## Implementation
- Room DAOs: `fun observe*(): Flow<T>`
- Repositories: `Flow.map { entity.toDomain() }`
- Use cases: `combine()` for multi-source aggregation
- ViewModels: `Flow.stateIn(viewModelScope, WhileSubscribed(5_000), initialValue)`
- Compose: `viewModel.uiState.collectAsState()`

## Consequences
**Positive:**
- Consistent with Compose (no `observeAsState` adapter needed).
- First-class `combine`, `map`, `onEach`, `filter` operators.
- Testable with `turbine` or `runTest` + `toList()`.
- No additional dependencies.

**Negative:**
- `StateFlow` requires initial value — must define sensible empty/loading states.
- `combine` with > 5 sources requires nesting.
- More verbose than `LiveData` for simple single-source cases.

## Related
- `patterns/flow-stateflow-ui.md`
- `patterns/combine-flows.md`
