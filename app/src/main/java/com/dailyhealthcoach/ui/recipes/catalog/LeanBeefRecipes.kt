package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object LeanBeefRecipes {
    val ALL: List<Recipe> = listOf(

        // ── Breakfast ────────────────────────────────────────────────────────

        Recipe(
            id = "lean_beef_spinach_egg_scramble",
            name = "Lean Beef & Spinach Egg Scramble",
            description = "Lean ground beef sautéed with garlic and turmeric, folded through whole eggs and wilted spinach — a high-protein, low-carb breakfast that combines the iron and creatine of lean beef with the choline and B12 of eggs.",
            mealType = "Breakfast",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Beef",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free"),
            collection = listOf("Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "100g lean ground beef (90%+ lean)",
                "2 whole eggs",
                "1 cup baby spinach",
                "1 clove garlic, minced",
                "¼ tsp ground turmeric",
                "½ tsp ground cumin",
                "1 tsp olive oil",
                "Salt and black pepper to taste",
                "Fresh coriander or parsley to serve"
            ),
            calories = 310, proteinGrams = 36.0, carbGrams = 4.0, fatGrams = 17.0, fiberGrams = 1.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Heat olive oil in a non-stick pan over medium-high heat.",
                "Add garlic and cook 30 seconds; add beef and break it up.",
                "Cook beef 4–5 minutes until browned; drain any excess fat.",
                "Stir in turmeric and cumin; cook 1 minute.",
                "Add spinach and stir until wilted.",
                "Beat eggs lightly, pour over the beef mixture, and stir gently until just set.",
                "Season with salt and pepper; serve with fresh herbs."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 1
        ),

        // ── Lunch ────────────────────────────────────────────────────────────

        Recipe(
            id = "lean_beef_vegetable_stirfry",
            name = "Lean Beef & Vegetable Stir-Fry Bowl",
            description = "Thinly sliced lean beef wok-fried with broccoli, bok choy, and capsicum in a ginger-tamari sauce — a high-protein, low-carb bowl that brings together iron-rich lean beef with cruciferous vegetables for a metabolically supportive lunch.",
            mealType = "Lunch",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Beef",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "180g lean beef sirloin or rump, thinly sliced",
                "2 cups broccoli florets",
                "2 cups bok choy, halved",
                "½ red capsicum, sliced",
                "2 tsp fresh ginger, grated",
                "1 clove garlic, minced",
                "1 tbsp tamari (gluten-free soy sauce)",
                "1 tsp sesame oil",
                "1 tsp olive oil",
                "1 tsp sesame seeds",
                "2 spring onions, sliced"
            ),
            calories = 380, proteinGrams = 42.0, carbGrams = 14.0, fatGrams = 17.0, fiberGrams = 5.0,
            prepMinutes = 10, cookMinutes = 12,
            instructions = listOf(
                "Heat olive oil in a wok over high heat until very hot.",
                "Add beef slices in a single layer; sear 1–2 minutes per side. Remove and set aside.",
                "Add broccoli and stir-fry 3 minutes.",
                "Add bok choy, capsicum, ginger, and garlic; stir-fry 2 minutes.",
                "Return beef to wok; add tamari and sesame oil. Toss for 1 minute.",
                "Serve topped with sesame seeds and spring onions."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "lean_beef_kheema_cauliflower",
            name = "Lean Beef Kheema with Cauliflower Rice",
            description = "Spiced lean ground beef cooked with peas, onion, tomatoes, and Indian spices served over cauliflower rice — a low-carb, high-protein take on a South Asian classic that substitutes cauliflower for rice to eliminate the glucose spike.",
            mealType = "Lunch",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Beef",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "180g lean ground beef",
                "3 cups cauliflower florets, grated or processed into rice",
                "½ cup frozen peas",
                "½ onion, finely diced",
                "½ cup chopped tomatoes",
                "1 clove garlic, minced",
                "1 tsp fresh ginger, grated",
                "½ tsp garam masala",
                "½ tsp ground cumin",
                "¼ tsp ground turmeric",
                "1 tsp olive oil",
                "Fresh coriander to serve",
                "Salt to taste"
            ),
            calories = 360, proteinGrams = 40.0, carbGrams = 18.0, fatGrams = 15.0, fiberGrams = 6.0,
            prepMinutes = 10, cookMinutes = 22,
            instructions = listOf(
                "Heat olive oil in a pan; sauté onion 3 minutes.",
                "Add garlic, ginger, garam masala, cumin, and turmeric; cook 1 minute.",
                "Add beef; break it up and cook 5–6 minutes until browned.",
                "Add tomatoes and peas; simmer 6–8 minutes until tomatoes break down.",
                "Meanwhile, cook cauliflower rice in a dry non-stick pan 5 minutes until just tender.",
                "Serve kheema over cauliflower rice with fresh coriander."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        // ── Dinner ───────────────────────────────────────────────────────────

        Recipe(
            id = "ginger_turmeric_beef_broccoli",
            name = "Ginger Turmeric Lean Beef with Broccoli",
            description = "Lean beef strips marinated in turmeric, ginger, and tamari, wok-fried until caramelised and served over a bed of steamed broccoli — maximising the anti-inflammatory and insulin-sensitising potential of turmeric alongside the high bioavailability protein of lean red meat.",
            mealType = "Dinner",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Beef",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "200g lean beef sirloin, thinly sliced",
                "2 cups broccoli florets",
                "2 tsp fresh ginger, grated",
                "1 clove garlic, minced",
                "1 tbsp tamari (gluten-free soy sauce)",
                "½ tsp ground turmeric",
                "1 tsp sesame oil",
                "1 tsp olive oil",
                "2 spring onions, sliced",
                "1 tbsp sesame seeds"
            ),
            calories = 390, proteinGrams = 46.0, carbGrams = 12.0, fatGrams = 17.0, fiberGrams = 4.0,
            prepMinutes = 10, cookMinutes = 12,
            instructions = listOf(
                "Toss beef with turmeric, tamari, and half the ginger; marinate 5 minutes.",
                "Steam broccoli for 4–5 minutes until bright green and just tender.",
                "Heat olive oil in a wok over high heat; sear beef 1–2 minutes per side until caramelised.",
                "Add remaining ginger and garlic; stir-fry 1 minute.",
                "Add sesame oil; toss to combine.",
                "Serve beef over steamed broccoli; top with sesame seeds and spring onions."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 6,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "lean_beef_kale_bowl",
            name = "Lean Beef & Kale Anti-Inflammatory Bowl",
            description = "Pan-seared lean beef served over massaged kale with lemon, garlic, pumpkin seeds, and sesame — kale's vitamin K and sulforaphane pair with the iron and zinc of lean beef to create one of the most micronutrient-dense dinner bowls in the catalog.",
            mealType = "Dinner",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Beef",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "200g lean beef fillet or sirloin",
                "2 cups kale, stems removed and thinly sliced",
                "1 tbsp lemon juice",
                "1 clove garlic, minced",
                "1 tsp olive oil",
                "2 tbsp pumpkin seeds",
                "1 tbsp sesame seeds",
                "Salt and black pepper to taste"
            ),
            calories = 410, proteinGrams = 44.0, carbGrams = 10.0, fatGrams = 21.0, fiberGrams = 4.0,
            prepMinutes = 8, cookMinutes = 10,
            instructions = listOf(
                "Season beef with salt and pepper.",
                "Heat olive oil in a pan over high heat; sear beef 3–4 minutes per side until browned. Rest 3 minutes then slice.",
                "Massage kale with lemon juice, garlic, and a pinch of salt until softened.",
                "Arrange kale in a bowl; top with sliced beef.",
                "Scatter pumpkin seeds and sesame seeds over the bowl; serve immediately."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "lean_beef_keema_spinach_curry",
            name = "Lean Beef Keema & Spinach Curry",
            description = "Lean ground beef simmered in an aromatic tomato-onion curry base with garam masala and fresh ginger, finished with baby spinach — a whole-food, dairy-free Indian keema that provides haem iron alongside the non-haem iron of spinach for enhanced absorption.",
            mealType = "Dinner",
            tags = listOf("High Protein", "Whole Foods"),
            proteinSource = "Beef",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "200g lean ground beef",
                "2 cups baby spinach",
                "1 cup chopped tomatoes",
                "½ onion, finely diced",
                "1 clove garlic, minced",
                "1 tsp fresh ginger, grated",
                "1 tsp garam masala",
                "½ tsp ground cumin",
                "¼ tsp ground turmeric",
                "1 tsp olive oil",
                "Salt to taste",
                "Fresh coriander to serve"
            ),
            calories = 400, proteinGrams = 44.0, carbGrams = 14.0, fatGrams = 18.0, fiberGrams = 4.0,
            prepMinutes = 8, cookMinutes = 22,
            instructions = listOf(
                "Heat olive oil in a pan; sauté onion 3 minutes.",
                "Add garlic, ginger, garam masala, cumin, and turmeric; cook 1 minute.",
                "Add beef; break up and cook 5–6 minutes until browned.",
                "Add tomatoes and simmer 8–10 minutes until sauce thickens.",
                "Stir in spinach and cook until wilted, about 2 minutes.",
                "Season with salt; serve with fresh coriander."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 6,
            glucoseImpactScore = 3
        ),

    )
}
