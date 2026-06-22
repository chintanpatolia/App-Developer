package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object FishRecipes {
    val ALL: List<Recipe> = listOf(

        // ── Lunch ────────────────────────────────────────────────────────────

        Recipe(
            id = "teriyaki_salmon_rice_bowl",
            name = "Teriyaki Salmon & Brown Rice Bowl",
            description = "Pan-seared salmon glazed in a tamari-ginger teriyaki sauce served over brown rice with edamame and cucumber — combining EPA+DHA from salmon with the slow-release carbohydrates of brown rice and the complete plant protein of edamame.",
            mealType = "Lunch",
            tags = listOf("Pescatarian", "High Protein", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "160g salmon fillet",
                "½ cup cooked brown rice",
                "¼ cup shelled edamame (frozen, thawed)",
                "½ cucumber, sliced",
                "1 tbsp tamari (gluten-free soy sauce)",
                "1 tsp fresh ginger, grated",
                "1 tsp honey or maple syrup",
                "1 tsp sesame oil",
                "1 tsp sesame seeds",
                "1 spring onion, sliced"
            ),
            calories = 430, proteinGrams = 36.0, carbGrams = 44.0, fatGrams = 14.0, fiberGrams = 5.0,
            prepMinutes = 8, cookMinutes = 12,
            instructions = listOf(
                "Whisk tamari, ginger, honey, and sesame oil into a teriyaki glaze.",
                "Heat a non-stick pan over medium-high heat; sear salmon skin-side up for 4 minutes.",
                "Flip salmon; brush with glaze and cook 3–4 more minutes until cooked through.",
                "Serve salmon over brown rice with edamame and cucumber.",
                "Drizzle remaining glaze over the bowl; top with sesame seeds and spring onion."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 8,
            womensHealthScore = 9,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "tuna_nicoise_bowl",
            name = "Tuna Niçoise Lunch Bowl",
            description = "Chunky canned tuna over a bed of green beans, cherry tomatoes, cucumber, and kalamata olives with a lemon-herb vinaigrette — a Mediterranean-style, no-cook lunch that stacks omega-3 with lycopene, polyphenols, and soluble fiber.",
            mealType = "Lunch",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "170g canned tuna in spring water, drained",
                "1 cup green beans, blanched",
                "½ cup cherry tomatoes, halved",
                "½ cucumber, sliced into half-rounds",
                "8 kalamata olives",
                "1 tbsp lemon juice",
                "1 tsp olive oil",
                "½ tsp dried oregano",
                "Fresh parsley to serve",
                "Salt and black pepper to taste"
            ),
            calories = 290, proteinGrams = 34.0, carbGrams = 12.0, fatGrams = 11.0, fiberGrams = 5.0,
            prepMinutes = 10, cookMinutes = 4,
            instructions = listOf(
                "Blanch green beans in boiling water for 3–4 minutes; drain and refresh in cold water.",
                "Whisk lemon juice, olive oil, and oregano into a dressing.",
                "Arrange green beans, cherry tomatoes, cucumber, and olives in a bowl.",
                "Top with tuna (broken into chunks).",
                "Drizzle dressing over the bowl; scatter fresh parsley and season with salt and pepper."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        // ── Dinner ───────────────────────────────────────────────────────────

        Recipe(
            id = "miso_glazed_salmon_pak_choy",
            name = "Miso Glazed Salmon with Pak Choy",
            description = "Salmon fillet caramelised under a white miso and ginger glaze served alongside steamed pak choy with a sesame drizzle — miso provides gut-supportive fermented umami while salmon's EPA+DHA makes this the highest omega-3 dinner in the catalog.",
            mealType = "Dinner",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "180g salmon fillet",
                "1 tbsp white miso paste",
                "1 tsp fresh ginger, grated",
                "1 tsp tamari (gluten-free soy sauce)",
                "1 tsp honey or maple syrup",
                "2 heads pak choy, halved",
                "1 tsp sesame oil",
                "1 tsp sesame seeds",
                "2 spring onions, sliced",
                "Fresh coriander to serve"
            ),
            calories = 400, proteinGrams = 38.0, carbGrams = 12.0, fatGrams = 22.0, fiberGrams = 3.0,
            prepMinutes = 8, cookMinutes = 14,
            instructions = listOf(
                "Mix miso, ginger, tamari, and honey into a glaze.",
                "Spread glaze over salmon and rest 5 minutes.",
                "Preheat grill or oven to 220°C; grill salmon 10–12 minutes until caramelised.",
                "Steam pak choy 3–4 minutes until just tender.",
                "Drizzle pak choy with sesame oil.",
                "Serve salmon alongside pak choy; top with sesame seeds, spring onion, and coriander."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "herb_baked_cod_sweet_potato",
            name = "Herb Baked Cod with Roasted Sweet Potato",
            description = "Cod fillet baked with lemon, garlic, rosemary, and thyme alongside roasted sweet potato and steamed broccoli — a lean, low-fat white fish dinner where sweet potato provides beta-carotene and resistant starch alongside the very high-protein profile of cod.",
            mealType = "Dinner",
            tags = listOf("Pescatarian", "High Protein", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "200g cod fillet",
                "1 medium sweet potato, peeled and cubed",
                "1 cup broccoli florets",
                "1 tbsp lemon juice",
                "1 tsp lemon zest",
                "1 clove garlic, minced",
                "½ tsp dried rosemary",
                "½ tsp dried thyme",
                "1 tsp olive oil",
                "Salt and black pepper to taste",
                "Fresh parsley to serve"
            ),
            calories = 380, proteinGrams = 36.0, carbGrams = 32.0, fatGrams = 9.0, fiberGrams = 6.0,
            prepMinutes = 10, cookMinutes = 24,
            instructions = listOf(
                "Preheat oven to 200°C. Line a baking tray.",
                "Toss sweet potato in a little olive oil, salt, and pepper; place on tray.",
                "Roast sweet potato 12 minutes.",
                "Rub cod with lemon juice, lemon zest, garlic, rosemary, thyme, salt, and pepper; place on tray.",
                "Add broccoli to the tray; roast everything together for 12–14 more minutes until cod flakes easily.",
                "Serve with fresh parsley."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        // ── Protein Booster ──────────────────────────────────────────────────

        Recipe(
            id = "tuna_avocado_smash_booster",
            name = "Tuna & Avocado Smash Protein Booster",
            description = "Chunky canned tuna folded through mashed avocado with lemon, spring onion, and fresh herbs — a zero-cook protein booster where omega-3 from tuna and oleic acid from avocado create an anti-inflammatory fat profile rarely matched in a 5-minute preparation.",
            mealType = "Protein Booster",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "120g canned tuna in spring water, drained",
                "½ ripe avocado",
                "1 tbsp lemon juice",
                "1 spring onion, finely sliced",
                "2 tbsp fresh coriander or parsley",
                "Salt and black pepper to taste"
            ),
            calories = 240, proteinGrams = 28.0, carbGrams = 6.0, fatGrams = 12.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Mash avocado in a bowl with lemon juice, salt, and pepper.",
                "Fold in tuna, spring onion, and fresh herbs.",
                "Serve immediately or refrigerate for up to 2 hours."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "smoked_mackerel_cucumber_booster",
            name = "Smoked Mackerel & Cucumber Protein Booster",
            description = "Flaked smoked mackerel over cucumber rounds with lemon, dill, and cherry tomatoes — mackerel has one of the highest EPA+DHA concentrations of any canned fish, making this the most omega-3-dense no-cook protein booster in the catalog per gram.",
            mealType = "Protein Booster",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "100g canned mackerel in spring water, drained",
                "1 cucumber, sliced into thick rounds",
                "½ cup cherry tomatoes, halved",
                "1 tbsp lemon juice",
                "1 tbsp fresh dill or parsley",
                "Salt and black pepper to taste"
            ),
            calories = 220, proteinGrams = 24.0, carbGrams = 6.0, fatGrams = 11.0, fiberGrams = 1.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Arrange cucumber rounds on a plate.",
                "Break mackerel into chunks and place over cucumber.",
                "Add cherry tomatoes; drizzle with lemon juice.",
                "Scatter fresh dill or parsley; season with salt and pepper."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 1
        ),

    )
}
