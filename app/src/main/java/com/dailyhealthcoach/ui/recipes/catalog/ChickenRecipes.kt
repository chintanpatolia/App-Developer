package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object ChickenRecipes {
    val ALL: List<Recipe> = listOf(

        // ── Breakfast ────────────────────────────────────────────────────────

        Recipe(
            id = "chicken_keema_egg_scramble",
            name = "Chicken Keema & Egg White Scramble",
            description = "Spiced minced chicken cooked down with turmeric, ginger, and cumin then folded through fluffy egg whites and wilted spinach — a fast, very high-protein Indian-style breakfast that keeps glucose flat for hours.",
            mealType = "Breakfast",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Chicken",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free"),
            collection = listOf("Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "100g minced chicken (lean)",
                "3 egg whites",
                "1 cup baby spinach",
                "1 clove garlic, minced",
                "1 tsp fresh ginger, grated",
                "½ tsp ground cumin",
                "½ tsp ground turmeric",
                "½ tsp ground coriander",
                "1 tsp olive oil",
                "Salt and black pepper to taste",
                "Fresh coriander to serve"
            ),
            calories = 280, proteinGrams = 38.0, carbGrams = 6.0, fatGrams = 11.0, fiberGrams = 2.0,
            prepMinutes = 8, cookMinutes = 10,
            instructions = listOf(
                "Heat olive oil in a non-stick pan over medium-high heat.",
                "Add garlic and ginger; sauté 1 minute until fragrant.",
                "Add minced chicken and break it up with a spoon; cook for 4–5 minutes until cooked through.",
                "Stir in cumin, turmeric, and coriander; cook 1 minute.",
                "Add spinach and stir until wilted, about 1 minute.",
                "Pour egg whites over the chicken mixture; stir gently until just set.",
                "Season with salt and pepper; serve garnished with fresh coriander."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "tandoori_chicken_breakfast_bowl",
            name = "Tandoori Chicken Breakfast Bowl",
            description = "Marinated tandoori chicken strips roasted alongside capsicum, zucchini, and cherry tomatoes — a boldly spiced, dairy-free breakfast bowl that delivers lean protein and anti-inflammatory curcumin in under 20 minutes.",
            mealType = "Breakfast",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Chicken",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "150g chicken breast, sliced into strips",
                "1 tsp ground turmeric",
                "1 tsp paprika",
                "½ tsp ground cumin",
                "½ tsp ground coriander",
                "1 tsp lemon juice",
                "1 tsp olive oil",
                "½ red capsicum, sliced",
                "1 small zucchini, sliced into rounds",
                "½ cup cherry tomatoes",
                "Salt and black pepper to taste",
                "Fresh coriander to serve"
            ),
            calories = 295, proteinGrams = 36.0, carbGrams = 10.0, fatGrams = 11.0, fiberGrams = 3.0,
            prepMinutes = 8, cookMinutes = 15,
            instructions = listOf(
                "Preheat oven to 200°C. Line a baking tray.",
                "Toss chicken strips with turmeric, paprika, cumin, coriander, lemon juice, olive oil, salt, and pepper.",
                "Arrange chicken on one side of the tray; place capsicum, zucchini, and cherry tomatoes on the other.",
                "Roast for 14–16 minutes until chicken is cooked through and vegetables are caramelised.",
                "Serve in a bowl garnished with fresh coriander."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        // ── Lunch ────────────────────────────────────────────────────────────

        Recipe(
            id = "turmeric_chicken_quinoa_bowl",
            name = "Turmeric Grilled Chicken Quinoa Bowl",
            description = "Turmeric-marinated grilled chicken breast sliced over a base of quinoa, kale, cucumber, and cherry tomatoes with a lemon-tahini dressing — a balanced, high-protein lunch combining complete amino acids with fiber and curcumin.",
            mealType = "Lunch",
            tags = listOf("High Protein", "Whole Foods"),
            proteinSource = "Chicken",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "150g chicken breast",
                "1 tsp ground turmeric",
                "1 tsp lemon juice",
                "1 tsp olive oil",
                "¾ cup cooked quinoa",
                "1 cup kale, finely shredded",
                "½ cucumber, diced",
                "½ cup cherry tomatoes, halved",
                "1 tbsp tahini",
                "1 tbsp lemon juice",
                "Salt and black pepper to taste"
            ),
            calories = 420, proteinGrams = 40.0, carbGrams = 38.0, fatGrams = 12.0, fiberGrams = 6.0,
            prepMinutes = 10, cookMinutes = 14,
            instructions = listOf(
                "Marinate chicken breast in turmeric, lemon juice, olive oil, salt, and pepper for 5 minutes.",
                "Grill or pan-sear chicken for 6–7 minutes per side until cooked through; rest 3 minutes, then slice.",
                "Whisk tahini, lemon juice, and 2 tbsp water into a thin dressing.",
                "Massage kale with a pinch of salt until softened.",
                "Arrange quinoa and kale in a bowl; top with cucumber and cherry tomatoes.",
                "Place sliced chicken on top and drizzle with tahini dressing."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "ginger_chicken_lettuce_wraps",
            name = "Ginger Chicken Lettuce Wraps",
            description = "Lean ground chicken stir-fried with fresh ginger, garlic, and tamari then served in crisp butter lettuce cups with spring onion and sesame — a low-carb, high-protein lunch that satisfies without spiking blood sugar.",
            mealType = "Lunch",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Chicken",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "160g lean ground chicken",
                "6 large butter lettuce leaves",
                "2 spring onions, finely sliced",
                "1 tsp fresh ginger, grated",
                "1 clove garlic, minced",
                "1 tbsp tamari (gluten-free soy sauce)",
                "1 tsp sesame oil",
                "1 tsp olive oil",
                "1 tsp sesame seeds",
                "½ red chilli, finely sliced (optional)"
            ),
            calories = 310, proteinGrams = 38.0, carbGrams = 8.0, fatGrams = 13.0, fiberGrams = 2.0,
            prepMinutes = 8, cookMinutes = 10,
            instructions = listOf(
                "Heat olive oil in a wok or pan over high heat.",
                "Add garlic and ginger; stir-fry 30 seconds.",
                "Add ground chicken and break it up; cook for 5–6 minutes until cooked through.",
                "Add tamari, sesame oil, and most of the spring onion; toss to combine.",
                "Spoon the chicken mixture into lettuce cups.",
                "Garnish with remaining spring onion, sesame seeds, and chilli if using."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "lemon_herb_chicken_roasted_veg",
            name = "Lemon Herb Chicken with Roasted Vegetables",
            description = "Chicken breast baked with lemon, garlic, rosemary, and thyme alongside broccoli and asparagus — a lean, clean-protein lunch with sulforaphane from broccoli and anti-inflammatory herbs that keeps the meal completely whole-food.",
            mealType = "Lunch",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Chicken",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "170g chicken breast",
                "2 cups broccoli florets",
                "10 asparagus spears",
                "1 tbsp lemon juice",
                "1 tsp lemon zest",
                "1 clove garlic, minced",
                "½ tsp dried rosemary",
                "½ tsp dried thyme",
                "1 tsp olive oil",
                "Salt and black pepper to taste"
            ),
            calories = 340, proteinGrams = 42.0, carbGrams = 14.0, fatGrams = 11.0, fiberGrams = 5.0,
            prepMinutes = 8, cookMinutes = 22,
            instructions = listOf(
                "Preheat oven to 200°C. Line a baking tray.",
                "Rub chicken with lemon juice, lemon zest, garlic, rosemary, thyme, olive oil, salt, and pepper.",
                "Place chicken on the tray; arrange broccoli and asparagus alongside.",
                "Roast for 20–22 minutes until chicken is cooked through and vegetables are slightly charred.",
                "Slice chicken and serve with roasted vegetables."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        // ── Dinner ───────────────────────────────────────────────────────────

        Recipe(
            id = "ginger_garlic_chicken_broccoli",
            name = "Ginger Garlic Chicken & Broccoli Stir-Fry",
            description = "Chicken breast strips wok-fried with broccoli, bok choy, fresh ginger, and garlic in a tamari-sesame sauce — a classic high-protein, low-carb dinner that delivers sulforaphane and gingerols alongside a clean amino acid profile.",
            mealType = "Dinner",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Chicken",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "200g chicken breast, sliced thinly",
                "2 cups broccoli florets",
                "2 cups bok choy, halved",
                "2 tsp fresh ginger, grated",
                "2 cloves garlic, minced",
                "1 tbsp tamari (gluten-free soy sauce)",
                "1 tsp sesame oil",
                "1 tsp olive oil",
                "1 tbsp sesame seeds",
                "2 spring onions, sliced"
            ),
            calories = 360, proteinGrams = 46.0, carbGrams = 14.0, fatGrams = 13.0, fiberGrams = 5.0,
            prepMinutes = 8, cookMinutes = 12,
            instructions = listOf(
                "Heat olive oil in a wok over high heat until smoking.",
                "Add chicken strips and stir-fry 4–5 minutes until golden; remove and set aside.",
                "Add broccoli to the wok and stir-fry 3 minutes.",
                "Add bok choy, ginger, and garlic; stir-fry 2 minutes.",
                "Return chicken to the wok; add tamari and sesame oil. Toss to coat.",
                "Cook 1 more minute; serve topped with sesame seeds and spring onions."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "chicken_tikka_masala_df",
            name = "Coconut Chicken Tikka Masala",
            description = "Tender chicken breast simmered in a fragrant tomato-coconut masala with cumin, coriander, garam masala, and fresh ginger — a dairy-free, high-protein version of a classic that combines comfort with metabolic benefit.",
            mealType = "Dinner",
            tags = listOf("High Protein", "Whole Foods"),
            proteinSource = "Chicken",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "200g chicken breast, cubed",
                "½ cup light coconut milk",
                "1 cup chopped tomatoes (fresh or canned)",
                "½ onion, diced",
                "1 clove garlic, minced",
                "1 tsp fresh ginger, grated",
                "1 tsp ground cumin",
                "1 tsp ground coriander",
                "½ tsp garam masala",
                "½ tsp ground turmeric",
                "1 tsp olive oil",
                "1 cup baby spinach",
                "Salt to taste",
                "Fresh coriander to serve"
            ),
            calories = 390, proteinGrams = 42.0, carbGrams = 18.0, fatGrams = 17.0, fiberGrams = 4.0,
            prepMinutes = 10, cookMinutes = 22,
            instructions = listOf(
                "Heat olive oil in a pan over medium heat. Sauté onion 3 minutes until softened.",
                "Add garlic, ginger, cumin, coriander, turmeric, and garam masala; cook 1 minute.",
                "Add tomatoes and cook 5 minutes until broken down.",
                "Add chicken and stir to coat; cook 5 minutes.",
                "Pour in coconut milk; simmer 10 minutes until chicken is cooked through.",
                "Stir in spinach until wilted; season with salt and serve with fresh coriander."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "turmeric_baked_chicken_cauliflower",
            name = "Turmeric Baked Chicken with Cauliflower Rice",
            description = "Chicken thigh marinated in turmeric, cumin, and lemon baked until golden, served over cauliflower rice cooked with garlic and coriander — a low-carb dinner that maximises protein and curcumin bioavailability together.",
            mealType = "Dinner",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Chicken",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "200g chicken thigh (skin removed)",
                "1 tsp ground turmeric",
                "½ tsp ground cumin",
                "1 tsp lemon juice",
                "1 tsp olive oil",
                "3 cups cauliflower florets, grated or processed into rice",
                "1 clove garlic, minced",
                "¼ cup fresh coriander, chopped",
                "Salt and black pepper to taste"
            ),
            calories = 375, proteinGrams = 40.0, carbGrams = 14.0, fatGrams = 19.0, fiberGrams = 5.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Preheat oven to 200°C.",
                "Rub chicken with turmeric, cumin, lemon juice, olive oil, salt, and pepper.",
                "Bake chicken for 22–25 minutes until cooked through and golden.",
                "Meanwhile, heat a pan over medium heat and sauté garlic 1 minute.",
                "Add cauliflower rice and cook 5–6 minutes, stirring often, until tender.",
                "Stir in half the coriander; season with salt.",
                "Serve baked chicken over cauliflower rice; garnish with remaining coriander."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "chicken_chickpea_spinach_curry",
            name = "Chicken & Chickpea Spinach Curry",
            description = "Lean chicken breast and chickpeas simmered in a coconut-tomato curry base with ginger, garlic, and spinach — a dual-protein dinner where chickpeas add fiber and resistant starch alongside the complete protein of chicken.",
            mealType = "Dinner",
            tags = listOf("High Protein", "Whole Foods"),
            proteinSource = "Chicken",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "180g chicken breast, cubed",
                "½ cup canned chickpeas, drained and rinsed",
                "2 cups baby spinach",
                "1 cup chopped tomatoes",
                "¼ cup light coconut milk",
                "½ onion, diced",
                "1 clove garlic, minced",
                "1 tsp fresh ginger, grated",
                "1 tsp ground cumin",
                "½ tsp ground turmeric",
                "½ tsp ground coriander",
                "1 tsp olive oil",
                "Salt to taste"
            ),
            calories = 420, proteinGrams = 44.0, carbGrams = 30.0, fatGrams = 13.0, fiberGrams = 8.0,
            prepMinutes = 10, cookMinutes = 22,
            instructions = listOf(
                "Heat olive oil in a pan; sauté onion 3 minutes.",
                "Add garlic, ginger, cumin, turmeric, and coriander; cook 1 minute.",
                "Add tomatoes and simmer 5 minutes.",
                "Add chicken and chickpeas; stir to coat and cook 8 minutes.",
                "Pour in coconut milk; simmer 5 minutes until chicken is cooked through.",
                "Add spinach and stir until wilted; season with salt and serve."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        // ── Protein Booster ──────────────────────────────────────────────────

        Recipe(
            id = "chicken_avocado_booster",
            name = "Shredded Chicken & Avocado Anti-Inflammatory Booster",
            description = "Cold shredded chicken breast tossed with half an avocado, cucumber, lemon juice, and fresh herbs — a quick, no-cook protein booster combining complete lean protein with oleic acid and potassium from avocado.",
            mealType = "Protein Booster",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Chicken",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "100g cooked chicken breast, shredded",
                "½ ripe avocado, cubed",
                "½ cucumber, diced",
                "1 tbsp lemon juice",
                "2 tbsp fresh coriander or parsley, chopped",
                "Salt and black pepper to taste"
            ),
            calories = 245, proteinGrams = 28.0, carbGrams = 6.0, fatGrams = 12.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine shredded chicken, cucumber, and fresh herbs in a bowl.",
                "Add avocado cubes and lemon juice; toss gently to avoid mashing the avocado.",
                "Season with salt and pepper and serve immediately."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 1
        ),

    )
}
