package com.dailyhealthcoach.ui.recipes

object RecipeCatalog {

    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "greek_yogurt_bowl",
            name = "High Protein Greek Yogurt Bowl",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            ingredients = listOf(
                "1 cup non-fat Greek yogurt",
                "1 scoop protein powder",
                "½ cup mixed berries",
                "2 tbsp low-fat granola",
                "1 tbsp honey"
            ),
            calories = 380,
            proteinGrams = 38.0,
            carbGrams = 42.0,
            fatGrams = 4.0,
            fiberGrams = 3.0,
            prepMinutes = 5,
            instructions = listOf(
                "Add Greek yogurt to a bowl.",
                "Stir in protein powder until smooth.",
                "Top with mixed berries and granola.",
                "Drizzle honey and serve immediately."
            )
        ),

        Recipe(
            id = "protein_oats",
            name = "Protein Oats",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            ingredients = listOf(
                "½ cup rolled oats",
                "1 scoop protein powder",
                "1 cup milk or plant milk",
                "1 banana (sliced)",
                "1 tbsp almond butter",
                "Cinnamon to taste"
            ),
            calories = 440,
            proteinGrams = 32.0,
            carbGrams = 58.0,
            fatGrams = 9.0,
            fiberGrams = 5.0,
            prepMinutes = 10,
            instructions = listOf(
                "Cook oats in milk over medium heat, stirring often.",
                "Remove from heat; stir in protein powder.",
                "Top with sliced banana and almond butter.",
                "Sprinkle cinnamon and serve."
            )
        ),

        Recipe(
            id = "egg_white_scramble",
            name = "Egg White Veggie Scramble",
            mealType = "Breakfast",
            tags = listOf("High Protein", "Low Calorie"),
            ingredients = listOf(
                "6 egg whites",
                "½ cup fresh spinach",
                "¼ cup bell peppers (diced)",
                "¼ cup mushrooms (sliced)",
                "Salt, pepper, hot sauce to taste"
            ),
            calories = 180,
            proteinGrams = 26.0,
            carbGrams = 8.0,
            fatGrams = 1.0,
            fiberGrams = 2.0,
            prepMinutes = 10,
            instructions = listOf(
                "Sauté bell peppers and mushrooms over medium heat until soft.",
                "Add spinach; cook 1 minute until wilted.",
                "Pour in egg whites; scramble until just set.",
                "Season with salt, pepper, and hot sauce."
            )
        ),

        Recipe(
            id = "paneer_burrito_bowl",
            name = "Paneer Burrito Bowl",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            ingredients = listOf(
                "150g paneer (cubed)",
                "½ cup cooked brown rice",
                "½ cup black beans (rinsed)",
                "½ cup salsa",
                "1 tbsp olive oil",
                "Cumin, paprika, salt to taste"
            ),
            calories = 540,
            proteinGrams = 31.0,
            carbGrams = 52.0,
            fatGrams = 18.0,
            fiberGrams = 8.0,
            prepMinutes = 15,
            instructions = listOf(
                "Sauté paneer cubes in olive oil with cumin and paprika until golden.",
                "Warm brown rice and black beans separately.",
                "Build bowl: rice base, then beans, then paneer.",
                "Top with salsa and serve."
            )
        ),

        Recipe(
            id = "chickpea_salad",
            name = "Chickpea Protein Salad",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            ingredients = listOf(
                "1 cup cooked chickpeas",
                "1 cup cucumber (diced)",
                "1 cup cherry tomatoes (halved)",
                "¼ cup red onion (diced)",
                "2 tbsp olive oil",
                "Lemon juice, salt, pepper to taste"
            ),
            calories = 360,
            proteinGrams = 15.0,
            carbGrams = 40.0,
            fatGrams = 12.0,
            fiberGrams = 9.0,
            prepMinutes = 10,
            instructions = listOf(
                "Combine chickpeas, cucumber, tomatoes, and onion in a bowl.",
                "Whisk olive oil with lemon juice, salt, and pepper.",
                "Toss salad with dressing.",
                "Chill 5 minutes before serving."
            )
        ),

        Recipe(
            id = "tofu_stir_fry",
            name = "Tofu Stir Fry Bowl",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            ingredients = listOf(
                "200g firm tofu (pressed, cubed)",
                "1 cup mixed vegetables (broccoli, snap peas, carrots)",
                "½ cup cooked brown rice",
                "2 tbsp soy sauce (low sodium)",
                "1 tbsp sesame oil",
                "1 tsp garlic (minced)"
            ),
            calories = 420,
            proteinGrams = 24.0,
            carbGrams = 48.0,
            fatGrams = 12.0,
            fiberGrams = 6.0,
            prepMinutes = 20,
            instructions = listOf(
                "Press tofu dry with paper towels; cut into cubes.",
                "Stir-fry tofu in sesame oil until golden and crisp.",
                "Add garlic and vegetables; cook 5 minutes.",
                "Add soy sauce; stir to coat. Serve over brown rice."
            )
        ),

        Recipe(
            id = "lentil_rice_bowl",
            name = "Lentil Rice Bowl",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            ingredients = listOf(
                "½ cup red lentils (rinsed)",
                "½ cup basmati rice",
                "1½ cups vegetable broth",
                "1 tsp cumin",
                "1 tsp turmeric",
                "1 tbsp olive oil",
                "Salt and pepper to taste"
            ),
            calories = 460,
            proteinGrams = 20.0,
            carbGrams = 78.0,
            fatGrams = 6.0,
            fiberGrams = 12.0,
            prepMinutes = 25,
            instructions = listOf(
                "Rinse lentils and rice under cold water.",
                "Combine lentils, rice, broth, cumin, and turmeric in a pot.",
                "Bring to a boil, reduce heat, simmer 20 minutes until soft.",
                "Drizzle with olive oil, season, and serve."
            )
        ),

        Recipe(
            id = "protein_smoothie_bowl",
            name = "Premier Protein Smoothie Bowl",
            mealType = "Snack",
            tags = listOf("High Protein"),
            ingredients = listOf(
                "1 Premier Protein shake (any flavor)",
                "½ frozen banana",
                "½ cup frozen mixed berries",
                "1 tbsp chia seeds",
                "2 tbsp low-fat granola"
            ),
            calories = 320,
            proteinGrams = 34.0,
            carbGrams = 36.0,
            fatGrams = 5.0,
            fiberGrams = 4.0,
            prepMinutes = 5,
            instructions = listOf(
                "Blend protein shake with frozen banana and berries until thick.",
                "Pour into a bowl — should be thicker than a drink.",
                "Top with chia seeds and granola.",
                "Serve immediately."
            )
        ),

        Recipe(
            id = "cottage_cheese_toast",
            name = "Cottage Cheese Toast",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            ingredients = listOf(
                "2 slices whole grain bread",
                "½ cup low-fat cottage cheese",
                "Sliced cucumber or tomato",
                "Everything bagel seasoning or black pepper"
            ),
            calories = 280,
            proteinGrams = 24.0,
            carbGrams = 30.0,
            fatGrams = 4.0,
            fiberGrams = 3.0,
            prepMinutes = 5,
            instructions = listOf(
                "Toast bread slices to desired crispness.",
                "Spread cottage cheese generously on each slice.",
                "Top with sliced cucumber or tomato.",
                "Season with everything bagel seasoning or pepper."
            )
        ),

        Recipe(
            id = "protein_chia_pudding",
            name = "Protein Chia Pudding",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            ingredients = listOf(
                "3 tbsp chia seeds",
                "1 scoop protein powder",
                "1 cup unsweetened almond milk",
                "1 tbsp honey",
                "¼ tsp vanilla extract"
            ),
            calories = 300,
            proteinGrams = 25.0,
            carbGrams = 28.0,
            fatGrams = 9.0,
            fiberGrams = 10.0,
            prepMinutes = 5,
            instructions = listOf(
                "Mix chia seeds, protein powder, almond milk, honey, and vanilla in a jar.",
                "Stir vigorously to prevent clumping.",
                "Refrigerate at least 2 hours or overnight.",
                "Stir well before serving; add toppings if desired."
            )
        )
    )
}
