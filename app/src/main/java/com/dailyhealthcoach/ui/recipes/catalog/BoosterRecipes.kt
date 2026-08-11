package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object BoosterRecipes {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "protein_smoothie_bowl",
            name = "Protein Smoothie Bowl",
            description = "A thick-blended protein shake poured into a bowl and topped with crunchy granola, chia seeds, and mixed berries. Thick enough to eat with a spoon.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Protein Powder",
            restrictions = listOf("Dairy Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 scoop vanilla protein powder",
                "½ cup frozen banana",
                "½ cup frozen mixed berries",
                "½ cup unsweetened almond milk",
                "1 tbsp chia seeds",
                "2 tbsp low-fat granola"
            ),
            calories = 320, proteinGrams = 30.0, carbGrams = 36.0, fatGrams = 5.0, fiberGrams = 5.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Blend protein powder, banana, berries, and almond milk until thick.",
                "Pour into a bowl; should be thicker than a drink.",
                "Top with chia seeds and granola; serve immediately."
            ),
            metabolicResetScore = 6,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 5,
            womensHealthScore = 6,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "protein_chia_pudding",
            name = "Protein Chia Pudding",
            description = "Silky overnight chia pudding with protein powder and a honey-vanilla finish. Chia seeds provide 5g of omega-3 ALA per serving — a natural anti-inflammatory.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Protein Powder",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "3 tbsp chia seeds",
                "1 scoop vanilla protein powder",
                "1 cup unsweetened almond milk",
                "1 tbsp honey",
                "¼ tsp vanilla extract"
            ),
            calories = 300, proteinGrams = 25.0, carbGrams = 28.0, fatGrams = 9.0, fiberGrams = 10.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Whisk protein powder into almond milk until dissolved.",
                "Add chia seeds, honey, and vanilla; stir vigorously.",
                "Refrigerate at least 4 hours or overnight.",
                "Stir well before eating; top with berries if desired."
            ),
            storageNotes = "Keeps refrigerated for 4 days.",
            metabolicResetScore = 5,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "green_detox_smoothie",
            name = "Anti-Inflammatory Green Smoothie",
            description = "A clean, bright smoothie with spinach, cucumber, ginger, and lemon — the classic anti-inflammatory green combination with added protein for satiety.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Protein Powder",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup fresh spinach",
                "½ cucumber (roughly chopped)",
                "1 tsp fresh ginger (grated)",
                "Juice of ½ lemon",
                "1 scoop vanilla protein powder",
                "1 cup unsweetened almond milk",
                "1 tbsp chia seeds",
                "3 ice cubes"
            ),
            calories = 260, proteinGrams = 26.0, carbGrams = 18.0, fatGrams = 6.0, fiberGrams = 6.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to a blender.",
                "Blend on high 60 seconds until completely smooth.",
                "Adjust ginger or lemon to taste; serve immediately."
            ),
            metabolicResetScore = 5,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 7,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "protein_shake",
            name = "Protein Shake",
            description = "A quick 30-second protein hit — one scoop of protein powder blended with almond milk and ice. Simple, fast, and reliably 28g of protein with under 160 calories.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Protein Powder",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = emptyList(),
            ingredients = listOf(
                "1 scoop vanilla or chocolate protein powder",
                "1 cup unsweetened almond milk",
                "3–4 ice cubes"
            ),
            calories = 160, proteinGrams = 28.0, carbGrams = 6.0, fatGrams = 3.0, fiberGrams = 1.0,
            prepMinutes = 2, cookMinutes = 0,
            instructions = listOf(
                "Add protein powder, almond milk, and ice to a shaker bottle or blender.",
                "Shake vigorously for 30 seconds or blend for 20 seconds.",
                "Serve immediately."
            ),
            metabolicResetScore = 5,
            antiInflammatoryScore = 3,
            insulinResistanceScore = 7,
            womensHealthScore = 5,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "cottage_cheese_bowl",
            name = "Cottage Cheese Protein Bowl",
            description = "Low-fat cottage cheese topped with mixed berries, turmeric, and pumpkin seeds. Berries replace pineapple to reduce glucose impact; turmeric adds curcumin; pumpkin seeds provide zinc and magnesium for women's hormone health.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "¾ cup low-fat cottage cheese (170g)",
                "½ cup mixed berries (blueberries and raspberries)",
                "1 tbsp pumpkin seeds",
                "¼ tsp turmeric",
                "¼ tsp cinnamon",
                "Pinch of black pepper"
            ),
            calories = 220, proteinGrams = 28.0, carbGrams = 14.0, fatGrams = 6.0, fiberGrams = 4.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Spoon cottage cheese into a bowl.",
                "Top with berries and pumpkin seeds.",
                "Sprinkle turmeric, cinnamon, and black pepper; stir lightly."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "tempeh_bites_snack",
            name = "Crispy Marinated Tempeh Bites",
            description = "Tempeh sliced thin, marinated in tamari, ginger, and turmeric, then pan-crisped and served on wilted spinach. Ginger and turmeric elevate the anti-inflammatory profile; spinach adds folate and iron for women's health.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset"),
            ingredients = listOf(
                "150g tempeh (sliced)",
                "1 tbsp tamari",
                "1 tsp fresh ginger (grated)",
                "¼ tsp turmeric",
                "½ tsp smoked paprika",
                "½ tsp garlic powder",
                "1 tsp olive oil",
                "1 tsp apple cider vinegar",
                "1 cup baby spinach (to serve)"
            ),
            calories = 235, proteinGrams = 29.0, carbGrams = 9.0, fatGrams = 10.0, fiberGrams = 4.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Mix tamari, ginger, turmeric, smoked paprika, garlic powder, and apple cider vinegar.",
                "Toss tempeh slices in marinade; rest 5 minutes.",
                "Heat olive oil in a non-stick pan over medium-high heat.",
                "Fry tempeh 4–5 minutes per side until crispy and golden.",
                "Wilt spinach in the residual pan heat for 30 seconds.",
                "Serve tempeh on a bed of spinach."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 7,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "soy_chunks_snack",
            name = "Spiced Soy Protein Chunks",
            description = "Rehydrated textured soy protein simmered in a bold cumin-coriander broth. 28g of protein and under 200 calories from one of the highest-protein plant foods available.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = emptyList(),
            ingredients = listOf(
                "50g soy chunks / textured soy protein (dry weight)",
                "1 cup water or vegetable broth",
                "½ tsp cumin",
                "½ tsp coriander powder",
                "¼ tsp turmeric",
                "¼ tsp chilli powder",
                "Salt to taste",
                "1 tsp lemon juice"
            ),
            calories = 200, proteinGrams = 28.0, carbGrams = 10.0, fatGrams = 2.0, fiberGrams = 2.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Boil soy chunks in broth 8 minutes until tender; drain and squeeze dry.",
                "Return to pan; add cumin, coriander, turmeric, and chilli powder.",
                "Cook on medium heat 3–4 minutes, stirring often.",
                "Finish with lemon juice and salt. Serve hot."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 8,
            womensHealthScore = 6,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "paneer_protein_cup",
            name = "Chilli Lime Paneer Protein Cup",
            description = "Fresh paneer cubes tossed with lime juice, chilli powder, and chaat masala — no cooking needed. 26g of protein in 200 calories; the fastest high-protein vegetarian snack.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = emptyList(),
            ingredients = listOf(
                "130g paneer (cubed)",
                "Juice of ½ lime",
                "¼ tsp chilli powder",
                "½ tsp chaat masala",
                "Salt to taste",
                "Fresh coriander (optional)"
            ),
            calories = 200, proteinGrams = 26.0, carbGrams = 4.0, fatGrams = 13.0, fiberGrams = 0.5,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Cube paneer into bite-sized pieces.",
                "Toss with lime juice, chilli powder, and chaat masala.",
                "Season with salt; garnish with coriander if using.",
                "Serve immediately — no cooking required."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "cottage_cheese_protein_pudding",
            name = "Cottage Cheese Protein Pudding",
            description = "Cottage cheese blended smooth with protein powder becomes a thick, spoonable pudding with 30g of protein in a snack under 250 calories. Chill overnight for an even firmer texture.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "½ cup low-fat cottage cheese",
                "1 scoop vanilla protein powder",
                "½ cup oat milk",
                "1 tsp honey",
                "¼ tsp vanilla extract"
            ),
            calories = 250, proteinGrams = 30.0, carbGrams = 26.0, fatGrams = 3.0, fiberGrams = 1.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Blend cottage cheese, protein powder, oat milk, honey, and vanilla until completely smooth.",
                "Pour into a bowl or jar; refrigerate 30 minutes for a thicker set.",
                "Top with berries or cinnamon before serving."
            ),
            storageNotes = "Keeps refrigerated for 3 days.",
            metabolicResetScore = 8,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "double_protein_yogurt_snack",
            name = "Double-Protein Greek Yogurt Snack Cup",
            description = "Greek yogurt plus a generous serving of protein powder for 32g of protein in one snack cup. Slower-digesting casein from yogurt and fast-digesting protein powder make this an ideal post-workout snack.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup non-fat Greek yogurt",
                "¾ scoop vanilla protein powder",
                "½ cup mixed berries",
                "1 tsp honey"
            ),
            calories = 250, proteinGrams = 32.0, carbGrams = 28.0, fatGrams = 2.0, fiberGrams = 3.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Stir protein powder into Greek yogurt until smooth.",
                "Top with berries and drizzle with honey.",
                "Serve immediately or refrigerate up to 2 hours."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "large_roasted_edamame",
            name = "Spiced Dry-Roasted Edamame",
            description = "A generous 1.5-cup serving of edamame dry-roasted in the oven with cumin and chilli for a crunchy snack with 26g of complete soy protein. The highest-protein fully vegan snack that requires no protein powder.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1½ cups shelled edamame (cooked)",
                "½ tsp cumin",
                "½ tsp chilli powder",
                "¼ tsp garlic powder",
                "½ tsp olive oil",
                "Pinch of sea salt",
                "Squeeze of lime juice"
            ),
            calories = 250, proteinGrams = 26.0, carbGrams = 20.0, fatGrams = 10.0, fiberGrams = 10.0,
            prepMinutes = 3, cookMinutes = 15,
            instructions = listOf(
                "Pat edamame dry; toss with olive oil, cumin, chilli powder, garlic powder, and salt.",
                "Spread on a baking sheet; roast at 200°C for 12–15 minutes until slightly crispy.",
                "Squeeze lime juice over the top; serve warm or at room temperature."
            ),
            metabolicResetScore = 5,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "big_soy_chunk_snack",
            name = "Double Soy Chunk Protein Snack",
            description = "65g of dry soy chunks — the largest soy serving in the snack section — simmered in a bold spiced broth for 34g of protein. At under 230 calories, it is the most protein-dense snack per calorie in the catalog.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = emptyList(),
            ingredients = listOf(
                "65g soy chunks (dry weight)",
                "1 cup vegetable broth",
                "½ tsp cumin",
                "½ tsp coriander powder",
                "¼ tsp turmeric",
                "¼ tsp chilli powder",
                "1 tsp lemon juice",
                "Salt to taste"
            ),
            calories = 230, proteinGrams = 34.0, carbGrams = 12.0, fatGrams = 2.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Boil soy chunks in broth 8 minutes until tender; drain and squeeze dry.",
                "Return to pan with cumin, coriander, turmeric, and chilli powder.",
                "Cook on medium heat 3–4 minutes, stirring often.",
                "Finish with lemon juice and salt. Serve hot."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 9,
            womensHealthScore = 6,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "protein_shake_plus",
            name = "High-Protein Shake Plus",
            description = "One scoop of protein powder blended with Greek yogurt and oat milk — a creamy shake that delivers 35g of protein in under 250 calories. The yogurt creates a thicker, creamier texture than almond milk alone.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Nut Free", "Soy Free", "Egg Free"),
            collection = emptyList(),
            ingredients = listOf(
                "1 scoop vanilla or chocolate protein powder",
                "½ cup non-fat Greek yogurt",
                "¾ cup oat milk",
                "3–4 ice cubes"
            ),
            calories = 240, proteinGrams = 35.0, carbGrams = 22.0, fatGrams = 3.0, fiberGrams = 1.0,
            prepMinutes = 2, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to a shaker bottle or blender.",
                "Shake or blend until smooth.",
                "Serve immediately."
            ),
            metabolicResetScore = 6,
            antiInflammatoryScore = 3,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "tempeh_tikka_snack",
            name = "Tandoori Tempeh Protein Bites",
            description = "Tempeh marinated in tandoori spices with ginger and turmeric, pan-crisped and served on wilted baby spinach. Ginger and turmeric amplify the anti-inflammatory compound density; spinach supplies folate and iron.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset"),
            ingredients = listOf(
                "170g tempeh (sliced)",
                "1 tbsp tamari",
                "1 tsp tandoori spice blend",
                "1 tsp fresh ginger (grated)",
                "¼ tsp turmeric",
                "½ tsp garlic powder",
                "½ tsp smoked paprika",
                "1 tsp olive oil",
                "Squeeze of lemon juice",
                "1 cup baby spinach (to serve)"
            ),
            calories = 285, proteinGrams = 33.0, carbGrams = 11.0, fatGrams = 12.0, fiberGrams = 4.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Mix tamari, tandoori spice, ginger, turmeric, garlic powder, and smoked paprika; toss with tempeh.",
                "Rest 5 minutes.",
                "Heat olive oil in a non-stick pan over medium-high heat.",
                "Fry tempeh 4–5 minutes per side until crispy and golden.",
                "Wilt spinach in residual pan heat for 30 seconds.",
                "Finish with a squeeze of lemon. Serve tempeh on spinach."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 7,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "soy_edamame_protein_bowl",
            name = "Soy Chunk & Edamame Protein Snack Bowl",
            description = "Warm edamame paired with a small serving of spiced soy chunks for a filling 33g protein snack. Two soy foods — one chewy, one soft — seasoned with sesame and chilli.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Egg Free"),
            collection = emptyList(),
            ingredients = listOf(
                "1 cup shelled edamame (cooked)",
                "30g soy chunks (dry weight)",
                "½ tsp sesame oil",
                "1 tsp low-sodium soy sauce",
                "¼ tsp chilli flakes",
                "½ tsp garlic powder",
                "1 tsp sesame seeds"
            ),
            calories = 280, proteinGrams = 33.0, carbGrams = 22.0, fatGrams = 10.0, fiberGrams = 10.0,
            prepMinutes = 10, cookMinutes = 8,
            instructions = listOf(
                "Soak soy chunks in boiling water 8 minutes; drain and squeeze dry.",
                "Heat sesame oil; toss soy chunks with soy sauce, chilli flakes, and garlic powder. Cook 3–4 minutes.",
                "Warm edamame; combine with soy chunks.",
                "Serve topped with sesame seeds."
            ),
            metabolicResetScore = 5,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "cinnamon_cottage_cheese_snack",
            name = "Cinnamon Cottage Cheese Snack Bowl",
            description = "Cottage cheese boosted with protein powder, mixed berries, turmeric, and pumpkin seeds. Berries replace banana to stabilise blood sugar; turmeric adds curcumin; pumpkin seeds supply zinc and magnesium for women's hormone health.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "¾ cup low-fat cottage cheese",
                "½ scoop vanilla protein powder",
                "½ cup mixed berries",
                "1 tbsp pumpkin seeds",
                "¼ tsp turmeric",
                "¼ tsp cinnamon",
                "1 tsp honey"
            ),
            calories = 215, proteinGrams = 31.0, carbGrams = 20.0, fatGrams = 7.0, fiberGrams = 4.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Stir protein powder and turmeric into cottage cheese until smooth.",
                "Top with berries and pumpkin seeds.",
                "Drizzle honey and sprinkle cinnamon. Serve immediately."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "high_protein_smoothie_snack",
            name = "High-Protein Berry Smoothie",
            description = "A thicker, higher-protein version of the standard protein shake: Greek yogurt replaces half the liquid for 30g of protein in a smooth drinkable snack that keeps hunger at bay for hours.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Nut Free", "Soy Free", "Egg Free"),
            collection = emptyList(),
            ingredients = listOf(
                "¾ cup non-fat Greek yogurt",
                "½ scoop vanilla protein powder",
                "½ cup frozen mixed berries",
                "½ cup oat milk",
                "1 tsp honey",
                "3 ice cubes"
            ),
            calories = 240, proteinGrams = 30.0, carbGrams = 32.0, fatGrams = 2.0, fiberGrams = 4.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to a blender.",
                "Blend on high 30 seconds until smooth.",
                "Serve immediately."
            ),
            metabolicResetScore = 6,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 6,
            womensHealthScore = 6,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "egg_white_spinach_booster",
            name = "Egg White & Spinach Scramble Booster",
            description = "Six egg whites and one whole egg scrambled with baby spinach and a pinch of turmeric deliver 28 g of fast-digesting complete protein in under 200 calories — the highest protein-to-calorie ratio booster in the plan.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Eggs",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "6 egg whites (180g)",
                "1 whole egg",
                "1 cup baby spinach (30g)",
                "¼ tsp turmeric",
                "¼ tsp black pepper",
                "Salt to taste",
                "½ tsp olive oil or cooking spray"
            ),
            calories = 195, proteinGrams = 28.0, carbGrams = 3.0, fatGrams = 7.0, fiberGrams = 1.0,
            prepMinutes = 3, cookMinutes = 5,
            instructions = listOf(
                "Whisk egg whites and whole egg with turmeric, pepper, and salt.",
                "Heat oil in a non-stick pan over medium heat; add spinach and wilt 1 minute.",
                "Pour egg mixture over spinach; fold gently until just set, about 3–4 minutes.",
                "Serve immediately — eggs continue cooking off the heat."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "sesame_tofu_booster",
            name = "Sesame Tofu Protein Booster Bowl",
            description = "300 g of extra-firm tofu pan-seared in sesame oil with tamari and garlic — maximising the protein density of soy while keeping total fat reasonable. A reliable 27 g booster with minimal carbs.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "300g extra-firm tofu (pressed)",
                "1 tbsp tamari",
                "1 tsp sesame oil",
                "2 cloves garlic (minced)",
                "½ tsp ginger (grated)",
                "¼ tsp chilli flakes",
                "1 tsp sesame seeds",
                "1 tsp avocado oil for frying"
            ),
            calories = 250, proteinGrams = 27.0, carbGrams = 6.0, fatGrams = 14.0, fiberGrams = 1.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Slice pressed tofu into 1 cm slabs; pat very dry.",
                "Heat avocado oil in a cast-iron or non-stick pan over high heat.",
                "Sear tofu 4–5 minutes per side until deep golden.",
                "Reduce heat to medium; add garlic, ginger, tamari, sesame oil, and chilli flakes.",
                "Toss 1 minute; top with sesame seeds and serve."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "skyr_protein_power_bowl",
            name = "High-Protein Skyr Power Bowl",
            description = "Thick Icelandic-style skyr (or strained Greek yogurt) layered with a half-scoop of unflavoured protein powder, topped with crushed flaxseed and cinnamon. The result is a cold, creamy booster with 32 g of slow-digesting protein.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "200g non-fat skyr or thick strained Greek yogurt",
                "½ scoop unflavoured whey or pea protein powder (15g, ~12g protein)",
                "1 tbsp ground flaxseed",
                "¼ tsp cinnamon",
                "1 tsp honey (optional)",
                "½ cup blueberries"
            ),
            calories = 255, proteinGrams = 32.0, carbGrams = 22.0, fatGrams = 3.0, fiberGrams = 3.0,
            prepMinutes = 4, cookMinutes = 0,
            instructions = listOf(
                "Whisk protein powder into skyr until fully dissolved and lump-free.",
                "Stir in ground flaxseed and cinnamon.",
                "Top with blueberries; drizzle honey if using.",
                "Serve immediately or refrigerate up to 4 hours."
            ),
            storageNotes = "Do not mix blueberries in if storing — add just before eating.",
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "paneer_tikka_bites_booster",
            name = "Paneer Tikka Protein Bites",
            description = "140 g of paneer marinated in a bold tandoori spice blend and grilled until charred at the edges — a straightforward booster delivering 26 g of slow-digesting casein protein with near-zero carbs.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "140g paneer (cubed, 2 cm pieces)",
                "3 tbsp low-fat yogurt",
                "1 tsp tandoori masala",
                "½ tsp cumin",
                "½ tsp smoked paprika",
                "¼ tsp turmeric",
                "1 tsp lemon juice",
                "Salt to taste"
            ),
            calories = 290, proteinGrams = 26.0, carbGrams = 5.0, fatGrams = 18.0, fiberGrams = 0.5,
            prepMinutes = 10, cookMinutes = 10,
            instructions = listOf(
                "Mix yogurt, tandoori masala, cumin, paprika, turmeric, lemon juice, and salt.",
                "Coat paneer cubes thoroughly; marinate at least 15 minutes (or overnight).",
                "Grill or pan-sear on high heat 3–4 minutes per side until charred and fragrant.",
                "Serve hot; no accompaniment needed."
            ),
            mealPrepNotes = "Marinate paneer the night before; grill fresh for best texture.",
            metabolicResetScore = 9,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "herbed_cottage_veggie_booster",
            name = "Herbed Cottage Cheese Veggie Bowl",
            description = "A full cup of low-fat cottage cheese seasoned with fresh herbs and black pepper, served alongside raw crunchy vegetables. Simple, filling, and delivering 28 g of slow-digesting casein protein with minimal prep.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup low-fat cottage cheese (226g)",
                "1 tbsp fresh chives or spring onion (chopped)",
                "1 tbsp fresh dill or coriander",
                "½ tsp black pepper",
                "¼ tsp garlic powder",
                "1 tsp lemon juice",
                "1 cup raw vegetables (cucumber, carrot, celery, capsicum)"
            ),
            calories = 230, proteinGrams = 28.0, carbGrams = 14.0, fatGrams = 4.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine cottage cheese with chives, dill, black pepper, garlic powder, and lemon juice.",
                "Stir until herbs are evenly distributed.",
                "Serve in a bowl alongside raw vegetables for dipping.",
                "Refrigerate any leftovers; consume within 24 hours."
            ),
            storageNotes = "Season fresh each time; the herb mixture can be pre-mixed into cottage cheese and stored 2 days.",
            metabolicResetScore = 9,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "soy_paneer_fusion_booster",
            name = "Soy Chunk & Paneer Protein Fusion",
            description = "Rehydrated soy chunks stir-fried with diced paneer in a quick masala base — two complementary protein sources in one bowl delivering an exceptional 34 g of combined protein with a rich, satisfying texture.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "50g dry soy chunks (rehydrated in hot water 10 min, ~150g wet)",
                "50g paneer (diced small)",
                "½ tsp cumin seeds",
                "1 small tomato (chopped)",
                "½ tsp coriander powder",
                "¼ tsp turmeric",
                "¼ tsp garam masala",
                "Salt to taste",
                "½ tsp oil"
            ),
            calories = 280, proteinGrams = 34.0, carbGrams = 16.0, fatGrams = 9.0, fiberGrams = 3.0,
            prepMinutes = 12, cookMinutes = 8,
            instructions = listOf(
                "Soak soy chunks in boiling water 10 minutes; drain and squeeze dry.",
                "Heat oil in a pan; add cumin seeds and let them crackle.",
                "Add tomato and cook 2 minutes until soft; add coriander, turmeric, and salt.",
                "Add soy chunks and paneer; stir-fry on high heat 4–5 minutes until lightly browned.",
                "Finish with garam masala; serve hot."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "greek_yogurt_hemp_booster",
            name = "Greek Yogurt Hemp Protein Booster",
            description = "Thick Greek yogurt blended with hemp protein powder, flaxseeds, and cinnamon — 28 g protein, rich in omega-3s and complete amino acids.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Dairy",
            restrictions = listOf("Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "¾ cup plain Greek yogurt (0%)",
                "2 tbsp hemp protein powder",
                "1 tbsp ground flaxseeds",
                "½ tsp cinnamon",
                "Stevia to taste",
                "¼ cup blueberries"
            ),
            calories = 265, proteinGrams = 28.0, carbGrams = 16.0, fatGrams = 7.0, fiberGrams = 4.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Stir hemp protein, flaxseeds, cinnamon, and stevia into Greek yogurt.",
                "Mix until smooth.",
                "Top with blueberries and serve."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "lentil_protein_shake_booster",
            name = "Red Lentil Protein Shake",
            description = "Cooked red lentils blended smooth with banana, soy milk, and vanilla protein — an unconventional 30 g plant-protein shake that is naturally thick and IR-friendly.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "¼ cup cooked red lentils (cooled)",
                "1 scoop vanilla pea or soy protein powder",
                "1 cup unsweetened soy milk",
                "½ small frozen banana",
                "½ tsp cinnamon",
                "Ice cubes"
            ),
            calories = 320, proteinGrams = 30.0, carbGrams = 30.0, fatGrams = 5.0, fiberGrams = 6.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to a blender.",
                "Blend on high until completely smooth.",
                "Serve over ice."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "tuna_cottage_power_bowl",
            name = "Tuna & Cottage Cheese Power Bowl",
            description = "Canned tuna mixed with low-fat cottage cheese, diced vegetables, and Dijon — no cooking, 35 g protein, and extremely low carb.",
            mealType = "Protein Booster",
            tags = listOf("Pescatarian", "High Protein", "Low Carb"),
            proteinSource = "Fish",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 can (120 g) tuna in water, drained",
                "½ cup low-fat cottage cheese",
                "¼ cup cucumber, diced",
                "¼ cup cherry tomatoes, halved",
                "1 tsp Dijon mustard",
                "1 tsp lemon juice",
                "Fresh parsley, salt, and pepper"
            ),
            calories = 275, proteinGrams = 35.0, carbGrams = 8.0, fatGrams = 5.0, fiberGrams = 1.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Mix tuna and cottage cheese until combined.",
                "Fold in cucumber, tomatoes, mustard, and lemon juice.",
                "Season and garnish with parsley."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "tofu_scramble_protein_cup",
            name = "High-Protein Tofu Scramble Cup",
            description = "Firm tofu scrambled with nutritional yeast, turmeric, and veggies — vegan egg-free alternative delivering 28 g protein and anti-inflammatory compounds.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "200 g firm tofu, pressed and crumbled",
                "2 tbsp nutritional yeast",
                "½ tsp turmeric",
                "½ tsp garlic powder",
                "¼ cup baby spinach",
                "¼ cup capsicum, diced",
                "1 tsp olive oil",
                "Salt and pepper"
            ),
            calories = 250, proteinGrams = 28.0, carbGrams = 10.0, fatGrams = 10.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Heat oil in a pan over medium heat.",
                "Add capsicum and cook 2 min.",
                "Add crumbled tofu, turmeric, garlic powder, and nutritional yeast.",
                "Cook 5–6 min, stirring often. Add spinach, season, and serve."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "egg_paneer_protein_bowl",
            name = "Egg & Paneer Protein Bowl",
            description = "Scrambled eggs combined with cubed paneer and sautéed spinach — a dual protein source booster with 30 g protein and minimal carbs.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein", "Low Carb"),
            proteinSource = "Eggs",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "3 large eggs",
                "60 g low-fat paneer, cubed",
                "1 cup baby spinach",
                "¼ tsp turmeric",
                "¼ tsp black pepper",
                "½ tsp olive oil",
                "Salt"
            ),
            calories = 310, proteinGrams = 30.0, carbGrams = 5.0, fatGrams = 17.0, fiberGrams = 1.0,
            prepMinutes = 5, cookMinutes = 8,
            instructions = listOf(
                "Whisk eggs with turmeric, salt, and pepper.",
                "Heat oil in pan; sauté paneer until lightly golden.",
                "Add spinach and wilt for 1 min.",
                "Pour in eggs and scramble to desired consistency."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "black_soybean_protein_bowl",
            name = "Black Soybean Protein Bowl",
            description = "Canned black soybeans tossed with avocado, tomato, and lime — one of the highest-protein legumes at 32 g per serve, naturally low-carb.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein", "Low Carb"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup canned black soybeans, drained",
                "¼ avocado, diced",
                "¼ cup cherry tomatoes, halved",
                "1 tbsp lime juice",
                "1 tbsp fresh coriander",
                "Salt, cumin, chilli flakes"
            ),
            calories = 295, proteinGrams = 32.0, carbGrams = 12.0, fatGrams = 11.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine black soybeans, avocado, and tomatoes.",
                "Dress with lime juice, cumin, salt, and chilli.",
                "Toss gently, top with coriander."
            ),
            metabolicResetScore = 10,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 10,
            womensHealthScore = 9,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "sardine_white_bean_smash",
            name = "Sardine & White Bean Protein Smash",
            description = "Canned sardines mashed with white beans, herbs, and lemon — Mediterranean protein powerhouse at 30 g protein, omega-3-rich, and blood-sugar stable.",
            mealType = "Protein Booster",
            tags = listOf("Pescatarian", "High Protein"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "1 can (120 g) sardines in water, drained",
                "½ cup canned white beans, drained",
                "1 tbsp lemon juice",
                "1 tsp olive oil",
                "1 tbsp fresh parsley",
                "½ tsp dried oregano",
                "Salt and pepper"
            ),
            calories = 285, proteinGrams = 30.0, carbGrams = 18.0, fatGrams = 7.0, fiberGrams = 5.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Mash white beans coarsely with a fork.",
                "Flake sardines and fold into beans.",
                "Stir in lemon juice, olive oil, parsley, and oregano.",
                "Season and serve."
            ),
            metabolicResetScore = 10,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "whey_oat_protein_porridge",
            name = "Whey-Boosted Oat Protein Porridge",
            description = "Steel-cut oats cooked and stirred through with whey protein and cinnamon — 28 g protein, beta-glucan fibre for glucose management, ideal post-workout booster.",
            mealType = "Protein Booster",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Dairy",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "½ cup steel-cut oats",
                "1 scoop unflavoured whey protein",
                "1 cup water",
                "¼ tsp cinnamon",
                "Stevia to taste",
                "¼ cup mixed berries"
            ),
            calories = 310, proteinGrams = 28.0, carbGrams = 32.0, fatGrams = 4.0, fiberGrams = 5.0,
            prepMinutes = 2, cookMinutes = 12,
            instructions = listOf(
                "Cook oats in water for 10–12 min.",
                "Remove from heat and stir in whey protein until dissolved.",
                "Add cinnamon and stevia; top with berries."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "pea_protein_green_smoothie_booster",
            name = "Pea Protein Green Smoothie Booster",
            description = "Spinach, cucumber, and pea protein blended with lemon — a dairy-free 27 g protein green booster that is anti-inflammatory and insulin-resistance friendly.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Pea Protein",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "2 scoops pea protein powder (unflavoured)",
                "1 cup baby spinach",
                "½ cup cucumber, chopped",
                "1 tsp lemon juice",
                "½ tsp spirulina (optional)",
                "1 cup cold water",
                "Ice cubes"
            ),
            calories = 220, proteinGrams = 27.0, carbGrams = 8.0, fatGrams = 3.0, fiberGrams = 2.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to blender.",
                "Blend until smooth.",
                "Serve over ice immediately."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "chickpea_tempeh_masala_booster",
            name = "Chickpea & Tempeh Masala Booster",
            description = "Tempeh and chickpeas simmered in a quick tomato-spice base — dual plant protein sources delivering 29 g protein with prebiotic fibre and anti-inflammatory spices.",
            mealType = "Protein Booster",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tempeh",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "100 g tempeh, cubed",
                "½ cup canned chickpeas, drained",
                "½ cup tomato, diced",
                "½ tsp cumin",
                "½ tsp coriander",
                "¼ tsp turmeric",
                "¼ tsp garam masala",
                "1 tsp olive oil",
                "Salt"
            ),
            calories = 305, proteinGrams = 29.0, carbGrams = 22.0, fatGrams = 10.0, fiberGrams = 7.0,
            prepMinutes = 5, cookMinutes = 12,
            instructions = listOf(
                "Heat oil; brown tempeh cubes 3–4 min.",
                "Add tomato and spices; cook 3 min.",
                "Add chickpeas and ¼ cup water; simmer 5 min.",
                "Adjust salt and serve."
            ),
            metabolicResetScore = 10,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),
    )
}
