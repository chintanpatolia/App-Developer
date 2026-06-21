package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object SnackRecipes2 {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "spiced_roasted_chickpeas",
            name = "Spiced Roasted Chickpeas",
            description = "Oven-roasted chickpeas with cumin, smoked paprika, and lime — crunchy, high-fibre, blood-sugar stable snack that travels well.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Legumes",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "1 can (400 g) chickpeas, drained and rinsed",
                "1 tsp olive oil",
                "½ tsp smoked paprika",
                "½ tsp cumin",
                "¼ tsp garlic powder",
                "Pinch cayenne",
                "Salt to taste",
                "1 tsp lime juice"
            ),
            calories = 210, proteinGrams = 14.0, carbGrams = 28.0, fatGrams = 4.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 30,
            instructions = listOf(
                "Preheat oven to 200 °C. Pat chickpeas completely dry.",
                "Toss with oil and spices.",
                "Spread on a baking tray and roast 25–30 min until crisp.",
                "Squeeze lime juice over hot chickpeas and cool before eating."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "skyr_cucumber_dip",
            name = "Skyr & Cucumber Protein Dip",
            description = "Thick Icelandic skyr blended with grated cucumber, dill, and garlic — a Greek-yogurt-style dip that packs 18 g protein per serving.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein", "Low Carb"),
            proteinSource = "Dairy",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "¾ cup plain skyr (or strained Greek yogurt)",
                "½ cup cucumber, grated and squeezed dry",
                "1 clove garlic, minced",
                "1 tbsp fresh dill",
                "1 tsp lemon juice",
                "Salt and pepper to taste",
                "Veggie sticks for dipping"
            ),
            calories = 155, proteinGrams = 18.0, carbGrams = 10.0, fatGrams = 2.0, fiberGrams = 1.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Grate cucumber and squeeze out excess liquid with a cloth.",
                "Mix skyr, cucumber, garlic, dill, and lemon juice.",
                "Season with salt and pepper.",
                "Serve chilled with veggie sticks."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "black_bean_protein_cup",
            name = "Black Bean & Lime Protein Cup",
            description = "Warm spiced black beans topped with salsa, avocado, and a dollop of Greek yogurt — 20 g protein in a satisfying, low-GI snack cup.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein", "Whole Foods"),
            proteinSource = "Legumes",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup canned black beans, drained",
                "¼ tsp cumin",
                "¼ tsp smoked paprika",
                "2 tbsp salsa",
                "¼ avocado, sliced",
                "2 tbsp low-fat Greek yogurt",
                "1 tbsp lime juice",
                "Fresh coriander"
            ),
            calories = 255, proteinGrams = 20.0, carbGrams = 26.0, fatGrams = 6.0, fiberGrams = 9.0,
            prepMinutes = 5, cookMinutes = 5,
            instructions = listOf(
                "Warm black beans with cumin and smoked paprika in a pan for 3–4 min.",
                "Transfer to a bowl. Top with salsa, avocado, and yogurt.",
                "Squeeze lime and scatter coriander."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "egg_white_veggie_muffin",
            name = "Egg White Veggie Muffin",
            description = "Baked egg-white cups loaded with spinach, capsicum, and feta — meal-prepped mini muffins with 16 g protein and almost zero carbs.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein", "Low Carb"),
            proteinSource = "Eggs",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "6 large egg whites",
                "¼ cup baby spinach, chopped",
                "¼ cup red capsicum, diced",
                "2 tbsp low-fat feta, crumbled",
                "Salt, pepper, mixed herbs"
            ),
            calories = 145, proteinGrams = 16.0, carbGrams = 4.0, fatGrams = 4.0, fiberGrams = 1.0,
            prepMinutes = 5, cookMinutes = 20,
            instructions = listOf(
                "Preheat oven to 180 °C. Grease a 6-hole muffin tin.",
                "Whisk egg whites with salt, pepper, and herbs.",
                "Divide veggies and feta among cups; pour egg whites over.",
                "Bake 18–20 min until set. Cool before removing."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "tuna_cucumber_boats",
            name = "Tuna-Stuffed Cucumber Boats",
            description = "Crisp cucumber halves filled with lemon-herb tuna and white beans — light, refreshing, and delivers 22 g protein without any cooking.",
            mealType = "Snack",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "1 large cucumber, halved lengthwise",
                "1 can (120 g) tuna in water, drained",
                "¼ cup canned white beans, mashed",
                "1 tsp Dijon mustard",
                "1 tsp lemon juice",
                "1 tbsp fresh parsley",
                "Salt and pepper"
            ),
            calories = 220, proteinGrams = 22.0, carbGrams = 12.0, fatGrams = 2.0, fiberGrams = 3.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Scoop seeds from cucumber halves to form boats.",
                "Mix tuna, mashed white beans, mustard, lemon juice, and parsley.",
                "Season and fill cucumber boats.",
                "Serve immediately."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "edamame_chilli_garlic",
            name = "Chilli-Garlic Edamame",
            description = "Steamed edamame pods tossed in garlic oil, chilli flakes, and soy — a 15-minute snack with 15 g plant protein and anti-inflammatory phytoestrogens.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "200 g frozen edamame in pods",
                "1 tsp sesame oil",
                "2 cloves garlic, minced",
                "½ tsp chilli flakes",
                "1 tsp low-sodium soy sauce",
                "1 tsp sesame seeds"
            ),
            calories = 195, proteinGrams = 15.0, carbGrams = 14.0, fatGrams = 7.0, fiberGrams = 5.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Steam or microwave edamame until tender.",
                "Heat sesame oil; sauté garlic and chilli 1 min.",
                "Toss edamame in garlic oil and soy sauce.",
                "Sprinkle sesame seeds and serve."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "cottage_cheese_bell_pepper",
            name = "Cottage Cheese-Stuffed Bell Peppers",
            description = "Mini sweet peppers filled with herb cottage cheese — colourful, crisp, zero cooking, and 18 g protein per serving.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein", "Low Carb"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "4 mini sweet bell peppers, halved and deseeded",
                "¾ cup low-fat cottage cheese",
                "1 tbsp chives, chopped",
                "½ tsp garlic powder",
                "Salt, pepper, and smoked paprika"
            ),
            calories = 160, proteinGrams = 18.0, carbGrams = 10.0, fatGrams = 3.0, fiberGrams = 2.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Mix cottage cheese with chives, garlic powder, salt, and pepper.",
                "Spoon filling into pepper halves.",
                "Dust with smoked paprika and serve."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "tempeh_sunflower_bar",
            name = "Tempeh & Sunflower Protein Bar",
            description = "Pan-fried tempeh crumbles mixed with sunflower seeds, tahini, and cinnamon, pressed into a snack bar — 17 g protein and prebiotic fibre.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Tempeh",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "100 g tempeh, crumbled",
                "2 tbsp sunflower seeds",
                "1 tbsp tahini",
                "1 tsp cinnamon",
                "1 tsp maple syrup",
                "Pinch salt"
            ),
            calories = 265, proteinGrams = 17.0, carbGrams = 14.0, fatGrams = 15.0, fiberGrams = 4.0,
            prepMinutes = 10, cookMinutes = 10,
            instructions = listOf(
                "Dry-pan fry tempeh crumbles until lightly golden.",
                "Mix with sunflower seeds, tahini, cinnamon, maple syrup, and salt.",
                "Press into a small lined container, refrigerate 1 hour, slice into bars."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "lentil_veggie_patty_snack",
            name = "Mini Lentil Veggie Patties",
            description = "Small air-fried red lentil patties spiced with coriander and turmeric — batch-cookable, 16 g protein per serve, low-GI snack.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup red lentils, soaked and drained",
                "¼ cup grated carrot",
                "2 tbsp grated zucchini",
                "1 clove garlic",
                "½ tsp turmeric",
                "½ tsp coriander powder",
                "Salt to taste"
            ),
            calories = 200, proteinGrams = 16.0, carbGrams = 28.0, fatGrams = 2.0, fiberGrams = 7.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Blend soaked lentils with garlic until coarse paste.",
                "Mix in grated veggies and spices.",
                "Shape into small patties and air-fry at 190 °C for 12–15 min, flipping once."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "pea_protein_energy_ball",
            name = "Pea Protein Energy Balls",
            description = "No-bake pea protein, oat, and almond butter balls dusted with cacao — 14 g protein per two-ball serve and stable blood sugar.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Pea Protein",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "2 tbsp pea protein powder",
                "3 tbsp rolled oats",
                "1 tbsp almond butter",
                "1 tsp raw cacao powder",
                "1 tbsp chia seeds",
                "1–2 tbsp water"
            ),
            calories = 210, proteinGrams = 14.0, carbGrams = 18.0, fatGrams = 7.0, fiberGrams = 4.0,
            prepMinutes = 10, cookMinutes = 0,
            instructions = listOf(
                "Combine all dry ingredients.",
                "Stir in almond butter and enough water to form a dough.",
                "Roll into 4 small balls and refrigerate 30 min.",
                "Dust with extra cacao before serving."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "tofu_veggie_skewer_snack",
            name = "Tofu & Veggie Snack Skewers",
            description = "Marinated extra-firm tofu and colourful veggies grilled on skewers — savoury, high protein, and anti-inflammatory.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "150 g extra-firm tofu, cubed",
                "½ red capsicum, chunked",
                "½ zucchini, sliced",
                "1 tsp soy sauce",
                "1 tsp olive oil",
                "½ tsp smoked paprika",
                "½ tsp garlic powder"
            ),
            calories = 175, proteinGrams = 14.0, carbGrams = 8.0, fatGrams = 8.0, fiberGrams = 2.0,
            prepMinutes = 10, cookMinutes = 12,
            instructions = listOf(
                "Marinate tofu in soy sauce, oil, paprika, and garlic 10 min.",
                "Thread tofu and veggies onto skewers.",
                "Grill or air-fry at 200 °C for 10–12 min, turning once."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "smoked_salmon_cucumber_rounds",
            name = "Smoked Salmon Cucumber Rounds",
            description = "Crisp cucumber slices topped with cream cheese, smoked salmon, and capers — elegant 5-minute no-cook snack with 20 g omega-3-rich protein.",
            mealType = "Snack",
            tags = listOf("High Protein", "Low Carb"),
            proteinSource = "Fish",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "1 large cucumber, sliced into rounds",
                "80 g smoked salmon",
                "3 tbsp low-fat cream cheese",
                "1 tbsp capers",
                "Fresh dill",
                "Lemon zest"
            ),
            calories = 195, proteinGrams = 20.0, carbGrams = 6.0, fatGrams = 8.0, fiberGrams = 1.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Spread a small amount of cream cheese on each cucumber round.",
                "Top with a piece of smoked salmon and a caper.",
                "Garnish with dill and lemon zest."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "high_protein_guacamole",
            name = "High-Protein Guacamole with Veggie Dippers",
            description = "Classic guacamole boosted with white beans for extra protein — served with colourful veggie sticks instead of chips for a blood-sugar-friendly dip.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Legumes",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "1 ripe avocado",
                "¼ cup canned white beans, mashed",
                "1 tbsp lime juice",
                "¼ cup tomato, diced",
                "2 tbsp red onion, finely chopped",
                "Fresh coriander",
                "Salt and cumin",
                "Celery and capsicum sticks"
            ),
            calories = 230, proteinGrams = 14.0, carbGrams = 20.0, fatGrams = 12.0, fiberGrams = 8.0,
            prepMinutes = 10, cookMinutes = 0,
            instructions = listOf(
                "Mash avocado and white beans together.",
                "Stir in lime juice, tomato, onion, coriander, salt, and cumin.",
                "Serve with veggie dippers."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "soy_milk_chia_cup",
            name = "Vanilla Soy Chia Cup",
            description = "Chia seeds soaked in unsweetened soy milk with vanilla and cinnamon — 16 g protein, high soluble fibre to blunt post-meal glucose spikes.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup unsweetened soy milk",
                "3 tbsp chia seeds",
                "½ tsp vanilla extract",
                "¼ tsp cinnamon",
                "Stevia to taste",
                "Fresh berries for topping"
            ),
            calories = 220, proteinGrams = 16.0, carbGrams = 18.0, fatGrams = 8.0, fiberGrams = 10.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Mix soy milk, chia seeds, vanilla, cinnamon, and stevia.",
                "Refrigerate at least 4 hours or overnight.",
                "Top with berries before serving."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "paneer_mint_chutney_wrap",
            name = "Paneer & Mint Chutney Lettuce Wrap",
            description = "Grilled paneer cubes and mint-coriander chutney wrapped in butter lettuce — zero-gluten, grain-free snack with 20 g protein.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein", "Low Carb"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "100 g low-fat paneer, cubed",
                "½ tsp cumin",
                "½ tsp chilli powder",
                "Salt",
                "4 butter lettuce leaves",
                "2 tbsp mint-coriander chutney",
                "Sliced cucumber and tomato"
            ),
            calories = 215, proteinGrams = 20.0, carbGrams = 7.0, fatGrams = 10.0, fiberGrams = 2.0,
            prepMinutes = 8, cookMinutes = 8,
            instructions = listOf(
                "Season paneer with cumin, chilli, and salt.",
                "Pan-fry or air-fry until golden, about 7–8 min.",
                "Fill lettuce leaves with paneer, chutney, cucumber, and tomato.",
                "Wrap and serve immediately."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "canned_sardine_crackers",
            name = "Sardine & Avocado Crispbread",
            description = "Omega-3-rich sardines mashed with avocado on rye crispbread — 10-minute anti-inflammatory snack providing 18 g protein and heart-healthy fats.",
            mealType = "Snack",
            tags = listOf("High Protein", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "1 can (90 g) sardines in water, drained",
                "¼ avocado",
                "1 tsp lemon juice",
                "¼ tsp black pepper",
                "2 rye crispbreads",
                "Fresh parsley"
            ),
            calories = 255, proteinGrams = 18.0, carbGrams = 16.0, fatGrams = 9.0, fiberGrams = 3.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Mash sardines and avocado with lemon juice and pepper.",
                "Spread generously on crispbreads.",
                "Top with parsley and serve."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "roasted_pumpkin_seeds_snack",
            name = "Spiced Roasted Pumpkin Seeds",
            description = "Pumpkin seeds roasted with smoked paprika and lime — zinc-rich, 14 g protein, and a satisfying crunch that keeps blood sugar steady.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Seeds",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup raw pumpkin seeds",
                "½ tsp olive oil",
                "½ tsp smoked paprika",
                "¼ tsp garlic powder",
                "Pinch cayenne",
                "Sea salt",
                "1 tsp lime juice"
            ),
            calories = 240, proteinGrams = 14.0, carbGrams = 7.0, fatGrams = 17.0, fiberGrams = 2.0,
            prepMinutes = 3, cookMinutes = 12,
            instructions = listOf(
                "Toss pumpkin seeds with oil and spices.",
                "Roast at 175 °C for 10–12 min, stirring once.",
                "Cool, squeeze lime, season with salt."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "miso_tofu_silken_bowl",
            name = "Miso Silken Tofu Bowl",
            description = "Cold silken tofu drizzled with miso dressing, sesame oil, spring onion, and bonito flakes — Japanese-inspired 5-minute snack, 16 g protein.",
            mealType = "Snack",
            tags = listOf("High Protein", "Low Carb"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "200 g silken tofu",
                "1 tsp white miso paste",
                "1 tsp low-sodium soy sauce",
                "½ tsp sesame oil",
                "1 spring onion, sliced",
                "1 tsp sesame seeds",
                "Pinch chilli flakes"
            ),
            calories = 165, proteinGrams = 16.0, carbGrams = 6.0, fatGrams = 8.0, fiberGrams = 1.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Drain silken tofu and place in a bowl.",
                "Whisk miso, soy sauce, and sesame oil; drizzle over tofu.",
                "Top with spring onion, sesame seeds, and chilli flakes."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "chickpea_spinach_salad_snack",
            name = "Chickpea & Spinach Power Snack",
            description = "Canned chickpeas tossed with baby spinach, lemon, olive oil, and za'atar — a nutrient-dense plant-protein snack ready in 5 minutes.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Legumes",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup canned chickpeas, drained",
                "1 cup baby spinach",
                "1 tsp olive oil",
                "1 tsp lemon juice",
                "½ tsp za'atar or mixed herbs",
                "Salt and pepper"
            ),
            calories = 185, proteinGrams = 12.0, carbGrams = 24.0, fatGrams = 5.0, fiberGrams = 7.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine chickpeas and spinach in a bowl.",
                "Drizzle with olive oil and lemon juice.",
                "Sprinkle za'atar, season, and toss to coat."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "high_protein_hummus_veggies",
            name = "Extra-Protein Hummus with Rainbow Veggies",
            description = "Hummus made with double chickpeas and added tahini for 16 g protein per serve — paired with crunchy rainbow veggie sticks for a blood-sugar-stable snack.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Legumes",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup canned chickpeas, drained",
                "2 tbsp tahini",
                "1 clove garlic",
                "2 tbsp lemon juice",
                "1 tbsp olive oil",
                "Salt and cumin",
                "Assorted veggie sticks (carrot, celery, capsicum)"
            ),
            calories = 265, proteinGrams = 16.0, carbGrams = 28.0, fatGrams = 10.0, fiberGrams = 8.0,
            prepMinutes = 10, cookMinutes = 0,
            instructions = listOf(
                "Blend chickpeas, tahini, garlic, lemon juice, and olive oil until smooth.",
                "Season with salt and cumin; add water if too thick.",
                "Serve with rainbow veggie sticks for dipping."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        )
    )
}
