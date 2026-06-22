package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object TurkeyRecipes {
    val ALL: List<Recipe> = listOf(

        // ── Breakfast ────────────────────────────────────────────────────────

        Recipe(
            id = "turkey_spinach_egg_white_omelette",
            name = "Turkey & Spinach Egg White Omelette",
            description = "Diced turkey breast and baby spinach folded into a light egg white omelette seasoned with turmeric and black pepper — a very low-fat, high-protein breakfast that combines lean poultry and the anti-inflammatory synergy of curcumin with piperine.",
            mealType = "Breakfast",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Turkey",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "80g turkey breast, finely diced",
                "3 egg whites",
                "1 cup baby spinach",
                "½ cup cherry tomatoes, halved",
                "¼ tsp ground turmeric",
                "Pinch of black pepper",
                "1 tsp olive oil",
                "Salt to taste",
                "Fresh herbs to serve"
            ),
            calories = 260, proteinGrams = 36.0, carbGrams = 8.0, fatGrams = 8.0, fiberGrams = 2.0,
            prepMinutes = 7, cookMinutes = 10,
            instructions = listOf(
                "Lightly beat egg whites with turmeric, black pepper, and a pinch of salt.",
                "Heat olive oil in a non-stick pan over medium heat.",
                "Cook turkey for 3–4 minutes until just cooked through; set aside.",
                "Pour egg whites into the same pan; cook undisturbed for 2 minutes until mostly set.",
                "Add spinach, turkey, and tomatoes to one half; fold omelette over.",
                "Cook 1 more minute; serve with fresh herbs."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        // ── Lunch ────────────────────────────────────────────────────────────

        Recipe(
            id = "turkey_quinoa_stuffed_capsicum",
            name = "Turkey & Quinoa Stuffed Capsicum",
            description = "Halved capsicums filled with a spiced ground turkey and quinoa mixture then baked until tender — the capsicum shell provides vitamin C and carotenoids while the turkey-quinoa filling delivers complete protein and all essential amino acids.",
            mealType = "Lunch",
            tags = listOf("High Protein", "Whole Foods"),
            proteinSource = "Turkey",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "150g lean ground turkey",
                "½ cup cooked quinoa",
                "2 large red or yellow capsicums, halved and deseeded",
                "½ onion, finely diced",
                "1 clove garlic, minced",
                "½ cup chopped tomatoes",
                "1 tsp ground cumin",
                "½ tsp smoked paprika",
                "1 tsp olive oil",
                "2 tbsp fresh parsley or coriander",
                "Salt and black pepper to taste"
            ),
            calories = 395, proteinGrams = 38.0, carbGrams = 36.0, fatGrams = 10.0, fiberGrams = 6.0,
            prepMinutes = 12, cookMinutes = 28,
            instructions = listOf(
                "Preheat oven to 190°C.",
                "Heat olive oil in a pan; sauté onion 3 minutes. Add garlic and spices; cook 1 minute.",
                "Add ground turkey; cook 5 minutes until browned. Stir in tomatoes and cook 3 minutes.",
                "Remove from heat and stir in quinoa and herbs; season with salt and pepper.",
                "Fill capsicum halves with the turkey-quinoa mixture; place in a baking dish.",
                "Cover with foil and bake 20 minutes; remove foil and bake 8 more minutes.",
                "Serve warm."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "ginger_turkey_meatball_kale",
            name = "Ginger Turkey Meatballs with Kale Bowl",
            description = "Baked ground turkey meatballs seasoned with fresh ginger, garlic, and tamari served over massaged kale with sesame dressing — a low-carb, anti-inflammatory lunch where ginger's gingerols amplify the metabolic benefit of lean turkey.",
            mealType = "Lunch",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Turkey",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "180g lean ground turkey",
                "1 tsp fresh ginger, grated",
                "1 clove garlic, minced",
                "1 tbsp tamari (gluten-free soy sauce)",
                "2 cups kale, stems removed and thinly sliced",
                "1 tsp sesame oil",
                "1 tsp rice vinegar",
                "1 tsp sesame seeds",
                "2 spring onions, sliced",
                "Salt and black pepper to taste"
            ),
            calories = 350, proteinGrams = 42.0, carbGrams = 12.0, fatGrams = 13.0, fiberGrams = 4.0,
            prepMinutes = 12, cookMinutes = 18,
            instructions = listOf(
                "Preheat oven to 200°C.",
                "Mix turkey, ginger, garlic, half the tamari, salt, and pepper; form into 8 meatballs.",
                "Bake meatballs on a lined tray for 15–18 minutes until cooked through.",
                "Massage kale with a pinch of salt until softened.",
                "Whisk sesame oil, rice vinegar, and remaining tamari into a dressing.",
                "Toss kale with dressing; top with meatballs, sesame seeds, and spring onions."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "turkey_lentil_soup",
            name = "Turkey & Red Lentil Anti-Inflammatory Soup",
            description = "Diced turkey breast simmered with red lentils, carrot, turmeric, and cumin into a thick, warming soup — combining lean poultry protein with lentil fiber and anti-inflammatory spices for a deeply nourishing, insulin-friendly lunch.",
            mealType = "Lunch",
            tags = listOf("High Protein", "Whole Foods"),
            proteinSource = "Turkey",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "150g turkey breast, diced",
                "½ cup red lentils, rinsed",
                "1 medium carrot, diced",
                "1 stalk celery, diced",
                "½ onion, diced",
                "1 clove garlic, minced",
                "1 tsp fresh ginger, grated",
                "1 tsp ground turmeric",
                "½ tsp ground cumin",
                "2 cups low-sodium vegetable stock",
                "1 tsp olive oil",
                "Fresh coriander and lemon juice to serve",
                "Salt and black pepper to taste"
            ),
            calories = 380, proteinGrams = 40.0, carbGrams = 40.0, fatGrams = 6.0, fiberGrams = 10.0,
            prepMinutes = 10, cookMinutes = 30,
            instructions = listOf(
                "Heat olive oil in a pot over medium heat. Sauté onion, carrot, and celery 4 minutes.",
                "Add garlic, ginger, turmeric, and cumin; cook 1 minute.",
                "Add turkey and cook 3 minutes.",
                "Add lentils and stock; bring to a boil then reduce to a simmer.",
                "Simmer 20–25 minutes until lentils are soft and turkey is cooked through.",
                "Season with salt and pepper; serve with lemon juice and fresh coriander."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        // ── Dinner ───────────────────────────────────────────────────────────

        Recipe(
            id = "turkey_keema_peas_cauliflower",
            name = "Turkey Keema with Peas & Cauliflower",
            description = "Spiced ground turkey cooked with peas and cauliflower in a tomato-ginger base — a low-carb, high-protein Indian-style dry curry where the cauliflower replaces starchy grains and turkey provides a leaner protein profile than traditional lamb keema.",
            mealType = "Dinner",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Turkey",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "200g lean ground turkey",
                "1 cup frozen peas",
                "2 cups cauliflower florets",
                "½ onion, finely diced",
                "1 cup chopped tomatoes",
                "1 clove garlic, minced",
                "1 tsp fresh ginger, grated",
                "1 tsp garam masala",
                "½ tsp ground turmeric",
                "½ tsp ground cumin",
                "1 tsp olive oil",
                "Fresh coriander to serve",
                "Salt to taste"
            ),
            calories = 370, proteinGrams = 44.0, carbGrams = 20.0, fatGrams = 10.0, fiberGrams = 8.0,
            prepMinutes = 10, cookMinutes = 22,
            instructions = listOf(
                "Heat olive oil in a pan; sauté onion 3 minutes.",
                "Add garlic, ginger, turmeric, cumin, and garam masala; cook 1 minute.",
                "Add ground turkey; cook 5–6 minutes until browned.",
                "Add tomatoes and cook 4 minutes.",
                "Add cauliflower and peas; stir to combine. Cover and cook 8–10 minutes until cauliflower is tender.",
                "Season with salt; serve with fresh coriander."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "baked_turkey_kofta_roasted_veg",
            name = "Baked Turkey Kofta with Roasted Vegetables",
            description = "Herbed ground turkey kofta patties baked alongside zucchini, eggplant, and cherry tomatoes — a Middle Eastern-inspired dinner where lean turkey is shaped into kofta and paired with low-glycaemic roasted vegetables for a protein-dense, anti-inflammatory plate.",
            mealType = "Dinner",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Turkey",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "200g lean ground turkey",
                "1 tsp fresh ginger, grated",
                "1 clove garlic, minced",
                "½ tsp ground cumin",
                "½ tsp ground coriander",
                "2 tbsp fresh parsley, finely chopped",
                "1 small zucchini, sliced",
                "1 small eggplant, cubed",
                "½ cup cherry tomatoes",
                "1 tsp olive oil",
                "Salt and black pepper to taste"
            ),
            calories = 355, proteinGrams = 42.0, carbGrams = 16.0, fatGrams = 12.0, fiberGrams = 5.0,
            prepMinutes = 12, cookMinutes = 22,
            instructions = listOf(
                "Preheat oven to 200°C. Line a large baking tray.",
                "Mix turkey with ginger, garlic, cumin, coriander, parsley, salt, and pepper; form into 6 oval kofta.",
                "Toss zucchini, eggplant, and cherry tomatoes in olive oil, salt, and pepper.",
                "Arrange kofta and vegetables on the tray.",
                "Bake 20–22 minutes until kofta are cooked through and vegetables are golden.",
                "Serve immediately."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "turkey_sweet_potato_stew",
            name = "Turkey & Sweet Potato Coconut Stew",
            description = "Cubed turkey breast simmered in a turmeric-ginger coconut broth with sweet potato and spinach — a warming, nutrient-dense dinner where sweet potato provides slow-release carbohydrates and beta-carotene, while coconut milk adds lauric acid.",
            mealType = "Dinner",
            tags = listOf("High Protein", "Whole Foods"),
            proteinSource = "Turkey",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory", "Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "180g turkey breast, cubed",
                "1 medium sweet potato, peeled and cubed",
                "2 cups baby spinach",
                "½ cup light coconut milk",
                "½ cup low-sodium vegetable stock",
                "½ onion, diced",
                "1 clove garlic, minced",
                "1 tsp fresh ginger, grated",
                "1 tsp ground turmeric",
                "½ tsp ground cumin",
                "½ tsp ground coriander",
                "1 tsp olive oil",
                "Salt and black pepper to taste"
            ),
            calories = 410, proteinGrams = 38.0, carbGrams = 38.0, fatGrams = 12.0, fiberGrams = 7.0,
            prepMinutes = 10, cookMinutes = 28,
            instructions = listOf(
                "Heat olive oil in a pot; sauté onion 3 minutes.",
                "Add garlic, ginger, turmeric, cumin, and coriander; cook 1 minute.",
                "Add turkey and cook 3 minutes.",
                "Add sweet potato, coconut milk, and stock; bring to a simmer.",
                "Cook 18–20 minutes until sweet potato is tender and turkey is cooked through.",
                "Stir in spinach until wilted; season with salt and pepper and serve."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        // ── Protein Booster ──────────────────────────────────────────────────

        Recipe(
            id = "turkey_avocado_protein_cup",
            name = "Turkey & Avocado Protein Cup",
            description = "Shredded turkey breast paired with ripe avocado, cucumber, and fresh lemon in a quick no-cook protein cup — lean turkey provides a low-fat complete protein while avocado contributes heart-healthy monounsaturated fat and potassium.",
            mealType = "Protein Booster",
            tags = listOf("High Protein", "Low Carb", "Whole Foods"),
            proteinSource = "Turkey",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Insulin Resistance Friendly"),
            ingredients = listOf(
                "100g cooked turkey breast, shredded",
                "½ ripe avocado, diced",
                "½ cucumber, diced",
                "1 tbsp lemon juice",
                "2 tbsp fresh parsley or coriander",
                "Salt and black pepper to taste"
            ),
            calories = 230, proteinGrams = 26.0, carbGrams = 6.0, fatGrams = 10.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine shredded turkey, cucumber, and fresh herbs in a bowl.",
                "Add diced avocado and lemon juice; toss gently.",
                "Season with salt and pepper and serve immediately."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 1
        ),

    )
}
