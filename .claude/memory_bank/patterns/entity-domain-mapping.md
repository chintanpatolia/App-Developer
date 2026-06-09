# Entity ↔ Domain Model Mapping Pattern

## Intent
Keep Room annotations out of domain models. Map between layers at the repository boundary using private extension functions.

## Forces
- Room `@Entity` classes must have Android/Room imports — domain layer must stay pure Kotlin.
- Mapping code should live close to where the conversion happens, not in a shared `Mapper` class.
- `List<String>` in domain models cannot be stored directly in Room (no `TypeConverter` — instead use newline-joined `String`).

## Solution
```kotlin
// In *RepositoryImpl.kt — private extension at file bottom
private fun HabitDefinitionEntity.toDomain(): HabitDefinition {
    return HabitDefinition(
        id = id,
        name = name,
        description = description,
        frequencyType = frequencyType,
        targetPerWeek = targetPerWeek,
        sortOrder = sortOrder
    )
}
```

### List<String> ↔ newline String
```kotlin
// Entity field: val reasonBullets: String? = null
// Domain field: val reasonBullets: List<String>

// Saving (Impl → Entity):
reasonBullets = recommendation.reasonBullets.joinToString("\n")

// Reading (Entity → Domain):
reasonBullets = reasonBullets?.lines()?.filter { it.isNotBlank() }.orEmpty()
```
Used in `DailyRecommendationRepositoryImpl` and `RecoveryRepositoryImpl`.

### Upsert with preserved fields
When saving, fetch the existing row first to preserve `id` and `createdAt`:
```kotlin
val existing = bodyMetricLogDao.getForDate(input.date)
bodyMetricLogDao.upsert(BodyMetricLogEntity(
    id = existing?.id ?: 0,
    createdAt = existing?.createdAt ?: now,
    updatedAt = now,
    ...
))
```

## Anti-Patterns
- Do NOT add `TypeConverter` for `List<String>` — the current newline string approach is intentional for these simple cases.
- Do NOT map in the DAO or ViewModel — mapping belongs in the repository impl.
- Do NOT make `toDomain()` a public function — it must be `private` to avoid accidental use outside the repository.

## Related
- `patterns/repository-pattern.md`
- `patterns/upsert-pattern.md`
- `architecture/repositories.md`
