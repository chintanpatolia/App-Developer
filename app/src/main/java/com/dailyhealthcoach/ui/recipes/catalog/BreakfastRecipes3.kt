package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object BreakfastRecipes3 {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "salmon_spinach_breakfast_plate",
            name = "Smoked Salmon & Spinach Morning Plate",
            description = "Omega-3 rich smoked salmon layered over baby spinach with avocado, pumpkin seeds, and flaxseed — a hormone-supporting, deeply anti-inflammatory start to the day with virtually zero glucose impact.",
            mealType = "Breakfast",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "100g smoked salmon",
                "2 cups baby spinach",
                "¼ avocado, sliced",
                "½ cucumber, thinly sliced",
                "1 tbsp pumpkin seeds",
                "1 tbsp ground flaxseed",
                "1 tsp capers",
                "2 tsp fresh dill (or ½ tsp dried)",
                "1 tbsp lemon juice",
                "Black pepper to taste"
            ),
            calories = 310, proteinGrams = 28.0, carbGrams = 8.0, fatGrams = 18.0, fiberGrams = 5.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Arrange baby spinach as a base on a plate.",
                "Layer smoked salmon over the spinach.",
                "Fan avocado slices and cucumber alongside.",
                "Scatter pumpkin seeds and flaxseed over the top.",
                "Add capers, drizzle with lemon juice, and season with black pepper.",
                "Garnish with fresh dill and serve immediately."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "chia_hemp_berry_breakfast_bowl",
            name = "Chia Hemp Berry Breakfast Bowl",
            description = "Overnight chia pudding enriched with hemp hearts, flaxseed, and antioxidant mixed berries — a complete omega-3, lignan, and GLA powerhouse that balances hormones and fights inflammation without any glucose spike.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Seeds",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "3 tbsp chia seeds",
                "4 tbsp hemp hearts",
                "1 tbsp ground flaxseed",
                "1 cup unsweetened almond milk",
                "½ cup mixed berries (fresh or frozen)",
                "¼ tsp cinnamon",
                "½ tsp pure vanilla extract"
            ),
            calories = 365, proteinGrams = 20.0, carbGrams = 22.0, fatGrams = 22.0, fiberGrams = 12.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine chia seeds, hemp hearts, flaxseed, cinnamon, and vanilla in a jar or bowl.",
                "Pour in almond milk and stir thoroughly to prevent clumping.",
                "Cover and refrigerate overnight, or for at least 4 hours.",
                "In the morning, stir the set pudding and add a splash more almond milk if too thick.",
                "Top with mixed berries and serve."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "pea_matcha_breakfast_smoothie",
            name = "Pea Protein Matcha Morning Smoothie",
            description = "Plant-based pea protein blended with ceremonial-grade matcha, spinach, and chia — delivering EGCG catechin antioxidants alongside a complete amino acid profile for sustained morning energy without any blood sugar spike.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Low Carb"),
            proteinSource = "Pea Protein",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "1 scoop (30g) pea protein powder, unflavoured",
                "1 tsp matcha powder (ceremonial grade preferred)",
                "1 cup baby spinach",
                "1 cup unsweetened almond milk",
                "1 tbsp chia seeds",
                "½ tsp fresh ginger, grated",
                "Juice of ¼ lemon",
                "4–5 ice cubes"
            ),
            calories = 225, proteinGrams = 27.0, carbGrams = 12.0, fatGrams = 5.0, fiberGrams = 4.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add almond milk to the blender first.",
                "Add spinach, matcha, ginger, and lemon juice.",
                "Add chia seeds and pea protein powder.",
                "Add ice cubes and blend on high for 60 seconds until completely smooth.",
                "Pour into a glass and consume within 10 minutes for best matcha potency."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "walnut_flax_berry_oats",
            name = "Walnut Flaxseed Anti-Inflammatory Oats",
            description = "Slow-rolled oats enriched with brain-healthy walnuts, hormone-balancing flaxseed, hemp hearts, and anti-inflammatory ginger — topped with antioxidant berries rather than high-sugar fruit to keep glucose stable.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein", "Whole Foods"),
            proteinSource = "Nuts",
            restrictions = listOf("Dairy Free", "Egg Free", "Soy Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "½ cup rolled oats",
                "1 cup unsweetened almond milk",
                "3 tbsp walnuts, roughly chopped",
                "2 tbsp hemp hearts",
                "1 tbsp ground flaxseed",
                "1 tbsp chia seeds",
                "¼ tsp ground cinnamon",
                "¼ tsp ground ginger",
                "½ cup mixed berries"
            ),
            calories = 395, proteinGrams = 20.0, carbGrams = 36.0, fatGrams = 20.0, fiberGrams = 10.0,
            prepMinutes = 2, cookMinutes = 8,
            instructions = listOf(
                "Bring almond milk to a gentle simmer in a small saucepan.",
                "Stir in oats, cinnamon, and ginger.",
                "Cook over medium-low heat for 6–8 minutes, stirring frequently until thickened.",
                "Remove from heat and stir in flaxseed and chia seeds.",
                "Transfer to a bowl and top with walnuts, hemp hearts, and mixed berries.",
                "Serve immediately."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 8,
            womensHealthScore = 9,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "tempeh_turmeric_breakfast_scramble",
            name = "Tempeh Turmeric Breakfast Scramble",
            description = "Crumbled tempeh sautéed with golden turmeric, fresh ginger, and iron-rich spinach — a plant-based scramble delivering complete protein alongside curcumin and the full anti-inflammatory spice spectrum.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Low Carb"),
            proteinSource = "Tempeh",
            restrictions = listOf("Dairy Free", "Gluten Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "100g tempeh, crumbled",
                "2 cups baby spinach",
                "½ red capsicum, diced",
                "1 tsp ground turmeric",
                "1 tsp fresh ginger, grated",
                "1 tbsp nutritional yeast",
                "1 tsp sesame seeds",
                "1 tsp olive oil",
                "Pinch of black pepper",
                "Salt to taste"
            ),
            calories = 275, proteinGrams = 24.0, carbGrams = 14.0, fatGrams = 13.0, fiberGrams = 4.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Heat olive oil in a non-stick pan over medium heat.",
                "Add crumbled tempeh and cook for 3 minutes until lightly golden.",
                "Add capsicum and cook for 2 more minutes.",
                "Stir in turmeric, ginger, and black pepper; cook for 1 minute.",
                "Add spinach and stir until wilted, about 2 minutes.",
                "Remove from heat and stir in nutritional yeast.",
                "Top with sesame seeds and serve."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "sardine_avocado_morning_bowl",
            name = "Sardine & Avocado Morning Power Bowl",
            description = "Calcium-rich sardines over baby spinach with creamy avocado, pumpkin seeds, and flaxseed — one of the highest combined EPA+DHA+calcium+vitamin D breakfasts in the catalog, with near-zero glucose impact.",
            mealType = "Breakfast",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "1 can (95g) sardines in spring water, drained",
                "½ avocado, sliced",
                "½ cucumber, sliced",
                "2 cups baby spinach",
                "1 tbsp pumpkin seeds",
                "1 tbsp ground flaxseed",
                "1 tbsp fresh dill",
                "Juice of ½ lemon",
                "Black pepper to taste"
            ),
            calories = 335, proteinGrams = 30.0, carbGrams = 10.0, fatGrams = 20.0, fiberGrams = 6.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Layer baby spinach in a bowl as the base.",
                "Arrange drained sardines over the spinach.",
                "Add avocado slices and cucumber alongside.",
                "Sprinkle pumpkin seeds and ground flaxseed over the top.",
                "Squeeze lemon juice over everything and finish with fresh dill and black pepper."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "hemp_pumpkin_seed_power_bowl",
            name = "Hemp & Pumpkin Seed Breakfast Power Bowl",
            description = "A no-cook bowl built on hemp hearts and pumpkin seeds — providing GLA for hormone regulation, zinc for reproductive health, and magnesium for stress resilience — topped with blueberries for anthocyanin antioxidants.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Seeds",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "4 tbsp hemp hearts",
                "2 tbsp pumpkin seeds",
                "2 tbsp chia seeds",
                "1 tbsp ground flaxseed",
                "1 cup unsweetened almond milk",
                "½ cup blueberries",
                "¼ tsp ground cinnamon",
                "½ tsp pure vanilla extract"
            ),
            calories = 345, proteinGrams = 22.0, carbGrams = 20.0, fatGrams = 20.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine hemp hearts, pumpkin seeds, chia seeds, and flaxseed in a bowl.",
                "Stir in cinnamon and vanilla extract.",
                "Pour almond milk over the mixture and stir to combine.",
                "Let sit for 5–10 minutes until chia begins to gel slightly.",
                "Top with blueberries and serve."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "golden_pea_turmeric_smoothie",
            name = "Golden Pea Protein Anti-Inflammatory Smoothie",
            description = "A deep-golden smoothie combining pea protein with curcumin-rich turmeric, anti-nausea ginger, omega-3 flaxseed, and berries — the black pepper activates curcumin absorption significantly for maximal anti-inflammatory effect.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Low Carb"),
            proteinSource = "Pea Protein",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "1 scoop (30g) pea protein powder, unflavoured",
                "1 tsp ground turmeric",
                "½ tsp fresh ginger, grated",
                "½ cup mixed berries (fresh or frozen)",
                "1 cup baby spinach",
                "1 tbsp ground flaxseed",
                "1 tbsp chia seeds",
                "1 cup unsweetened almond milk",
                "Pinch of black pepper"
            ),
            calories = 270, proteinGrams = 28.0, carbGrams = 18.0, fatGrams = 7.0, fiberGrams = 7.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add almond milk and spinach to the blender first.",
                "Add berries, turmeric, ginger, and black pepper.",
                "Add flaxseed, chia seeds, and pea protein powder.",
                "Blend on high for 60 seconds until silky smooth.",
                "Pour into a glass and consume within 15 minutes."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

    )
}
