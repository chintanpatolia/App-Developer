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
            proteinSource = "",
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
            proteinSource = "",
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
            proteinSource = "",
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
    )
}
