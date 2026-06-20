# Phase 35 — Recipe Library Expansion Architecture
**Date:** 2026-06-20
**Status:** Approved — Steps 1–6 in scope. Steps 7–9 deferred until migration validated.

---

## 1. Context

The existing recipe system has 92 recipes in a single 2,640-line `RecipeCatalog.kt`. At 200+ recipes this becomes unmanageable. The diet filter (`matchesDiet`) has no cases for Omnivore, Chicken/Fish, Poultry, Meat/Poultry, or Flexitarian — all fall through to `else -> true`. Restriction filtering is heuristic keyword scanning on ingredient strings, flagged as a TODO. The `Recipe` model has no `insulinResistanceScore`, no `glucoseImpactScore`, no explicit `restrictions` field, and no `womensHealthScore`.

This design resolves all of the above before any new recipes are written.

---

## 2. File Structure

`RecipeCatalog.kt` becomes a thin aggregator. All recipe data moves into a `catalog/` subfolder split strictly by meal type.

```
ui/recipes/
  catalog/
    BreakfastRecipes.kt      # ~40 recipes (V1 min)
    LunchRecipes.kt          # ~50 recipes (V1 min)
    DinnerRecipes.kt         # ~50 recipes (V1 min)
    SnackRecipes.kt          # ~35 recipes (V1 min)
    BoosterRecipes.kt        # ~25 recipes (V1 min)
  Recipe.kt                  # updated model
  RecipeCatalog.kt           # thin aggregator only
  MealPlanEngine.kt          # updated filters + ranking
  RecipesScreen.kt           # unchanged
  RecipesViewModel.kt        # unchanged
  RecipesUiState.kt          # unchanged
  RecipeSuggestionEngine.kt  # unchanged
```

**Aggregator (replaces entire RecipeCatalog.kt body):**
```kotlin
object RecipeCatalog {
    val ALL: List<Recipe> =
        BreakfastRecipes.ALL + LunchRecipes.ALL + DinnerRecipes.ALL +
        SnackRecipes.ALL + BoosterRecipes.ALL
}
```

Each catalog file is a Kotlin `object` with a single `val ALL: List<Recipe>`. Split is by meal type only — not by protein source, diet style, or health program. Those are metadata fields on each recipe.

---

## 3. Recipe Model

### 3a. Updated `Recipe.kt`

```kotlin
data class Recipe(
    val id: String,
    val name: String,
    val description: String = "",

    // "Breakfast" | "Lunch" | "Dinner" | "Snack" | "Protein Booster"
    val mealType: String,

    // Diet style tags: "Vegetarian" | "Vegan" | "Pescatarian" | "Mediterranean"
    //                  | "Omnivore" | "Flexitarian"
    val tags: List<String>,

    // Primary protein: "Chicken" | "Turkey" | "Beef" | "Fish" | "Seafood" |
    //   "Tofu" | "Paneer" | "Tempeh" | "Eggs" | "Lentils" |
    //   "Greek Yogurt" | "Cottage Cheese" | "Soy Chunks" | "" (plant-mixed / none)
    val proteinSource: String = "",

    // Explicit restriction tags — replaces heuristic ingredient scanning
    // "Dairy Free" | "Gluten Free" | "Nut Free" | "Soy Free" | "Egg Free"
    val restrictions: List<String> = emptyList(),

    // Program collections: "Metabolic Reset" | "Anti-Inflammatory" | "Heart Healthy"
    //                     | "Fat Loss" | "Muscle Gain"
    val collection: List<String> = emptyList(),

    val ingredients: List<String>,
    val calories: Int,
    val proteinGrams: Double,
    val carbGrams: Double,
    val fatGrams: Double,
    val fiberGrams: Double?,
    val prepMinutes: Int,
    val cookMinutes: Int = 0,
    val instructions: List<String>,
    val storageNotes: String? = null,
    val mealPrepNotes: String? = null,

    // ── Health program scores (1–10, higher = more beneficial) ───────────────
    val metabolicResetScore: Int = 0,
    val antiInflammatoryScore: Int = 0,
    val insulinResistanceScore: Int = 0,   // NEW
    val womensHealthScore: Int = 0,         // NEW — bone density, iron, hormone-supportive

    // ── Glucose (1 = minimal spike, 10 = high spike — lower is better) ───────
    val glucoseImpactScore: Int = 0,        // NEW — replaces isGlucoseConscious

    // isGlucoseConscious REMOVED — use glucoseImpactScore <= 3 instead
)
```

### 3b. Field rules

| Field | Rule |
|---|---|
| `proteinSource` | Must be set for any Omnivore/Chicken/Fish/Poultry/Meat recipe. Empty only for purely plant-based or mixed. |
| `restrictions` | Fully declared on every recipe. No defaults left empty when restrictions are known. |
| `glucoseImpactScore` | Must be set (not left at 0) if recipe contains refined carbs, high-GI fruit, or added sugar. |
| `insulinResistanceScore` | Must be set for any recipe in Metabolic Reset or Insulin Resistance collections. |
| `womensHealthScore` | Set for recipes rich in calcium, iron, magnesium, phytoestrogens, or folate. |

### 3c. Protein targets by meal slot

Every recipe must land within ±5g of its slot minimum:

| Slot | Minimum protein |
|---|---|
| Breakfast | ≥ 20g |
| Lunch | ≥ 25g |
| Dinner | ≥ 25g |
| Snack | ≥ 10g |
| Protein Booster | ≥ 25g |

---

## 4. MealPlanEngine Changes

### 4a. `violatesRestriction()` — replace heuristic with explicit lookup

```kotlin
// BEFORE: 60-line keyword scan of ingredient strings
// AFTER:
private fun violatesRestriction(recipe: Recipe, restriction: String): Boolean =
    recipe.restrictions.any { it.equals(restriction.trim(), ignoreCase = true) }
```

### 4b. `matchesDiet()` — add 5 missing profile preference cases

```kotlin
private fun matchesDiet(recipe: Recipe, pref: String): Boolean {
    val tags = recipe.tags.map { it.lowercase() }
    val src  = recipe.proteinSource.lowercase()
    return when (pref.lowercase().trim()) {
        "vegan"                                           -> "vegan" in tags
        "vegetarian", "high protein vegetarian",
        "lacto vegetarian", "ovo vegetarian"             -> "vegetarian" in tags || "vegan" in tags
        "pescatarian"                                     -> "vegetarian" in tags || "vegan" in tags
                                                             || "pescatarian" in tags
        "mediterranean"                                   -> "mediterranean" in tags
                                                             || "vegetarian" in tags || "vegan" in tags
                                                             || "pescatarian" in tags
        "omnivore", "high protein"                       -> true
        "chicken/fish"                                    -> "vegetarian" in tags || "vegan" in tags
                                                             || src in listOf("chicken","fish","seafood")
        "poultry"                                         -> "vegetarian" in tags || "vegan" in tags
                                                             || src in listOf("chicken","turkey")
        "meat/poultry"                                    -> "vegetarian" in tags || "vegan" in tags
                                                             || src in listOf("chicken","turkey",
                                                                              "beef","pork","lamb")
        "flexitarian"                                     -> "vegetarian" in tags || "vegan" in tags
                                                             || "flexitarian" in tags
        else                                              -> true
    }
}
```

### 4c. `rankRecipes()` — split Metabolic Reset / Insulin Resistance, replace `isGlucoseConscious`

```kotlin
// Metabolic Reset
goal.contains("metabolic") -> {
    if ("Metabolic Reset" in r.collection) score += 20.0
    if (r.glucoseImpactScore in 1..3) score += 8.0          // replaces isGlucoseConscious
    if ((r.fiberGrams ?: 0.0) >= 8.0) score += 4.0
    if (r.metabolicResetScore >= 7) score += 5.0
}

// Insulin Resistance — new dedicated branch
goal.contains("insulin") || goal.contains("insulin resistance") -> {
    if (r.insulinResistanceScore >= 7) score += r.insulinResistanceScore * 2.0
    if (r.glucoseImpactScore in 1..3) score += (10 - r.glucoseImpactScore) * 2.0
    if ((r.fiberGrams ?: 0.0) >= 8.0) score += 5.0
}

// Women's Health — new dedicated branch
goal.contains("women") || goal.contains("hormone") || goal.contains("pcos") -> {
    if (r.womensHealthScore >= 7) score += r.womensHealthScore * 2.0
    if (r.antiInflammatoryScore >= 6) score += 5.0
    if ((r.fiberGrams ?: 0.0) >= 8.0) score += 4.0
}
```

### 4d. `pickBooster()` — filter by `mealType` instead of protein threshold

```kotlin
// BEFORE
val boosters = candidates.filter { it.mealType == "Snack" && it.proteinGrams >= 25.0 }

// AFTER
val boosters = candidates.filter { it.mealType == "Protein Booster" }
```

---

## 5. Migration Strategy

### Step-by-step sequence (Phase 35A scope)

| Step | Action | Files changed |
|---|---|---|
| 1 | Update `Recipe.kt` — add new fields, remove `isGlucoseConscious` | `Recipe.kt` |
| 2 | Create `catalog/` folder with 5 stub files | 5 new files |
| 3 | Migrate existing 92 recipes — move + full re-tag | `BreakfastRecipes.kt`, `LunchRecipes.kt`, `DinnerRecipes.kt`, `SnackRecipes.kt`, `BoosterRecipes.kt` |
| 4 | Reclassify Snacks with `proteinGrams >= 25.0` → `mealType = "Protein Booster"`, move to `BoosterRecipes.kt` | `SnackRecipes.kt`, `BoosterRecipes.kt` |
| 5 | Replace `RecipeCatalog.kt` body with thin aggregator | `RecipeCatalog.kt` |
| 6 | Update `MealPlanEngine.kt` per Section 4 | `MealPlanEngine.kt` |
| 7 | Build — must be green before any new recipe content | — |

### Re-tagging rules for existing 92 recipes

Every migrated recipe must have all new fields explicitly set — no field left at default `0` or `emptyList()` where the value is knowable:

- `proteinSource`: derive from ingredients (e.g. Greek yogurt → `"Greek Yogurt"`, lentils → `"Lentils"`)
- `restrictions`: declare all that apply; `"Gluten Free"` if no wheat/barley/rye/seitan; `"Dairy Free"` if no dairy; etc.
- `glucoseImpactScore`: set 1–3 for high-fiber/low-GI recipes; 7–10 for recipes with refined carbs or added sugar
- `insulinResistanceScore`: set ≥7 for recipes already in Metabolic Reset collection
- `womensHealthScore`: set ≥6 for calcium-rich, iron-rich, or folate-heavy recipes

---

## 6. Expansion Roadmap (deferred — Phase 35B+)

Not in scope until Step 7 build is green.

| Phase | Action | Target |
|---|---|---|
| 35B | Add new Omnivore recipes (Breakfast + Lunch + Dinner) | +60 recipes |
| 35C | Add new Snack + Booster recipes | +30 recipes |
| 35D | Women's Health collection recipes | +20 recipes |
| **Total** | | **200 minimum / 250 stretch** |

### Health program coverage minimums (enforced in 35B–35D)

| Collection | Breakfast | Lunch | Dinner | Snack | Booster |
|---|---|---|---|---|---|
| Metabolic Reset | ≥8 | ≥10 | ≥10 | ≥7 | ≥5 |
| Anti-Inflammatory | ≥8 | ≥10 | ≥10 | ≥7 | ≥4 |
| Insulin Resistance | ≥8 | ≥10 | ≥10 | ≥7 | ≥5 |
| Heart Healthy | ≥5 | ≥8 | ≥8 | ≥5 | ≥3 |
| Fat Loss | ≥8 | ≥10 | ≥10 | ≥7 | ≥5 |
| Muscle Gain | ≥8 | ≥10 | ≥10 | ≥7 | ≥8 |
| Women's Health | ≥5 | ≥7 | ≥7 | ≥5 | ≥3 |

---

## 7. What Does Not Change

- `RecipesScreen.kt` — no UI changes
- `RecipesViewModel.kt` — no ViewModel changes
- `RecipesUiState.kt` — no state changes
- `RecipeSuggestionEngine.kt` — no changes
- Room schema — `Recipe` is not a Room entity; no migration needed
- `AppContainer.kt` — no wiring changes
- Grocery list generation — reads from `RecipeCatalog.ALL`; aggregator change is transparent
