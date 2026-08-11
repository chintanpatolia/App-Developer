package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object BoosterRecipes2 {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "pea_protein_berry_omega_booster",
            name = "Pea Protein Berry Omega Booster",
            description = "A fast anti-inflammatory shake blending pea protein with mixed berries, chia, flaxseed, and baby spinach — delivering 33g of plant-complete protein alongside omega-3 ALA, lignans, and anthocyanins for hormonal support and inflammation control.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein", "Low Carb"),
            proteinSource = "Pea Protein",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "1.5 scoops (45g) pea protein powder, unflavoured",
                "½ cup mixed berries (fresh or frozen)",
                "2 tbsp chia seeds",
                "1 tbsp ground flaxseed",
                "1 cup baby spinach",
                "1.5 cups unsweetened almond milk",
                "½ tsp ground ginger",
                "4 ice cubes"
            ),
            calories = 295, proteinGrams = 33.0, carbGrams = 20.0, fatGrams = 8.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add almond milk and spinach to the blender.",
                "Add berries, ginger, chia seeds, and flaxseed.",
                "Add pea protein powder and ice.",
                "Blend on high for 60 seconds until completely smooth.",
                "Pour into a glass or bowl and consume immediately."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "hemp_seed_omega3_booster",
            name = "Hemp Seed Omega-3 Power Booster Bowl",
            description = "A dense seed bowl combining hemp hearts, chia, and pumpkin seeds over almond milk — providing GLA, ALA, zinc, and magnesium with a full 25g protein from whole-food seeds, making it the highest-WH booster in the catalog for reproductive and hormonal health.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Seeds",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "5 tbsp hemp hearts",
                "2 tbsp chia seeds",
                "2 tbsp pumpkin seeds",
                "1 tbsp ground flaxseed",
                "1 cup unsweetened almond milk",
                "½ cup mixed berries",
                "¼ tsp ground cinnamon"
            ),
            calories = 345, proteinGrams = 25.0, carbGrams = 18.0, fatGrams = 22.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine hemp hearts, chia seeds, pumpkin seeds, flaxseed, and cinnamon in a bowl.",
                "Pour almond milk over the mixture and stir well.",
                "Let sit for 5 minutes until chia begins to hydrate.",
                "Top with mixed berries and serve."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "smoked_salmon_avocado_booster",
            name = "Smoked Salmon & Avocado Anti-Inflammatory Booster",
            description = "A no-cook protein booster of smoked salmon with avocado, hemp hearts, pumpkin seeds, capers, and dill — stacking EPA+DHA from salmon with GLA from hemp and zinc from pumpkin seeds for a 34g protein hit with near-zero glucose impact.",
            mealType = "Protein Booster",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "120g smoked salmon",
                "½ avocado, sliced",
                "2 tbsp hemp hearts",
                "1 tbsp pumpkin seeds",
                "1 tbsp capers",
                "1 tbsp fresh dill",
                "Juice of ¼ lemon",
                "Black pepper to taste"
            ),
            calories = 345, proteinGrams = 34.0, carbGrams = 8.0, fatGrams = 20.0, fiberGrams = 4.0,
            prepMinutes = 6, cookMinutes = 0,
            instructions = listOf(
                "Arrange smoked salmon on a plate or in a bowl.",
                "Add avocado slices alongside.",
                "Scatter hemp hearts, pumpkin seeds, and capers over the top.",
                "Finish with lemon juice, fresh dill, and black pepper."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "tempeh_ginger_turmeric_booster",
            name = "Tempeh Ginger & Turmeric Power Booster",
            description = "Pan-seared tempeh marinated in ginger, turmeric, and tamari — a fast 30g plant-protein booster where fermented soy provides the complete amino acid profile and curcumin-ginger synergy delivers one of the highest AI scores in the booster category.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein", "Low Carb"),
            proteinSource = "Tempeh",
            restrictions = listOf("Dairy Free", "Gluten Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "170g tempeh, sliced",
                "1 tsp fresh ginger, grated",
                "½ tsp ground turmeric",
                "1 tbsp tamari (gluten-free soy sauce)",
                "1 cup baby spinach",
                "1 tsp sesame oil",
                "1 tsp sesame seeds",
                "Pinch of black pepper"
            ),
            calories = 300, proteinGrams = 30.0, carbGrams = 12.0, fatGrams = 15.0, fiberGrams = 4.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Mix ginger, turmeric, tamari, and black pepper in a small bowl.",
                "Marinate tempeh slices for 2 minutes.",
                "Heat sesame oil in a non-stick pan over medium-high heat.",
                "Cook tempeh 3–4 minutes per side until caramelised.",
                "Add spinach in the final minute and stir until wilted.",
                "Serve topped with sesame seeds."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "walnut_hemp_antioxidant_booster",
            name = "Walnut & Hemp Antioxidant Booster Bowl",
            description = "A nutrient-dense no-cook bowl combining walnuts, hemp hearts, pumpkin seeds, and flaxseed with berries and almond milk — triple-stacking ALA omega-3 sources alongside zinc, magnesium, and lignans for comprehensive hormonal and anti-inflammatory support.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Nuts",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "¼ cup walnuts, roughly chopped",
                "4 tbsp hemp hearts",
                "2 tbsp pumpkin seeds",
                "1 tbsp ground flaxseed",
                "½ cup mixed berries",
                "½ cup unsweetened almond milk",
                "¼ tsp ground cinnamon"
            ),
            calories = 380, proteinGrams = 25.0, carbGrams = 16.0, fatGrams = 28.0, fiberGrams = 7.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine walnuts, hemp hearts, pumpkin seeds, and flaxseed in a bowl.",
                "Add cinnamon and stir to mix.",
                "Pour almond milk over the mixture.",
                "Top with mixed berries and serve immediately."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "pea_matcha_anti_inflammatory_booster",
            name = "Pea Protein Matcha Anti-Inflammatory Booster",
            description = "Two scoops of pea protein blended with ceremonial matcha, spinach, ginger, and chia — delivering 40g of plant-complete protein alongside L-theanine for calm focus, EGCG for cell protection, and ginger for prostaglandin inhibition.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein", "Low Carb"),
            proteinSource = "Pea Protein",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "2 scoops (60g) pea protein powder, unflavoured",
                "1 tsp matcha powder (ceremonial grade)",
                "1 cup baby spinach",
                "½ tsp fresh ginger, grated",
                "1 tbsp chia seeds",
                "1.5 cups unsweetened almond milk",
                "Juice of ¼ lemon",
                "4 ice cubes"
            ),
            calories = 295, proteinGrams = 40.0, carbGrams = 14.0, fatGrams = 6.0, fiberGrams = 5.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add almond milk and spinach to the blender.",
                "Add matcha, ginger, lemon juice, and chia seeds.",
                "Add pea protein powder and ice.",
                "Blend on high for 60 seconds until completely smooth.",
                "Serve immediately in a large glass."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "sardine_avocado_anti_inflammatory_booster",
            name = "Sardine & Avocado Anti-Inflammatory Protein Booster",
            description = "One and a half cans of sardines with avocado, spinach, pumpkin seeds, and lemon — one of the most compact anti-inflammatory protein boosters in the catalog, delivering 30g protein, vitamin D, calcium, EPA, and DHA in under 5 minutes of prep.",
            mealType = "Protein Booster",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "1.5 cans (135g) sardines in spring water, drained",
                "½ avocado, mashed or sliced",
                "1 cup baby spinach",
                "1 tbsp pumpkin seeds",
                "1 tbsp fresh parsley, chopped",
                "Juice of ½ lemon",
                "Black pepper to taste"
            ),
            calories = 355, proteinGrams = 30.0, carbGrams = 8.0, fatGrams = 22.0, fiberGrams = 5.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Drain sardines and place in a bowl.",
                "Add baby spinach alongside or underneath.",
                "Add avocado, either mashed over the sardines or sliced alongside.",
                "Scatter pumpkin seeds and fresh parsley over the top.",
                "Squeeze lemon juice over everything and season with black pepper."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "ultra_seed_omega_booster",
            name = "Ultra Seed Omega-3 Anti-Inflammatory Booster Bowl",
            description = "A maximalist seed booster combining hemp hearts, chia, pumpkin seeds, sunflower seeds, and flaxseed with berries and almond milk — stacking GLA, ALA, zinc, selenium, magnesium, and vitamin E into a single 23g-protein bowl that comprehensively supports hormones, fertility, and inflammation pathways.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Seeds",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "4 tbsp hemp hearts",
                "2 tbsp chia seeds",
                "2 tbsp pumpkin seeds",
                "1 tbsp sunflower seeds",
                "1 tbsp ground flaxseed",
                "1 cup unsweetened almond milk",
                "¼ cup mixed berries",
                "¼ tsp ground cinnamon"
            ),
            calories = 340, proteinGrams = 23.0, carbGrams = 16.0, fatGrams = 22.0, fiberGrams = 10.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine hemp hearts, chia seeds, pumpkin seeds, sunflower seeds, flaxseed, and cinnamon in a bowl.",
                "Pour almond milk over the seed mix and stir well.",
                "Let sit for 5–10 minutes until chia gels into a pudding texture.",
                "Top with mixed berries and serve."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 2
        ),

    )
}
