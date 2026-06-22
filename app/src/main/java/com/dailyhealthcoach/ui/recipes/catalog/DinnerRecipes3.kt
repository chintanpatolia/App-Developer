package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object DinnerRecipes3 {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "turmeric_ginger_baked_salmon",
            name = "Turmeric & Ginger Baked Salmon with Roasted Broccoli",
            description = "A whole salmon fillet coated in a golden turmeric-ginger paste and baked alongside broccoli and asparagus — delivering EPA+DHA, curcumin, and sulforaphane in a single anti-inflammatory dinner with essentially zero glucose impact.",
            mealType = "Dinner",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "180g salmon fillet",
                "1 tsp ground turmeric",
                "1 tsp fresh ginger, grated",
                "1 tbsp lemon juice",
                "1 tsp olive oil",
                "2 cups broccoli florets",
                "10 asparagus spears",
                "Pinch of black pepper",
                "Salt to taste",
                "1 tbsp fresh coriander or parsley (to serve)"
            ),
            calories = 415, proteinGrams = 40.0, carbGrams = 14.0, fatGrams = 22.0, fiberGrams = 7.0,
            prepMinutes = 8, cookMinutes = 20,
            instructions = listOf(
                "Preheat oven to 200°C. Line a baking tray with baking paper.",
                "Mix turmeric, ginger, lemon juice, olive oil, salt, and pepper into a paste.",
                "Coat salmon fillet with the paste and place on one side of the tray.",
                "Arrange broccoli and asparagus on the other side; drizzle with a little olive oil.",
                "Bake for 18–20 minutes until salmon is cooked through and broccoli is slightly charred.",
                "Serve garnished with fresh coriander or parsley."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "tempeh_bok_choy_stir_fry",
            name = "Tempeh & Bok Choy Anti-Inflammatory Stir-Fry",
            description = "Tempeh strips wok-fried with bok choy, broccoli, turmeric, and ginger in a light tamari-sesame sauce — a fast, high-protein plant-based dinner combining fermented soy protein with anti-inflammatory glucosinolates from the brassica vegetables.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein", "Low Carb"),
            proteinSource = "Tempeh",
            restrictions = listOf("Dairy Free", "Gluten Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "170g tempeh, sliced into strips",
                "2 cups bok choy, halved",
                "1 cup broccoli florets",
                "1 tsp fresh ginger, grated",
                "½ tsp ground turmeric",
                "1 tbsp tamari (gluten-free soy sauce)",
                "1 tsp sesame oil",
                "1 tsp olive oil",
                "1 tsp sesame seeds",
                "2 spring onions, sliced"
            ),
            calories = 310, proteinGrams = 28.0, carbGrams = 16.0, fatGrams = 14.0, fiberGrams = 5.0,
            prepMinutes = 8, cookMinutes = 12,
            instructions = listOf(
                "Heat olive oil in a wok or large pan over high heat.",
                "Add tempeh strips and cook 3–4 minutes until golden on each side; remove and set aside.",
                "Add broccoli to the wok and stir-fry 3 minutes.",
                "Add bok choy, ginger, and turmeric; stir-fry 2 minutes.",
                "Return tempeh to the wok and add tamari and sesame oil; toss to coat.",
                "Cook 1 more minute until everything is well combined.",
                "Serve topped with sesame seeds and spring onions."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "sardine_mediterranean_plate",
            name = "Mediterranean Sardine Roasted Vegetable Plate",
            description = "Whole sardines served alongside a vibrant roasted Mediterranean vegetable platter of zucchini, capsicum, cherry tomatoes, and olives — an ancient, deeply anti-inflammatory combination that delivers omega-3, lycopene, and polyphenols together.",
            mealType = "Dinner",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "2 cans (190g total) sardines in spring water, drained",
                "1 medium zucchini, sliced into rounds",
                "½ red capsicum, cut into strips",
                "½ yellow capsicum, cut into strips",
                "1 cup cherry tomatoes",
                "6–8 kalamata olives",
                "1 tbsp olive oil",
                "1 tsp dried oregano",
                "1 tsp fresh thyme",
                "1 tbsp lemon juice",
                "Salt and black pepper to taste"
            ),
            calories = 350, proteinGrams = 32.0, carbGrams = 12.0, fatGrams = 20.0, fiberGrams = 5.0,
            prepMinutes = 10, cookMinutes = 22,
            instructions = listOf(
                "Preheat oven to 200°C.",
                "Toss zucchini, capsicum, and cherry tomatoes in olive oil, oregano, thyme, salt, and pepper.",
                "Spread on a baking tray and roast for 20–22 minutes until tender and caramelised.",
                "Arrange sardines on the plate alongside the roasted vegetables.",
                "Scatter olives over the plate and drizzle with lemon juice.",
                "Serve warm with extra lemon wedges."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "walnut_hemp_broccoli_bowl",
            name = "Walnut Hemp Seed Broccoli Anti-Inflammatory Bowl",
            description = "Roasted broccoli and beets over kale, layered with walnuts, hemp hearts, pumpkin seeds, and flaxseed in a tahini-lemon dressing — stacking ALA, GLA, and sulforaphane for one of the most comprehensive women's health dinner profiles in the catalog.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Nuts",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "¼ cup walnuts, roughly chopped",
                "4 tbsp hemp hearts",
                "2 tbsp pumpkin seeds",
                "1 tbsp ground flaxseed",
                "2 cups broccoli florets",
                "1 small raw beet, peeled and cubed",
                "2 cups kale, stems removed and massaged",
                "1 tbsp tahini",
                "1 tbsp lemon juice",
                "1 tsp olive oil",
                "Salt and black pepper to taste"
            ),
            calories = 430, proteinGrams = 24.0, carbGrams = 28.0, fatGrams = 28.0, fiberGrams = 12.0,
            prepMinutes = 10, cookMinutes = 22,
            instructions = listOf(
                "Preheat oven to 200°C.",
                "Toss broccoli and beet in olive oil, salt, and pepper. Roast for 20–22 minutes.",
                "Whisk tahini, lemon juice, and a splash of water into a thin dressing.",
                "Massage kale with a pinch of salt in a bowl until softened.",
                "Add roasted vegetables to the kale.",
                "Scatter walnuts, hemp hearts, pumpkin seeds, and flaxseed over the bowl.",
                "Drizzle with tahini dressing and serve."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 8,
            womensHealthScore = 10,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "pea_protein_spinach_curry",
            name = "Pea Protein & Spinach Coconut Curry",
            description = "A warming, fragrant curry where pea protein is whisked into a coconut-tomato sauce with turmeric, ginger, and spinach — delivering 32g complete plant protein alongside curcumin, carotenoids, and lauric acid from coconut milk.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Pea Protein",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "2 scoops (60g) pea protein powder, unflavoured",
                "3 cups baby spinach",
                "1 cup chopped tomatoes (fresh or canned)",
                "½ cup light coconut milk",
                "1 tsp ground turmeric",
                "1 tsp fresh ginger, grated",
                "1 tsp ground cumin",
                "½ tsp ground coriander",
                "1 tsp olive oil",
                "½ onion, diced",
                "1 clove garlic, minced",
                "Salt and black pepper to taste"
            ),
            calories = 355, proteinGrams = 32.0, carbGrams = 18.0, fatGrams = 14.0, fiberGrams = 5.0,
            prepMinutes = 8, cookMinutes = 18,
            instructions = listOf(
                "Heat olive oil in a pan over medium heat. Sauté onion for 3 minutes until softened.",
                "Add garlic, ginger, turmeric, cumin, and coriander; cook for 1 minute.",
                "Add tomatoes and simmer for 5 minutes until broken down.",
                "Stir in coconut milk and bring to a gentle simmer.",
                "Whisk pea protein powder into the sauce gradually, stirring to prevent lumps.",
                "Add spinach and stir until wilted, about 2 minutes.",
                "Season with salt and pepper; serve immediately."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 8,
            womensHealthScore = 9,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "mackerel_ginger_asian_greens",
            name = "Ginger Mackerel with Asian Greens Bowl",
            description = "Flaked canned mackerel over a ginger-sesame broth of bok choy, cucumber, and spring onion — mackerel is one of the richest sources of EPA+DHA per gram, and this preparation keeps all glucose impact near zero while maximising anti-inflammatory density.",
            mealType = "Dinner",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "170g canned mackerel in spring water, drained",
                "2 cups baby bok choy, halved",
                "1 cucumber, sliced into ribbons",
                "2 spring onions, sliced",
                "1 tsp fresh ginger, grated",
                "1 clove garlic, minced",
                "1 tsp sesame oil",
                "1 tbsp tamari (gluten-free soy sauce)",
                "1 tsp sesame seeds",
                "Juice of ½ lime",
                "Fresh coriander to serve"
            ),
            calories = 310, proteinGrams = 30.0, carbGrams = 8.0, fatGrams = 18.0, fiberGrams = 3.0,
            prepMinutes = 8, cookMinutes = 8,
            instructions = listOf(
                "Steam or blanch bok choy for 3–4 minutes until just tender.",
                "Combine sesame oil, tamari, ginger, garlic, and lime juice in a small bowl.",
                "Arrange bok choy and cucumber ribbons in a bowl.",
                "Top with flaked mackerel and spring onions.",
                "Drizzle the ginger-sesame dressing over the bowl.",
                "Scatter sesame seeds and fresh coriander and serve immediately."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "tempeh_cauliflower_tahini",
            name = "Tempeh & Roasted Cauliflower with Tahini Herb Sauce",
            description = "Golden-roasted cauliflower and marinated tempeh drizzled with a turmeric-lemon tahini herb sauce — the fermented protein of tempeh combined with cauliflower's indole-3-carbinol supports both hormonal metabolism and sustained satiety.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Tempeh",
            restrictions = listOf("Dairy Free", "Gluten Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "150g tempeh, cut into cubes",
                "2 cups cauliflower florets",
                "2 tbsp tahini",
                "1 tbsp lemon juice",
                "½ tsp ground turmeric",
                "¼ cup fresh parsley, chopped",
                "1 tsp olive oil",
                "½ tsp ground cumin",
                "Salt and black pepper to taste"
            ),
            calories = 360, proteinGrams = 28.0, carbGrams = 18.0, fatGrams = 20.0, fiberGrams = 7.0,
            prepMinutes = 10, cookMinutes = 22,
            instructions = listOf(
                "Preheat oven to 200°C.",
                "Toss cauliflower and tempeh in olive oil, cumin, salt, and pepper.",
                "Spread on a baking tray and roast for 20–22 minutes until golden.",
                "Whisk tahini, lemon juice, turmeric, and 2–3 tbsp water into a smooth sauce.",
                "Plate the roasted cauliflower and tempeh.",
                "Drizzle generously with tahini sauce and scatter fresh parsley over the top."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "sesame_flax_salmon_asparagus",
            name = "Sesame Flax Crusted Salmon with Asparagus",
            description = "Salmon baked under a crust of sesame seeds and ground flaxseed, served with lemon-roasted asparagus — the crust doubles the omega-3 and lignan content while adding a nutty texture, making this one of the highest-WH dinners in the catalog.",
            mealType = "Dinner",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "180g salmon fillet",
                "2 tbsp sesame seeds",
                "1 tbsp ground flaxseed",
                "1 tsp lemon zest",
                "12 asparagus spears",
                "1 tbsp lemon juice",
                "1 tsp olive oil",
                "Salt and black pepper to taste"
            ),
            calories = 425, proteinGrams = 42.0, carbGrams = 10.0, fatGrams = 26.0, fiberGrams = 6.0,
            prepMinutes = 8, cookMinutes = 18,
            instructions = listOf(
                "Preheat oven to 200°C. Line a baking tray with baking paper.",
                "Mix sesame seeds, flaxseed, and lemon zest together.",
                "Brush salmon with a tiny amount of olive oil and press the sesame-flax mixture onto the top.",
                "Place salmon on the tray. Arrange asparagus alongside; drizzle with lemon juice and olive oil.",
                "Bake for 16–18 minutes until the crust is golden and salmon flakes easily.",
                "Season with salt and pepper; serve immediately with extra lemon wedges."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 1
        ),

    )
}
