package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object LunchRecipes3 {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "salmon_kale_lunch_bowl",
            name = "Salmon & Kale Anti-Inflammatory Lunch Bowl",
            description = "Poached or canned salmon over massaged kale with avocado, hemp seeds, pumpkin seeds, and a lemon-tahini dressing — combining EPA+DHA from salmon with the sulforaphane precursors in kale for a potent anti-inflammatory midday meal.",
            mealType = "Lunch",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "120g canned or poached salmon, flaked",
                "2 cups curly kale, stems removed and massaged",
                "¼ avocado, diced",
                "½ cucumber, sliced",
                "1 tbsp hemp hearts",
                "1 tbsp pumpkin seeds",
                "1 tbsp tahini",
                "1 tbsp lemon juice",
                "½ tsp ground turmeric",
                "Salt and black pepper to taste"
            ),
            calories = 380, proteinGrams = 34.0, carbGrams = 16.0, fatGrams = 22.0, fiberGrams = 7.0,
            prepMinutes = 10, cookMinutes = 0,
            instructions = listOf(
                "Massage kale with a pinch of salt and a few drops of lemon juice for 2 minutes until softened.",
                "Whisk tahini, remaining lemon juice, turmeric, and a splash of water into a dressing.",
                "Place kale in a bowl and top with flaked salmon, avocado, and cucumber.",
                "Drizzle tahini dressing over the bowl.",
                "Finish with hemp hearts, pumpkin seeds, and black pepper."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "tempeh_kale_tahini_bowl",
            name = "Tempeh & Kale Tahini Power Bowl",
            description = "Pan-seared tempeh over massaged kale dressed with lemon-tahini and turmeric — combining fermented soy protein with calcium-rich sesame, iron-rich kale, and curcumin for a deeply nourishing anti-inflammatory lunch.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Tempeh",
            restrictions = listOf("Dairy Free", "Gluten Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "150g tempeh, sliced into strips",
                "2 cups curly kale, stems removed and massaged",
                "½ roasted red capsicum, sliced",
                "2 tbsp tahini",
                "1 tbsp lemon juice",
                "½ tsp ground turmeric",
                "1 clove garlic, minced",
                "1 tsp olive oil",
                "1 tsp sesame seeds",
                "Salt and black pepper to taste"
            ),
            calories = 390, proteinGrams = 30.0, carbGrams = 20.0, fatGrams = 22.0, fiberGrams = 7.0,
            prepMinutes = 8, cookMinutes = 12,
            instructions = listOf(
                "Heat olive oil in a pan over medium-high heat.",
                "Add tempeh strips and cook 4–5 minutes per side until golden.",
                "Whisk tahini, lemon juice, garlic, turmeric, and 2 tbsp water into a smooth dressing.",
                "Massage kale with a pinch of salt until softened.",
                "Arrange kale and roasted capsicum in a bowl.",
                "Top with tempeh strips and drizzle tahini dressing over everything.",
                "Sprinkle sesame seeds and season with black pepper."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "sardine_arugula_walnut_salad",
            name = "Sardine & Arugula Walnut Anti-Inflammatory Salad",
            description = "Peppery arugula topped with omega-3-rich sardines, brain-healthy walnuts, cherry tomatoes, and capers — one of the most anti-inflammatory salads in the catalog, with ALA from walnuts stacking on top of EPA+DHA from sardines.",
            mealType = "Lunch",
            tags = listOf("Pescatarian", "High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Fish",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "1 can (95g) sardines in spring water, drained",
                "2 cups rocket/arugula",
                "½ cup cherry tomatoes, halved",
                "2 tbsp walnuts, roughly chopped",
                "1 tbsp capers",
                "¼ red onion, thinly sliced",
                "1 tbsp olive oil",
                "1 tbsp lemon juice",
                "½ tsp Dijon mustard",
                "Black pepper to taste"
            ),
            calories = 320, proteinGrams = 26.0, carbGrams = 10.0, fatGrams = 20.0, fiberGrams = 4.0,
            prepMinutes = 8, cookMinutes = 0,
            instructions = listOf(
                "Whisk olive oil, lemon juice, and Dijon mustard into a dressing.",
                "Toss rocket with the dressing and arrange on a plate.",
                "Top with sardines, cherry tomatoes, red onion, and capers.",
                "Scatter walnuts over the salad.",
                "Finish with black pepper and serve immediately."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "walnut_hemp_roasted_veggie_salad",
            name = "Walnut Hemp Roasted Vegetable Power Salad",
            description = "Roasted broccoli and capsicum over spinach with walnuts, hemp hearts, pumpkin seeds, flaxseed, and a lemon-olive oil dressing — stacking ALA, GLA, zinc, magnesium, and sulforaphane for comprehensive women's health support.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Nuts",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "¼ cup walnuts, roughly chopped",
                "3 tbsp hemp hearts",
                "2 tbsp pumpkin seeds",
                "1 tbsp ground flaxseed",
                "2 cups broccoli florets",
                "½ red capsicum, sliced",
                "2 cups baby spinach",
                "1 tbsp olive oil (for roasting)",
                "1 tbsp lemon juice",
                "½ tsp ground turmeric",
                "Salt and black pepper to taste"
            ),
            calories = 385, proteinGrams = 20.0, carbGrams = 22.0, fatGrams = 26.0, fiberGrams = 9.0,
            prepMinutes = 10, cookMinutes = 20,
            instructions = listOf(
                "Preheat oven to 200°C. Toss broccoli and capsicum in olive oil, turmeric, salt, and pepper.",
                "Roast for 18–20 minutes until broccoli is slightly charred at the edges.",
                "Arrange spinach in a bowl and top with the warm roasted vegetables.",
                "Scatter walnuts, hemp hearts, pumpkin seeds, and flaxseed over the top.",
                "Drizzle with lemon juice and serve immediately."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 8,
            womensHealthScore = 10,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "pea_protein_green_power_lunch",
            name = "Pea Protein Green Power Lunch Bowl",
            description = "Herb-seasoned pea protein patties baked and served over a bed of spinach, cucumber, cherry tomatoes, and tahini — delivering a plant-complete amino acid profile with virtually no glycaemic load.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Low Carb"),
            proteinSource = "Pea Protein",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "1.5 scoops (45g) pea protein powder, unflavoured",
                "1 tbsp almond flour",
                "½ cup grated zucchini, excess moisture squeezed out",
                "¼ cup baby spinach, finely chopped",
                "½ tsp ground cumin",
                "½ tsp ground turmeric",
                "1 tbsp ground flaxseed + 3 tbsp water (flax egg)",
                "2 cups baby spinach (for serving)",
                "½ cucumber, sliced",
                "½ cup cherry tomatoes, halved",
                "1 tbsp tahini",
                "1 tbsp lemon juice",
                "Salt and black pepper to taste"
            ),
            calories = 350, proteinGrams = 32.0, carbGrams = 18.0, fatGrams = 14.0, fiberGrams = 6.0,
            prepMinutes = 12, cookMinutes = 18,
            instructions = listOf(
                "Mix flaxseed and water; let sit 5 minutes to form a flax egg.",
                "Combine pea protein, almond flour, zucchini, chopped spinach, cumin, turmeric, and flax egg.",
                "Shape into 4 small patties.",
                "Bake at 190°C for 16–18 minutes, flipping once halfway.",
                "Whisk tahini and lemon juice with a splash of water into a dressing.",
                "Serve patties over baby spinach with cucumber and tomatoes, drizzled with tahini dressing."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 9,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "hemp_seed_tabbouleh",
            name = "Hemp Seed Power Tabbouleh",
            description = "A grain-free tabbouleh built on hemp hearts as the protein base instead of bulgur — piled high with parsley, mint, cucumber, and tomato, dressed in lemon and olive oil for a folate-rich, anti-inflammatory, zero-grain lunch.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Seeds",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "5 tbsp hemp hearts",
                "2 cups fresh flat-leaf parsley, finely chopped",
                "¼ cup fresh mint, finely chopped",
                "1 large cucumber, finely diced",
                "1 cup cherry tomatoes, quartered",
                "1 tbsp ground flaxseed",
                "2 tbsp lemon juice",
                "1 tbsp extra-virgin olive oil",
                "¼ tsp ground allspice",
                "Salt and black pepper to taste"
            ),
            calories = 285, proteinGrams = 22.0, carbGrams = 14.0, fatGrams = 18.0, fiberGrams = 5.0,
            prepMinutes = 15, cookMinutes = 0,
            instructions = listOf(
                "Finely chop parsley and mint and place in a large mixing bowl.",
                "Add diced cucumber, cherry tomatoes, and hemp hearts.",
                "Stir in ground flaxseed and allspice.",
                "Dress with lemon juice and olive oil; toss well to combine.",
                "Season with salt and pepper.",
                "Refrigerate for 10 minutes before serving to allow flavours to meld."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 10,
            glucoseImpactScore = 1
        ),

    )
}
