package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object SnackRecipes {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "cottage_cheese_toast",
            name = "Cottage Cheese & Veggie Toast",
            description = "Creamy cottage cheese heaped onto whole-grain toast and topped with fresh cucumber or tomato. Simple, high in protein, and blood-sugar friendly.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "2 slices whole-grain bread",
                "½ cup low-fat cottage cheese",
                "½ cup cucumber (sliced)",
                "½ cup cherry tomatoes (halved)",
                "Everything bagel seasoning or black pepper"
            ),
            calories = 280, proteinGrams = 24.0, carbGrams = 30.0, fatGrams = 4.0, fiberGrams = 4.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Toast bread until golden and crisp.",
                "Spread cottage cheese generously on each slice.",
                "Top with cucumber and tomatoes.",
                "Season with everything bagel seasoning or pepper."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 6,
            womensHealthScore = 6,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "moong_sprout_chaat",
            name = "Moong Sprout Chaat",
            description = "Freshly sprouted mung beans tossed with cucumber, tomato, and tangy chaat masala. Sprouting increases protein bioavailability and dramatically reduces the glycaemic impact.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup sprouted moong beans",
                "½ cup cucumber (diced)",
                "½ cup tomato (diced)",
                "¼ cup red onion (diced)",
                "1 tbsp lime juice",
                "½ tsp chaat masala",
                "½ tsp roasted cumin powder",
                "2 tbsp fresh coriander (chopped)",
                "Salt to taste"
            ),
            calories = 200, proteinGrams = 14.0, carbGrams = 30.0, fatGrams = 2.0, fiberGrams = 8.0,
            prepMinutes = 10, cookMinutes = 0,
            instructions = listOf(
                "Combine sprouted moong with cucumber, tomato, and onion.",
                "Add lime juice, chaat masala, cumin powder, and salt.",
                "Toss well; garnish with coriander. Serve immediately."
            ),
            mealPrepNotes = "Sprout moong beans at home in 2 days: soak overnight, then rinse and drain twice daily.",
            metabolicResetScore = 9,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "edamame_snack",
            name = "Chilli Lime Spiced Edamame",
            description = "Warm shelled edamame seasoned with chilli flakes, lime zest, and a pinch of sea salt. Each cup delivers 17g of complete soy protein with a satisfying anti-inflammatory flavour punch.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup shelled edamame (cooked)",
                "½ tsp chilli flakes",
                "Zest and juice of ½ lime",
                "Pinch of sea salt",
                "½ tsp garlic powder"
            ),
            calories = 190, proteinGrams = 17.0, carbGrams = 14.0, fatGrams = 7.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Warm edamame in the microwave 1–2 minutes or in boiling water.",
                "Toss with chilli flakes, lime zest, lime juice, garlic powder, and salt.",
                "Serve immediately."
            ),
            metabolicResetScore = 5,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "paneer_skewers",
            name = "Tandoori Paneer Bites",
            description = "Bite-sized paneer marinated in tandoori yogurt spices and grilled until charred. 24g of protein in a snack that tastes like it came from a restaurant.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "150g paneer (cubed)",
                "3 tbsp low-fat yogurt",
                "1 tsp tandoori masala",
                "½ tsp cumin",
                "¼ tsp turmeric",
                "1 tsp lemon juice",
                "Salt to taste",
                "Mint chutney to serve"
            ),
            calories = 260, proteinGrams = 24.0, carbGrams = 6.0, fatGrams = 16.0, fiberGrams = 1.0,
            prepMinutes = 10, cookMinutes = 10,
            instructions = listOf(
                "Mix yogurt, tandoori masala, cumin, turmeric, lemon juice, and salt.",
                "Coat paneer cubes in marinade; rest 10 minutes.",
                "Grill or pan-sear on high heat 3–4 minutes per side until charred.",
                "Serve with mint chutney."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "walnut_energy_balls",
            name = "Walnut & Date Energy Balls",
            description = "No-bake energy balls made with walnuts, Medjool dates, chia seeds, and raw cacao. Walnuts top the nut charts for anti-inflammatory ALA omega-3; dates provide natural fibre and minerals.",
            mealType = "Snack",
            tags = listOf("Vegan"),
            proteinSource = "Nuts",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup walnuts",
                "4 Medjool dates (pitted)",
                "1 tbsp chia seeds",
                "1 tbsp raw cacao powder",
                "Pinch of sea salt",
                "Shredded coconut for rolling (optional)"
            ),
            calories = 240, proteinGrams = 6.0, carbGrams = 28.0, fatGrams = 14.0, fiberGrams = 4.0,
            prepMinutes = 10, cookMinutes = 0,
            instructions = listOf(
                "Pulse walnuts in a food processor until coarsely ground.",
                "Add dates, chia seeds, cacao, and salt; process until the mixture sticks together.",
                "Roll into 6 balls; coat in shredded coconut if using.",
                "Refrigerate 20 minutes before eating."
            ),
            storageNotes = "Keeps refrigerated for 1 week; freezes for 1 month.",
            mealPrepNotes = "Make a double batch and freeze half.",
            metabolicResetScore = 3,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 4,
            womensHealthScore = 6,
            glucoseImpactScore = 6
        ),

        Recipe(
            id = "turmeric_almonds",
            name = "Turmeric Spiced Almonds",
            description = "Dry-roasted almonds coated in turmeric, black pepper, and a touch of sea salt. The black pepper activates curcumin absorption — turning a simple snack into an anti-inflammatory one.",
            mealType = "Snack",
            tags = listOf("Vegan"),
            proteinSource = "Nuts",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "¼ cup raw almonds",
                "½ tsp turmeric",
                "¼ tsp black pepper",
                "Pinch of sea salt",
                "½ tsp olive oil"
            ),
            calories = 220, proteinGrams = 8.0, carbGrams = 6.0, fatGrams = 18.0, fiberGrams = 3.0,
            prepMinutes = 2, cookMinutes = 8,
            instructions = listOf(
                "Toss almonds with olive oil, turmeric, black pepper, and salt.",
                "Dry-roast in a pan over medium heat 6–8 minutes, stirring often, until fragrant.",
                "Cool completely before eating."
            ),
            storageNotes = "Keeps in an airtight jar for 2 weeks.",
            metabolicResetScore = 4,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 6,
            womensHealthScore = 6,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "berry_protein_parfait",
            name = "Mixed Berry Protein Parfait",
            description = "Layered Greek yogurt, mixed berries, and crushed walnuts — anti-inflammatory antioxidants from the berries meet metabolic-reset protein from the yogurt in every bite.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "¾ cup non-fat Greek yogurt",
                "½ cup mixed berries (blueberry, raspberry, strawberry)",
                "2 tbsp walnuts (crushed)",
                "1 tsp honey",
                "¼ tsp cinnamon"
            ),
            calories = 290, proteinGrams = 20.0, carbGrams = 28.0, fatGrams = 10.0, fiberGrams = 4.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Layer half the yogurt in a glass.",
                "Add half the berries and walnuts.",
                "Repeat layers; drizzle honey and sprinkle cinnamon.",
                "Serve immediately or refrigerate up to 2 hours."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "greek_yogurt_berry_bowl",
            name = "Greek Yogurt Berry Bowl",
            description = "A simple bowl of thick non-fat Greek yogurt topped with fresh mixed berries. 22g of slow-digesting protein, naturally sweet, and under 220 calories.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup non-fat Greek yogurt (170g)",
                "½ cup mixed berries (blueberries, strawberries, raspberries)",
                "1 tsp honey (optional)"
            ),
            calories = 210, proteinGrams = 22.0, carbGrams = 24.0, fatGrams = 1.0, fiberGrams = 3.0,
            prepMinutes = 2, cookMinutes = 0,
            instructions = listOf(
                "Spoon Greek yogurt into a bowl.",
                "Top with mixed berries.",
                "Drizzle honey if desired. Serve immediately."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "protein_oats_mini",
            name = "Protein Oats Mini Bowl",
            description = "A half-portion of protein oatmeal — the perfect bridge snack between meals. 20g of protein from a half-scoop of powder stirred into warm oats, under 200 calories.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Protein Powder",
            restrictions = listOf("Dairy Free", "Soy Free", "Egg Free"),
            collection = emptyList(),
            ingredients = listOf(
                "¼ cup rolled oats",
                "½ scoop vanilla protein powder",
                "½ cup unsweetened almond milk",
                "1 tsp honey",
                "¼ tsp cinnamon"
            ),
            calories = 200, proteinGrams = 20.0, carbGrams = 26.0, fatGrams = 3.0, fiberGrams = 3.0,
            prepMinutes = 1, cookMinutes = 5,
            instructions = listOf(
                "Cook oats with almond milk on stovetop or microwave 3–4 minutes.",
                "Stir in protein powder until smooth.",
                "Top with honey and cinnamon. Serve warm."
            ),
            metabolicResetScore = 5,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 5,
            womensHealthScore = 5,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "tofu_snack_bowl",
            name = "Quick Sesame Tofu Bowl",
            description = "Firm tofu cubed and pan-fried in sesame oil with soy sauce and chilli. Ready in 10 minutes with 22g of complete plant protein and under 200 calories.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Egg Free"),
            collection = emptyList(),
            ingredients = listOf(
                "150g firm tofu (cubed)",
                "1 tsp sesame oil",
                "1 tsp soy sauce",
                "½ tsp garlic powder",
                "¼ tsp chilli flakes",
                "1 tsp sesame seeds"
            ),
            calories = 200, proteinGrams = 22.0, carbGrams = 4.0, fatGrams = 11.0, fiberGrams = 1.0,
            prepMinutes = 3, cookMinutes = 7,
            instructions = listOf(
                "Press tofu dry with a kitchen towel; cube into bite-sized pieces.",
                "Heat sesame oil in a non-stick pan over high heat.",
                "Add tofu; fry 3–4 minutes per side until golden.",
                "Toss with soy sauce, garlic powder, and chilli flakes.",
                "Finish with sesame seeds; serve warm."
            ),
            metabolicResetScore = 4,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "warm_miso_edamame",
            name = "Warm Miso Edamame Cup",
            description = "Shelled edamame tossed in a warm miso-ginger glaze — a five-minute anti-inflammatory snack with 18 g of complete soy protein and 7 g of fibre. White miso adds probiotic depth without overpowering the beans.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup shelled edamame (cooked, 155g)",
                "1 tsp white miso paste",
                "½ tsp fresh ginger (grated)",
                "½ tsp sesame oil",
                "1 tsp tamari or coconut aminos",
                "½ tsp sesame seeds",
                "Pinch of chilli flakes"
            ),
            calories = 210, proteinGrams = 18.0, carbGrams = 16.0, fatGrams = 9.0, fiberGrams = 7.0,
            prepMinutes = 3, cookMinutes = 3,
            instructions = listOf(
                "Warm edamame in a small pan over medium heat with 2 tbsp water for 2 minutes.",
                "Dissolve miso in 1 tbsp warm water; add to pan along with ginger, sesame oil, and tamari.",
                "Toss for 60 seconds until the glaze coats the beans evenly.",
                "Transfer to a bowl, top with sesame seeds and chilli flakes."
            ),
            metabolicResetScore = 6,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "paneer_cucumber_rolls",
            name = "Paneer & Cucumber Mint Rolls",
            description = "Cool cucumber rounds loaded with soft paneer seasoned with chaat masala and fresh mint. No cooking, 22 g of protein, and an insulinResistanceScore of 9 — the fastest high-protein snack in the plan.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "120g paneer (crumbled or thinly sliced)",
                "1 large cucumber (sliced into 10 rounds, ~150g)",
                "2 tbsp green mint chutney",
                "½ tsp chaat masala",
                "1 tsp lemon juice",
                "1 tbsp fresh coriander (chopped)",
                "Black salt to taste"
            ),
            calories = 195, proteinGrams = 22.0, carbGrams = 8.0, fatGrams = 9.0, fiberGrams = 2.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Crumble or dice paneer; season with chaat masala, lemon juice, and black salt.",
                "Spread ½ tsp mint chutney on each cucumber round.",
                "Top with seasoned paneer and garnish with fresh coriander.",
                "Serve immediately; refrigerate paneer separately if prepping ahead."
            ),
            mealPrepNotes = "Season paneer up to 24 h ahead; assemble just before eating to keep cucumber crisp.",
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "masala_moong_cup",
            name = "Spiced Masala Moong Cup",
            description = "A warm cup of cooked whole moong (green gram) tempered with cumin, turmeric, and lime — a deeply nourishing insulin-resistance snack rich in resistant starch, fibre, and plant protein.",
            mealType = "Snack",
            tags = listOf("Vegan", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup whole green moong (cooked, ~200g)",
                "½ tsp cumin seeds",
                "¼ tsp turmeric",
                "½ tsp coriander powder",
                "1 tbsp lemon juice",
                "½ tsp ghee or coconut oil",
                "Salt to taste",
                "2 tbsp fresh coriander (chopped)"
            ),
            calories = 190, proteinGrams = 16.0, carbGrams = 30.0, fatGrams = 3.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 3,
            instructions = listOf(
                "Heat ghee or coconut oil in a small pan; add cumin seeds and let them splutter.",
                "Add turmeric and coriander powder; stir 20 seconds.",
                "Add cooked moong and salt; toss to coat with the tempering.",
                "Remove from heat, squeeze lemon juice, and garnish with coriander."
            ),
            mealPrepNotes = "Cook a large batch of moong; store refrigerated 4 days. Temper fresh portions each time.",
            metabolicResetScore = 9,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "tempeh_chaat_bites",
            name = "Tempeh Chaat Bowl",
            description = "Cubed tempeh served chaat-style with diced tomato, onion, coriander, and tangy spices — 20 g of protein with a satisfying crunch and the probiotic benefit of fermented soy.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "150g tempeh (cubed, raw weight)",
                "¼ cup tomato (finely diced)",
                "¼ cup red onion (finely diced)",
                "1 tbsp lemon juice",
                "½ tsp chaat masala",
                "½ tsp cumin powder",
                "¼ tsp chilli powder",
                "2 tbsp fresh coriander (chopped)"
            ),
            calories = 215, proteinGrams = 20.0, carbGrams = 14.0, fatGrams = 10.0, fiberGrams = 4.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Steam tempeh cubes 5 minutes to mellow the bitterness, then cool slightly.",
                "Combine tomato, onion, lemon juice, chaat masala, cumin, and chilli in a bowl.",
                "Add tempeh and toss to coat evenly.",
                "Garnish with coriander; serve at room temperature."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "tzatziki_protein_bowl",
            name = "High-Protein Tzatziki Bowl",
            description = "Thick Greek yogurt blended with grated cucumber, garlic, dill, and lemon — served as a scooping dip with crisp raw vegetables. At 24 g of protein and under 200 cal, it is one of the highest protein-to-calorie snacks in the plan.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "¾ cup non-fat Greek yogurt (170g)",
                "½ cup cucumber (grated and squeezed dry, ~80g)",
                "1 clove garlic (minced)",
                "1 tbsp fresh dill (or 1 tsp dried)",
                "1 tsp lemon juice",
                "½ tsp olive oil",
                "Salt and pepper to taste",
                "1 cup raw vegetables to serve (carrot, celery, capsicum)"
            ),
            calories = 190, proteinGrams = 24.0, carbGrams = 16.0, fatGrams = 4.0, fiberGrams = 3.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Grate cucumber and squeeze firmly in a clean towel to remove excess water.",
                "Combine yogurt, cucumber, garlic, dill, lemon juice, and olive oil; season with salt and pepper.",
                "Rest in the fridge 10 minutes for flavours to meld.",
                "Serve with raw vegetables for dipping."
            ),
            storageNotes = "Keeps refrigerated up to 3 days; stir before serving.",
            metabolicResetScore = 9,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "chickpea_paneer_cup",
            name = "Crispy Chickpea & Paneer Cup",
            description = "Pan-seared paneer cubes paired with oven-roasted chickpeas, both seasoned with smoked paprika and cumin — a textural snack with dual protein sources delivering 21 g from two complementary plant foods.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "75g paneer (cubed)",
                "50g canned chickpeas (drained, rinsed)",
                "½ tsp smoked paprika",
                "½ tsp cumin powder",
                "¼ tsp garlic powder",
                "½ tsp olive oil",
                "Salt to taste",
                "Fresh coriander to garnish"
            ),
            calories = 265, proteinGrams = 21.0, carbGrams = 17.0, fatGrams = 12.0, fiberGrams = 5.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Pat chickpeas dry; toss with half the oil, paprika, cumin, and garlic powder.",
                "Roast chickpeas in a 200°C oven for 8–10 minutes until crispy.",
                "Meanwhile, pan-sear paneer cubes in remaining oil over medium-high heat 2–3 minutes each side.",
                "Season paneer with remaining spices; combine with chickpeas and serve warm."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "ginger_garlic_tofu_bites",
            name = "Ginger Garlic Crispy Tofu Bites",
            description = "Extra-firm tofu pan-fried in a fragrant ginger-garlic tamari glaze until golden on every side. 22 g of complete plant protein in a bold, satisfying snack that is ready in 12 minutes.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "180g extra-firm tofu (pressed and cubed)",
                "1 tsp fresh ginger (grated)",
                "2 cloves garlic (minced)",
                "1 tbsp tamari or reduced-sodium soy sauce",
                "1 tsp sesame oil",
                "¼ tsp chilli flakes",
                "1 tsp sesame seeds",
                "1 tsp avocado or coconut oil for frying"
            ),
            calories = 200, proteinGrams = 22.0, carbGrams = 5.0, fatGrams = 11.0, fiberGrams = 1.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Press tofu between paper towels for 5 minutes; cube into 2 cm pieces.",
                "Heat oil in a non-stick pan over high heat; add tofu and fry 3–4 minutes per side until golden.",
                "Reduce to medium; add ginger, garlic, tamari, sesame oil, and chilli flakes.",
                "Toss 1–2 minutes until the glaze coats the tofu; top with sesame seeds and serve."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "mini_moong_chilla_snack",
            name = "Mini Moong Dal Chilla",
            description = "Petite savoury pancakes made from blended yellow moong dal batter with grated zucchini and cumin — gluten-free, dairy-free, and a legitimate 18 g protein snack straight from Indian home kitchens.",
            mealType = "Snack",
            tags = listOf("Vegan", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "½ cup yellow moong dal (soaked 4 h, blended with ¼ cup water)",
                "¼ cup zucchini (grated)",
                "½ tsp cumin seeds",
                "¼ tsp turmeric",
                "¼ tsp green chilli (minced, optional)",
                "Salt to taste",
                "Oil spray",
                "2 tbsp green chutney to serve"
            ),
            calories = 220, proteinGrams = 18.0, carbGrams = 32.0, fatGrams = 3.0, fiberGrams = 6.0,
            prepMinutes = 10, cookMinutes = 10,
            instructions = listOf(
                "Blend soaked moong dal with water to a smooth, slightly thick batter.",
                "Fold in grated zucchini, cumin, turmeric, chilli, and salt.",
                "Heat a non-stick pan over medium heat; spray with oil.",
                "Pour small rounds (about 2 tbsp each), cook 2–3 minutes per side until set and lightly golden.",
                "Serve hot with green chutney."
            ),
            mealPrepNotes = "Soak moong dal the night before; batter keeps refrigerated 24 h.",
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "hemp_chia_protein_pudding",
            name = "Hemp Seed & Chia Protein Pudding",
            description = "A no-cook pudding set overnight with hemp hearts, chia seeds, and unsweetened almond milk — naturally creamy, high in omega-3 fatty acids, and delivering 15 g of plant protein with 9 g of fibre.",
            mealType = "Snack",
            tags = listOf("Vegan", "Whole Foods"),
            proteinSource = "Seeds",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "3 tbsp hemp hearts (30g)",
                "2 tbsp chia seeds (20g)",
                "1 cup unsweetened almond milk (240ml)",
                "½ tsp vanilla extract",
                "1 tsp maple syrup",
                "¼ tsp cinnamon",
                "¼ cup mixed berries (topping)"
            ),
            calories = 290, proteinGrams = 15.0, carbGrams = 20.0, fatGrams = 18.0, fiberGrams = 9.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Whisk hemp hearts, chia seeds, almond milk, vanilla, maple syrup, and cinnamon in a jar.",
                "Stir again after 10 minutes to break up any chia clumps.",
                "Cover and refrigerate overnight or at least 4 hours until set.",
                "Top with berries before serving."
            ),
            storageNotes = "Keeps refrigerated up to 4 days; stir before eating.",
            mealPrepNotes = "Make a 3-day batch in individual jars for an instant grab-and-go snack.",
            metabolicResetScore = 7,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 7,
            womensHealthScore = 9,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "masala_cottage_capsicum",
            name = "Masala Cottage Cheese Stuffed Capsicum",
            description = "Halved bell peppers filled with spiced low-fat cottage cheese — no cooking, 22 g of protein, and a glucoseImpactScore of 2 making it one of the most blood-sugar-friendly snacks in the plan.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "¾ cup low-fat cottage cheese (170g)",
                "2 medium capsicums (halved, seeds removed)",
                "½ tsp cumin powder",
                "¼ tsp chilli flakes",
                "¼ tsp black pepper",
                "1 tsp lemon juice",
                "1 tbsp fresh coriander (chopped)"
            ),
            calories = 185, proteinGrams = 22.0, carbGrams = 14.0, fatGrams = 4.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Season cottage cheese with cumin, chilli flakes, black pepper, and lemon juice; stir to combine.",
                "Fill each capsicum half generously with seasoned cottage cheese.",
                "Garnish with fresh coriander.",
                "Serve immediately or refrigerate up to 2 hours before eating."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),
    )
}
