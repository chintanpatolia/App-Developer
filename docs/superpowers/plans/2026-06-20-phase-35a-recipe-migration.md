# Phase 35A — Recipe Library Expansion: Architecture Migration

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Migrate all 92 existing recipes from a monolithic `RecipeCatalog.kt` into a split `catalog/` folder structure with an extended `Recipe` model, updated `MealPlanEngine` filters, and zero new recipe content added.

**Architecture:** `Recipe.kt` gains 4 new fields and loses `isGlucoseConscious`. `RecipeCatalog.kt` becomes an 8-line thin aggregator. Five catalog files (one per meal type) each own one slice of the recipe library. `MealPlanEngine.kt` gets explicit restriction lookup, 5 new diet filter cases, a split Metabolic/IR ranking branch, a Women's Health branch, and a type-based booster filter. No Room migration required — `Recipe` is not a Room entity.

**Tech Stack:** Kotlin · Jetpack Compose · Android Gradle
Build command: `$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug 2>&1 | Select-Object -Last 20`

---

## File Map

| Action | File | Responsibility |
|---|---|---|
| Modify | `ui/recipes/Recipe.kt` | Data model — add 4 fields, remove `isGlucoseConscious` |
| Create | `ui/recipes/catalog/BreakfastRecipes.kt` | All 21 breakfast recipes |
| Create | `ui/recipes/catalog/LunchRecipes.kt` | All 22 lunch recipes |
| Create | `ui/recipes/catalog/DinnerRecipes.kt` | All 22 dinner recipes |
| Create | `ui/recipes/catalog/SnackRecipes.kt` | Snacks with `proteinGrams < 25.0` |
| Create | `ui/recipes/catalog/BoosterRecipes.kt` | Snacks reclassified to `mealType = "Protein Booster"` (protein ≥ 25g) |
| Modify | `ui/recipes/RecipeCatalog.kt` | Replace body with thin aggregator |
| Modify | `ui/recipes/MealPlanEngine.kt` | Update 4 functions |

**Unchanged:** `RecipesScreen.kt`, `RecipesViewModel.kt`, `RecipesUiState.kt`, `RecipeSuggestionEngine.kt`, `AppContainer.kt`, all Room entities.

---

## Shared Re-Tagging Rules (apply in Tasks 3–6)

Every migrated recipe must have all new fields explicitly populated — no field left at default `0` or `emptyList()` where the value is knowable.

### `proteinSource` lookup

| Primary protein ingredient | Value |
|---|---|
| Greek yogurt | `"Greek Yogurt"` |
| Cottage cheese | `"Cottage Cheese"` |
| Paneer | `"Paneer"` |
| Tofu | `"Tofu"` |
| Tempeh | `"Tempeh"` |
| Soy chunks | `"Soy Chunks"` |
| Eggs | `"Eggs"` |
| Lentils | `"Lentils"` |
| Chickpeas / black beans | `"Lentils"` (legume family) |
| Protein powder only (no whole food protein) | `""` |
| Mixed plant sources, no dominant protein | `""` |

### `restrictions` rules

Declare every restriction that applies. Leave the list empty only if the recipe is not free of any restricted ingredient.

| Tag | Declare when recipe contains NONE of... |
|---|---|
| `"Gluten Free"` | wheat, bread, roti, seitan, barley, rye, flour, oats (unless GF-labelled) |
| `"Dairy Free"` | yogurt, cheese, paneer, ghee, butter, cream, whey, milk (plant milks do NOT count) |
| `"Nut Free"` | almond, walnut, cashew, pistachio, peanut, nut butter, tahini |
| `"Soy Free"` | tofu, tempeh, edamame, soy sauce, miso, soy milk, soy chunks |
| `"Egg Free"` | egg (any form) |

### `glucoseImpactScore` guidelines (1–10, lower = better for blood sugar)

| Score | Meaning | Typical ingredients |
|---|---|---|
| 1–3 | Minimal spike | High-fiber oats, lentils, non-starchy veg, berries, Greek yogurt plain |
| 4–6 | Moderate | Whole-grain bread, banana, sweet potato, brown rice, honey in small quantity |
| 7–10 | High spike | White rice, refined flour, granola with sugar, added sugar, large fruit portions |

### `insulinResistanceScore` (1–10)

- Set ≥ 7 for any recipe already in `collection = listOf("Metabolic Reset")` or with `glucoseImpactScore <= 3`
- Leave at `0` only if the recipe has no relevance to blood sugar management

### `womensHealthScore` (1–10)

- Set ≥ 6 for recipes rich in calcium (dairy, fortified plant milk, paneer), iron (lentils, spinach, tofu), folate (legumes, leafy greens), or magnesium (seeds, dark chocolate, beans)
- Leave at `0` for recipes with no notable micronutrient density for women's health

### Protein minimums by meal slot

Every recipe must meet its slot minimum. Flag any that don't with a `// TODO: below 25g protein target` comment rather than skipping.

| Slot | Minimum |
|---|---|
| Breakfast | ≥ 20g |
| Lunch | ≥ 25g |
| Dinner | ≥ 25g |
| Snack | ≥ 10g |
| Protein Booster | ≥ 25g |

---

## Task 1: Update Recipe.kt

**Files:**
- Modify: `app/src/main/java/com/dailyhealthcoach/ui/recipes/Recipe.kt`

- [ ] **Step 1: Replace the entire file with the updated model**

```kotlin
package com.dailyhealthcoach.ui.recipes

data class Recipe(
    val id: String,
    val name: String,
    val description: String = "",

    // "Breakfast" | "Lunch" | "Dinner" | "Snack" | "Protein Booster"
    val mealType: String,

    // Diet style tags: "Vegetarian" | "Vegan" | "Pescatarian" | "Mediterranean"
    //                  | "Omnivore" | "Flexitarian"
    val tags: List<String>,

    // Primary protein — see proteinSource lookup table in plan
    // "Chicken" | "Turkey" | "Beef" | "Fish" | "Seafood" | "Tofu" | "Paneer" |
    // "Tempeh" | "Eggs" | "Lentils" | "Greek Yogurt" | "Cottage Cheese" | "Soy Chunks" | ""
    val proteinSource: String = "",

    // Explicit restriction tags — replaces heuristic ingredient scanning
    // "Dairy Free" | "Gluten Free" | "Nut Free" | "Soy Free" | "Egg Free"
    val restrictions: List<String> = emptyList(),

    // Program collections
    // "Metabolic Reset" | "Anti-Inflammatory" | "Heart Healthy" | "Fat Loss" | "Muscle Gain"
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

    // Health program scores (1–10, higher = more beneficial)
    val metabolicResetScore: Int = 0,
    val antiInflammatoryScore: Int = 0,
    val insulinResistanceScore: Int = 0,
    val womensHealthScore: Int = 0,

    // Glucose impact (1 = minimal spike, 10 = high spike — lower is better)
    // Replaces isGlucoseConscious: Boolean — use glucoseImpactScore <= 3 wherever
    // isGlucoseConscious = true was used before
    val glucoseImpactScore: Int = 0,
)
```

- [ ] **Step 2: Expect compile errors — do not fix them yet**

Run: `$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat :app:compileDebugKotlin 2>&1 | Select-String "error:"`

Expected: errors referencing `isGlucoseConscious` in `RecipeCatalog.kt` and `MealPlanEngine.kt`. These are intentional — they will be resolved in Tasks 3–8.

- [ ] **Step 3: Commit**

```powershell
git add app/src/main/java/com/dailyhealthcoach/ui/recipes/Recipe.kt
git commit -m "Phase 35A: extend Recipe model — add proteinSource, restrictions, IR/glucose/womens scores, remove isGlucoseConscious"
```

---

## Task 2: Create catalog/ stub files

**Files:**
- Create: `app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/BreakfastRecipes.kt`
- Create: `app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/LunchRecipes.kt`
- Create: `app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/DinnerRecipes.kt`
- Create: `app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/SnackRecipes.kt`
- Create: `app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/BoosterRecipes.kt`

- [ ] **Step 1: Create BreakfastRecipes.kt**

```kotlin
package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object BreakfastRecipes {
    val ALL: List<Recipe> = listOf()
}
```

- [ ] **Step 2: Create LunchRecipes.kt**

```kotlin
package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object LunchRecipes {
    val ALL: List<Recipe> = listOf()
}
```

- [ ] **Step 3: Create DinnerRecipes.kt**

```kotlin
package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object DinnerRecipes {
    val ALL: List<Recipe> = listOf()
}
```

- [ ] **Step 4: Create SnackRecipes.kt**

```kotlin
package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object SnackRecipes {
    val ALL: List<Recipe> = listOf()
}
```

- [ ] **Step 5: Create BoosterRecipes.kt**

```kotlin
package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object BoosterRecipes {
    val ALL: List<Recipe> = listOf()
}
```

- [ ] **Step 6: Commit**

```powershell
git add app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/
git commit -m "Phase 35A: create catalog/ stub files"
```

---

## Task 3: Migrate Breakfast recipes (21 recipes)

**Files:**
- Source: `app/src/main/java/com/dailyhealthcoach/ui/recipes/RecipeCatalog.kt` — all `mealType = "Breakfast"` entries
- Modify: `app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/BreakfastRecipes.kt`

- [ ] **Step 1: Copy all 21 breakfast recipes into BreakfastRecipes.ALL**

In `RecipeCatalog.kt`, find every `Recipe(` block where `mealType = "Breakfast"`. Copy each into the `listOf(...)` in `BreakfastRecipes.ALL`.

For each copied recipe apply the re-tagging rules from the Shared Re-Tagging Rules section above:
1. Remove `isGlucoseConscious = true/false`
2. Add `proteinSource = "..."` after `tags`
3. Add `restrictions = listOf(...)` after `proteinSource`
4. Add `insulinResistanceScore = <n>` after `antiInflammatoryScore`
5. Add `womensHealthScore = <n>` after `insulinResistanceScore`
6. Add `glucoseImpactScore = <n>` at the end (before closing `)`

**Example — original recipe:**
```kotlin
Recipe(
    id = "greek_yogurt_bowl",
    name = "High-Protein Greek Yogurt Bowl",
    description = "Thick Greek yogurt boosted with protein powder...",
    mealType = "Breakfast",
    tags = listOf("Vegetarian", "High Protein"),
    collection = listOf("Metabolic Reset"),
    ingredients = listOf(
        "1 cup non-fat Greek yogurt", "1 scoop protein powder",
        "½ cup mixed berries", "2 tbsp low-fat granola", "1 tbsp honey"
    ),
    calories = 380, proteinGrams = 38.0, carbGrams = 42.0, fatGrams = 4.0, fiberGrams = 3.0,
    prepMinutes = 5, cookMinutes = 0,
    instructions = listOf(
        "Add Greek yogurt to a bowl.",
        "Stir in protein powder until smooth.",
        "Top with mixed berries and granola.",
        "Drizzle honey and serve immediately."
    ),
    storageNotes = "Best consumed fresh. Do not pre-mix protein powder.",
    metabolicResetScore = 8, isGlucoseConscious = true
)
```

**Example — re-tagged:**
```kotlin
Recipe(
    id = "greek_yogurt_bowl",
    name = "High-Protein Greek Yogurt Bowl",
    description = "Thick Greek yogurt boosted with protein powder...",
    mealType = "Breakfast",
    tags = listOf("Vegetarian", "High Protein"),
    proteinSource = "Greek Yogurt",
    restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
    collection = listOf("Metabolic Reset"),
    ingredients = listOf(
        "1 cup non-fat Greek yogurt", "1 scoop protein powder",
        "½ cup mixed berries", "2 tbsp low-fat granola", "1 tbsp honey"
    ),
    calories = 380, proteinGrams = 38.0, carbGrams = 42.0, fatGrams = 4.0, fiberGrams = 3.0,
    prepMinutes = 5, cookMinutes = 0,
    instructions = listOf(
        "Add Greek yogurt to a bowl.",
        "Stir in protein powder until smooth.",
        "Top with mixed berries and granola.",
        "Drizzle honey and serve immediately."
    ),
    storageNotes = "Best consumed fresh. Do not pre-mix protein powder.",
    metabolicResetScore = 8,
    antiInflammatoryScore = 0,
    insulinResistanceScore = 7,
    womensHealthScore = 6,
    glucoseImpactScore = 5  // granola + honey = moderate GI
)
```

- [ ] **Step 2: Verify count = 21**

```powershell
(Select-String -Path "app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/BreakfastRecipes.kt" -Pattern "^\s+id = ").Count
```

Expected: `21`

- [ ] **Step 3: Commit**

```powershell
git add app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/BreakfastRecipes.kt
git commit -m "Phase 35A: migrate 21 breakfast recipes with full re-tagging"
```

---

## Task 4: Migrate Lunch recipes (22 recipes)

**Files:**
- Source: `app/src/main/java/com/dailyhealthcoach/ui/recipes/RecipeCatalog.kt` — all `mealType = "Lunch"` entries
- Modify: `app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/LunchRecipes.kt`

- [ ] **Step 1: Copy all 22 lunch recipes into LunchRecipes.ALL**

Find every `Recipe(` block in `RecipeCatalog.kt` where `mealType = "Lunch"`. Copy each into `LunchRecipes.ALL`.

Apply the same re-tagging rules as Task 3:
1. Remove `isGlucoseConscious`
2. Add `proteinSource`, `restrictions`, `insulinResistanceScore`, `womensHealthScore`, `glucoseImpactScore`

Lunch protein floor is 25g. If any recipe has `proteinGrams < 25.0`, add a comment above it:
```kotlin
// TODO: protein below 25g target (current: Xg)
```

- [ ] **Step 2: Verify count = 22**

```powershell
(Select-String -Path "app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/LunchRecipes.kt" -Pattern "^\s+id = ").Count
```

Expected: `22`

- [ ] **Step 3: Commit**

```powershell
git add app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/LunchRecipes.kt
git commit -m "Phase 35A: migrate 22 lunch recipes with full re-tagging"
```

---

## Task 5: Migrate Dinner recipes (22 recipes)

**Files:**
- Source: `app/src/main/java/com/dailyhealthcoach/ui/recipes/RecipeCatalog.kt` — all `mealType = "Dinner"` entries
- Modify: `app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/DinnerRecipes.kt`

- [ ] **Step 1: Copy all 22 dinner recipes into DinnerRecipes.ALL**

Find every `Recipe(` block in `RecipeCatalog.kt` where `mealType = "Dinner"`. Copy each into `DinnerRecipes.ALL`.

Apply the same re-tagging rules as Task 3:
1. Remove `isGlucoseConscious`
2. Add `proteinSource`, `restrictions`, `insulinResistanceScore`, `womensHealthScore`, `glucoseImpactScore`

Dinner protein floor is 25g. Flag any under 25g with a `// TODO` comment as in Task 4.

- [ ] **Step 2: Verify count = 22**

```powershell
(Select-String -Path "app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/DinnerRecipes.kt" -Pattern "^\s+id = ").Count
```

Expected: `22`

- [ ] **Step 3: Commit**

```powershell
git add app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/DinnerRecipes.kt
git commit -m "Phase 35A: migrate 22 dinner recipes with full re-tagging"
```

---

## Task 6: Migrate Snack recipes + reclassify Boosters (27 recipes → split)

**Files:**
- Source: `app/src/main/java/com/dailyhealthcoach/ui/recipes/RecipeCatalog.kt` — all `mealType = "Snack"` entries
- Modify: `app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/SnackRecipes.kt`
- Modify: `app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/BoosterRecipes.kt`

**Reclassification rule:**
- `proteinGrams < 25.0` → `mealType = "Snack"` → `SnackRecipes.ALL`
- `proteinGrams >= 25.0` → `mealType = "Protein Booster"` → `BoosterRecipes.ALL`

- [ ] **Step 1: Identify the booster candidates**

In `RecipeCatalog.kt`, scan all 27 `mealType = "Snack"` entries. Note the `id` of every entry where `proteinGrams >= 25.0`. These move to `BoosterRecipes.kt`.

- [ ] **Step 2: Populate SnackRecipes.ALL**

Copy all snacks where `proteinGrams < 25.0` into `SnackRecipes.ALL`. Apply re-tagging rules (same as Task 3). Keep `mealType = "Snack"`.

- [ ] **Step 3: Populate BoosterRecipes.ALL**

Copy all snacks where `proteinGrams >= 25.0` into `BoosterRecipes.ALL`. Apply re-tagging rules. Change `mealType = "Snack"` → `mealType = "Protein Booster"`.

- [ ] **Step 4: Verify counts add up to 27**

```powershell
$s = (Select-String -Path "app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/SnackRecipes.kt" -Pattern "^\s+id = ").Count
$b = (Select-String -Path "app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/BoosterRecipes.kt" -Pattern "^\s+id = ").Count
Write-Host "Snack: $s   Booster: $b   Total: $($s + $b)"
```

Expected: total = `27`

- [ ] **Step 5: Commit**

```powershell
git add app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/SnackRecipes.kt
git add app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/BoosterRecipes.kt
git commit -m "Phase 35A: migrate 27 snack recipes — split into Snack and Protein Booster types"
```

---

## Task 7: Replace RecipeCatalog.kt with thin aggregator

**Files:**
- Modify: `app/src/main/java/com/dailyhealthcoach/ui/recipes/RecipeCatalog.kt`

- [ ] **Step 1: Replace the entire file**

```kotlin
package com.dailyhealthcoach.ui.recipes

import com.dailyhealthcoach.ui.recipes.catalog.BoosterRecipes
import com.dailyhealthcoach.ui.recipes.catalog.BreakfastRecipes
import com.dailyhealthcoach.ui.recipes.catalog.DinnerRecipes
import com.dailyhealthcoach.ui.recipes.catalog.LunchRecipes
import com.dailyhealthcoach.ui.recipes.catalog.SnackRecipes

object RecipeCatalog {
    val ALL: List<Recipe> =
        BreakfastRecipes.ALL + LunchRecipes.ALL + DinnerRecipes.ALL +
        SnackRecipes.ALL + BoosterRecipes.ALL
}
```

- [ ] **Step 2: Verify total recipe count across all catalog files**

```powershell
(Select-String -Path "app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog" -Pattern "^\s+id = " -Recurse).Count
```

Expected: `92`

- [ ] **Step 3: Commit**

```powershell
git add app/src/main/java/com/dailyhealthcoach/ui/recipes/RecipeCatalog.kt
git commit -m "Phase 35A: replace RecipeCatalog.kt with thin aggregator"
```

---

## Task 8: Update MealPlanEngine.kt

**Files:**
- Modify: `app/src/main/java/com/dailyhealthcoach/ui/recipes/MealPlanEngine.kt`

Make the four changes below one at a time in this order.

- [ ] **Step 1: Replace `violatesRestriction()`**

Find the existing `private fun violatesRestriction(...)` function (approximately 30 lines of keyword scanning). Replace the entire function body:

```kotlin
private fun violatesRestriction(recipe: Recipe, restriction: String): Boolean =
    recipe.restrictions.any { it.equals(restriction.trim(), ignoreCase = true) }
```

- [ ] **Step 2: Replace `matchesDiet()`**

Find the existing `private fun matchesDiet(...)`. Replace entirely:

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
                                                             || src in listOf("chicken", "fish", "seafood")
        "poultry"                                         -> "vegetarian" in tags || "vegan" in tags
                                                             || src in listOf("chicken", "turkey")
        "meat/poultry"                                    -> "vegetarian" in tags || "vegan" in tags
                                                             || src in listOf("chicken", "turkey",
                                                                              "beef", "pork", "lamb")
        "flexitarian"                                     -> "vegetarian" in tags || "vegan" in tags
                                                             || "flexitarian" in tags
        else                                              -> true
    }
}
```

- [ ] **Step 3: Update the `when` block inside `rankRecipes()`**

In `rankRecipes()`, find the `when {` block inside the `val scored = recipes.map { r -> ... }` lambda. The existing block has a compound `goal.contains("metabolic") || goal.contains("prediabetes") || goal.contains("insulin") || ...` branch. Replace the entire `when { ... }` block with:

```kotlin
when {
    goal.contains("metabolic") -> {
        if ("Metabolic Reset" in r.collection) score += 20.0
        if (r.glucoseImpactScore in 1..3) score += 8.0
        if ((r.fiberGrams ?: 0.0) >= 8.0) score += 4.0
        if (r.metabolicResetScore >= 7) score += 5.0
    }
    goal.contains("insulin") || goal.contains("insulin resistance") -> {
        if (r.insulinResistanceScore >= 7) score += r.insulinResistanceScore * 2.0
        if (r.glucoseImpactScore in 1..3) score += (10 - r.glucoseImpactScore) * 2.0
        if ((r.fiberGrams ?: 0.0) >= 8.0) score += 5.0
    }
    goal.contains("inflam") -> {
        if ("Anti-Inflammatory" in r.collection) score += 20.0
        if (r.antiInflammatoryScore >= 7) score += 8.0
    }
    goal.contains("muscle") || goal.contains("gain") -> score += r.proteinGrams * 0.8
    goal.contains("lose") || goal.contains("fat") || goal.contains("weight") -> {
        score += r.proteinGrams / r.calories.coerceAtLeast(1).toDouble() * 60.0
        if (r.calories < 400) score += 5.0
    }
    goal.contains("women") || goal.contains("hormone") || goal.contains("pcos") -> {
        if (r.womensHealthScore >= 7) score += r.womensHealthScore * 2.0
        if (r.antiInflammatoryScore >= 6) score += 5.0
        if ((r.fiberGrams ?: 0.0) >= 8.0) score += 4.0
    }
    else -> if (r.collection.isNotEmpty()) score += 5.0
}
```

Note: the old `goal.contains("prediabetes")` and `goal.contains("blood sugar")` match strings are intentionally removed — "Insulin Resistance" is the canonical goal label since Phase 34's chip rename.

- [ ] **Step 4: Replace `pickBooster()`**

Find `private fun pickBooster(candidates: List<Recipe>, targetGrams: Double): Recipe?`. Replace:

```kotlin
private fun pickBooster(candidates: List<Recipe>, targetGrams: Double): Recipe? =
    candidates.filter { it.mealType == "Protein Booster" }
              .minByOrNull { kotlin.math.abs(it.proteinGrams - targetGrams) }
```

- [ ] **Step 5: Commit**

```powershell
git add app/src/main/java/com/dailyhealthcoach/ui/recipes/MealPlanEngine.kt
git commit -m "Phase 35A: update MealPlanEngine — explicit restrictions, new diet filters, split IR ranking, womens-health branch, type-based booster filter"
```

---

## Task 9: Build verification

**Files:** None changed.

- [ ] **Step 1: Full debug build**

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat assembleDebug 2>&1 | Select-Object -Last 20
```

Expected: `BUILD SUCCESSFUL`

**If build fails — most likely causes:**

| Error pattern | Fix |
|---|---|
| `Unresolved reference: isGlucoseConscious` | Search with `Select-String -Path "app/src" -Pattern "isGlucoseConscious" -Recurse` — remove every remaining reference |
| `No value passed for parameter 'mealType'` | A Recipe constructor in a catalog file is missing a required field — check the file named in the error |
| `Unresolved reference: prediabetes` | The old compound branch in `rankRecipes()` wasn't fully replaced — check MealPlanEngine.kt |
| `None of the following candidates...` | Recipe field order mismatch — Kotlin named params prevent this, but check if any recipe uses positional args |

- [ ] **Step 2: Confirm zero `isGlucoseConscious` references**

```powershell
(Select-String -Path "app/src" -Pattern "isGlucoseConscious" -Recurse).Count
```

Expected: `0`

- [ ] **Step 3: Confirm 92 recipes total**

```powershell
(Select-String -Path "app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog" -Pattern "^\s+id = " -Recurse).Count
```

Expected: `92`

- [ ] **Step 4: Confirm Protein Booster type exists in BoosterRecipes**

```powershell
(Select-String -Path "app/src/main/java/com/dailyhealthcoach/ui/recipes/catalog/BoosterRecipes.kt" -Pattern '"Protein Booster"').Count
```

Expected: ≥ 1 (at least one recipe with `mealType = "Protein Booster"`)

---

## Task 10: Validate recipe engine on device

- [ ] **Step 1: Install on device**

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'; .\gradlew.bat installDebug 2>&1 | Select-Object -Last 10
```

Expected: `BUILD SUCCESSFUL` and app installs.

- [ ] **Step 2: Validate Vegetarian diet filter**

- Profile → Diet Preferences → select "High Protein Vegetarian" only → Save
- Recipes → Generate meal plan
- Confirm: every meal slot shows Vegetarian or Vegan recipes — no Chicken, Beef, Fish, Turkey

- [ ] **Step 3: Validate Chicken/Fish diet filter**

- Profile → Diet Preferences → select "Chicken/Fish" only → Save
- Recipes → Generate meal plan
- Confirm: all recipes are Vegetarian, Vegan, or have `proteinSource` of Chicken/Fish/Seafood. No Tofu-only dinners, no Paneer.

- [ ] **Step 4: Validate Dairy Free restriction**

- Profile → Food Restrictions → select "Dairy Free" → Save
- Recipes → Generate meal plan
- Confirm: no Greek Yogurt Bowl, no Paneer Tikka, no cottage cheese recipes appear

- [ ] **Step 5: Validate Protein Booster slots appear**

- Profile → Protein max → set to 220g (high enough to leave a gap after 4 meals)
- Recipes → Generate meal plan
- Confirm: "Protein Booster 1" slot appears on at least some days
- Confirm: the recipe shown in that slot has `proteinGrams >= 25.0` (cross-check against BoosterRecipes.kt by recipe name)

- [ ] **Step 6: Validate grocery list completeness**

- Generate a full week meal plan
- Open Grocery List
- Confirm: ingredients appear from Breakfast, Lunch, Dinner, Snack, and Protein Booster slots
- Confirm: no crash, no empty list

- [ ] **Step 7: Validate Nutrition screen macro targets unchanged**

- Open Nutrition screen
- Confirm: protein goal, calorie target, carb/fat/fiber targets display the same values as before migration (these come from `MacroTargetRepository`, not `RecipeCatalog` — this confirms the aggregator swap was transparent)

- [ ] **Step 8: Final commit**

```powershell
git commit --allow-empty -m "Phase 35A: migration complete and validated — 92 recipes, split catalog, extended model, updated engine"
```
