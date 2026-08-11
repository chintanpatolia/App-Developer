package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object LunchRecipes {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "paneer_burrito_bowl",
            name = "Paneer Burrito Bowl",
            description = "Pan-seared golden paneer cubes over brown rice and black beans — a hearty high-protein bowl with protein from two sources and blood-sugar-friendly fiber.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "150g paneer (cubed)",
                "½ cup cooked brown rice",
                "½ cup black beans (rinsed)",
                "½ cup tomato salsa",
                "¼ cup low-fat yogurt",
                "1 tbsp olive oil",
                "1 tsp cumin",
                "1 tsp smoked paprika",
                "Salt to taste"
            ),
            calories = 540, proteinGrams = 31.0, carbGrams = 52.0, fatGrams = 18.0, fiberGrams = 9.0,
            prepMinutes = 10, cookMinutes = 12,
            instructions = listOf(
                "Toss paneer cubes with cumin, paprika, and salt.",
                "Sauté in olive oil over medium-high heat until golden on all sides, about 5 minutes.",
                "Warm rice and beans separately.",
                "Build bowl: rice base, beans, paneer, salsa, and yogurt."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "chickpea_salad",
            name = "Mediterranean Chickpea Protein Salad",
            description = "Bright, vibrant salad with protein-dense chickpeas, crunchy cucumber, and cherry tomatoes tossed in a lemon-olive oil dressing. Anti-inflammatory and blood-sugar friendly.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup cooked chickpeas",
                "1 cup cucumber (diced)",
                "1 cup cherry tomatoes (halved)",
                "¼ cup red onion (thinly sliced)",
                "2 tbsp extra-virgin olive oil",
                "1 tbsp lemon juice",
                "1 tsp fresh mint (chopped)",
                "Salt and black pepper to taste"
            ),
            calories = 370, proteinGrams = 16.0, carbGrams = 42.0, fatGrams = 14.0, fiberGrams = 10.0,
            prepMinutes = 10, cookMinutes = 0,
            instructions = listOf(
                "Combine chickpeas, cucumber, tomatoes, and red onion.",
                "Whisk olive oil with lemon juice, mint, salt, and pepper.",
                "Toss salad with dressing and chill 5 minutes before serving."
            ),
            storageNotes = "Keeps refrigerated for 2 days; add dressing just before serving.",
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "rajma_salad",
            name = "Spiced Rajma Bean Salad",
            description = "Cooked red kidney beans (rajma) tossed with crisp vegetables, roasted cumin, and fresh lime — a protein-and-fiber powerhouse that keeps hunger at bay.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup cooked red kidney beans",
                "½ cucumber (diced)",
                "1 medium tomato (diced)",
                "¼ cup red onion (diced)",
                "1 tsp roasted cumin powder",
                "½ tsp chaat masala",
                "2 tbsp lime juice",
                "2 tbsp fresh coriander (chopped)",
                "Salt to taste"
            ),
            calories = 360, proteinGrams = 18.0, carbGrams = 56.0, fatGrams = 3.0, fiberGrams = 14.0,
            prepMinutes = 10, cookMinutes = 0,
            instructions = listOf(
                "Drain and rinse kidney beans; pat dry.",
                "Combine with cucumber, tomato, and red onion.",
                "Toss with cumin, chaat masala, lime juice, and coriander.",
                "Season with salt and serve immediately or chilled."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "quinoa_power_bowl",
            name = "Roasted Chickpea & Quinoa Power Bowl",
            description = "Complete protein from quinoa paired with crispy roasted chickpeas, baby spinach, and a silky tahini-lemon dressing. Hits metabolic reset and anti-inflammatory goals simultaneously.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup dry quinoa (cooked)",
                "1 cup chickpeas (cooked)",
                "2 cups baby spinach",
                "½ cup cherry tomatoes (halved)",
                "2 tbsp tahini",
                "1 tbsp lemon juice",
                "1 garlic clove (minced)",
                "1 tsp olive oil",
                "½ tsp smoked paprika",
                "Salt to taste"
            ),
            calories = 490, proteinGrams = 24.0, carbGrams = 62.0, fatGrams = 16.0, fiberGrams = 12.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Toss chickpeas with olive oil, paprika, and salt; roast at 200°C for 20 minutes until crispy.",
                "Cook quinoa per packet instructions.",
                "Whisk tahini with lemon juice, garlic, and 2 tbsp water to make dressing.",
                "Build bowl: quinoa, spinach, tomatoes, chickpeas; drizzle with dressing."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 7,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "turmeric_lentil_soup",
            name = "Golden Turmeric Red Lentil Soup",
            description = "A restorative, deeply golden soup with red lentils cooked in turmeric, ginger, and coconut milk. Turmeric and ginger are science-backed anti-inflammatory powerhouses.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup red lentils (rinsed)",
                "1 can light coconut milk",
                "1 cup vegetable broth",
                "1 tsp turmeric",
                "1 tsp fresh ginger (grated)",
                "2 garlic cloves (minced)",
                "1 cup fresh spinach",
                "1 tbsp olive oil",
                "Salt and black pepper to taste",
                "Lemon wedge to serve"
            ),
            calories = 390, proteinGrams = 20.0, carbGrams = 48.0, fatGrams = 12.0, fiberGrams = 14.0,
            prepMinutes = 5, cookMinutes = 25,
            instructions = listOf(
                "Heat olive oil; sauté garlic and ginger 2 minutes.",
                "Add turmeric, lentils, coconut milk, and broth; bring to a boil.",
                "Reduce heat and simmer 20 minutes until lentils are very soft.",
                "Stir in spinach until wilted; season and serve with lemon."
            ),
            storageNotes = "Refrigerates for 4 days; freezes well for 1 month.",
            metabolicResetScore = 6,
            antiInflammatoryScore = 10,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "spinach_chickpea_curry",
            name = "Quick Spinach & Chickpea Curry",
            description = "A 20-minute curry packing iron-rich spinach and plant-protein chickpeas in a spiced tomato base. Garlic, ginger, and cumin give it a powerful anti-inflammatory profile.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup cooked chickpeas",
                "2 cups baby spinach",
                "1 can diced tomatoes",
                "3 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "1 tsp cumin",
                "1 tsp coriander powder",
                "½ tsp turmeric",
                "½ tsp garam masala",
                "1 tbsp olive oil",
                "Salt to taste"
            ),
            calories = 380, proteinGrams = 18.0, carbGrams = 50.0, fatGrams = 10.0, fiberGrams = 13.0,
            prepMinutes = 5, cookMinutes = 20,
            instructions = listOf(
                "Heat oil; sauté garlic and ginger for 2 minutes.",
                "Add cumin, coriander, and turmeric; stir 30 seconds.",
                "Add tomatoes and chickpeas; simmer 12 minutes.",
                "Stir in spinach until wilted; add garam masala, season, and serve with brown rice."
            ),
            metabolicResetScore = 6,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 7,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "sweet_potato_lentil",
            name = "Roasted Sweet Potato & Red Lentil Bowl",
            description = "Caramelised roasted sweet potato over spiced red lentils with a lemon-tahini drizzle. Sweet potatoes are rich in beta-carotene; lentils provide sustained protein and fiber.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 medium sweet potato (cubed)",
                "½ cup red lentils (cooked)",
                "2 tbsp tahini",
                "1 tbsp lemon juice",
                "1 tsp smoked paprika",
                "1 tsp olive oil",
                "1 cup baby spinach",
                "Salt and pepper to taste"
            ),
            calories = 450, proteinGrams = 18.0, carbGrams = 66.0, fatGrams = 12.0, fiberGrams = 14.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Toss sweet potato with olive oil, paprika, salt; roast at 200°C for 25 minutes.",
                "Cook red lentils in 1.5 cups water until soft, about 15 minutes.",
                "Whisk tahini with lemon juice and 2 tbsp water to make dressing.",
                "Plate spinach, lentils, roasted sweet potato; drizzle with tahini dressing."
            ),
            metabolicResetScore = 5,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "greek_paneer_bowl",
            name = "Greek-Style Paneer & Quinoa Bowl",
            description = "Pan-seared paneer over quinoa with olives, cucumber, and a lemon-herb dressing — a Mediterranean-Indian fusion bowl high in protein and satisfying healthy fats.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "150g paneer (cubed)",
                "½ cup dry quinoa (cooked)",
                "¼ cup kalamata olives (halved)",
                "1 cup cucumber (diced)",
                "½ cup cherry tomatoes (halved)",
                "2 tbsp extra-virgin olive oil",
                "1 tbsp lemon juice",
                "1 tsp dried oregano",
                "Salt and pepper to taste"
            ),
            calories = 540, proteinGrams = 32.0, carbGrams = 44.0, fatGrams = 24.0, fiberGrams = 6.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Cook quinoa per packet instructions.",
                "Sear paneer in 1 tsp olive oil until golden on all sides.",
                "Whisk remaining olive oil with lemon juice and oregano.",
                "Combine quinoa, olives, cucumber, and tomatoes; toss with dressing.",
                "Top with seared paneer and serve."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "edamame_rice_bowl",
            name = "Ginger Edamame & Brown Rice Bowl",
            description = "Shelled edamame tossed with sesame-ginger dressing over brown rice — a complete protein with anti-inflammatory ginger and heart-healthy omega-6 from edamame.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup shelled edamame (cooked)",
                "½ cup cooked brown rice",
                "1 cup cucumber (sliced)",
                "½ cup shredded purple cabbage",
                "1 tsp fresh ginger (grated)",
                "1 tbsp low-sodium soy sauce",
                "1 tbsp sesame oil",
                "1 tsp rice vinegar",
                "1 tsp sesame seeds",
                "1 spring onion (sliced)"
            ),
            calories = 430, proteinGrams = 22.0, carbGrams = 54.0, fatGrams = 12.0, fiberGrams = 9.0,
            prepMinutes = 10, cookMinutes = 0,
            instructions = listOf(
                "Whisk ginger, soy sauce, sesame oil, and rice vinegar to make dressing.",
                "Combine edamame, cucumber, and cabbage in a bowl.",
                "Serve over brown rice; drizzle dressing over the top.",
                "Garnish with sesame seeds and spring onion."
            ),
            metabolicResetScore = 5,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "soya_chunk_salad",
            name = "Soya Chunk & Vegetable Protein Salad",
            description = "Rehydrated soya chunks — one of the highest plant protein sources — tossed with crisp vegetables, herbs, and a tangy lime dressing.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup soya chunks (mini)",
                "½ cup cucumber (diced)",
                "1 medium tomato (diced)",
                "¼ cup red onion (diced)",
                "2 tbsp fresh coriander (chopped)",
                "1 tsp olive oil",
                "2 tbsp lime juice",
                "½ tsp chilli powder",
                "½ tsp chaat masala",
                "Salt to taste"
            ),
            calories = 340, proteinGrams = 32.0, carbGrams = 24.0, fatGrams = 6.0, fiberGrams = 8.0,
            prepMinutes = 15, cookMinutes = 0,
            instructions = listOf(
                "Soak soya chunks in hot water 10 minutes; squeeze out excess moisture.",
                "Combine with cucumber, tomato, and onion.",
                "Toss with olive oil, lime juice, chilli powder, chaat masala, and salt.",
                "Garnish with coriander and serve."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "walnut_beet_salad",
            name = "Roasted Beetroot & Walnut Paneer Salad",
            description = "Earthy roasted beets paired with creamy paneer, crunchy walnuts, and peppery rocket. Beets are natural nitric-oxide boosters; walnuts are one of the top anti-inflammatory nuts.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "2 medium beetroot (cubed, roasted)",
                "100g paneer (cubed, pan-seared)",
                "2 cups rocket (arugula)",
                "3 tbsp walnuts (roughly chopped)",
                "1 tbsp extra-virgin olive oil",
                "1 tbsp balsamic vinegar",
                "1 tsp honey",
                "Salt and pepper to taste"
            ),
            calories = 420, proteinGrams = 24.0, carbGrams = 28.0, fatGrams = 26.0, fiberGrams = 6.0,
            prepMinutes = 10, cookMinutes = 30,
            instructions = listOf(
                "Toss beetroot cubes with a little olive oil; roast at 200°C for 25–30 minutes until tender.",
                "Sear paneer in a dry pan until golden.",
                "Whisk olive oil, balsamic, and honey for dressing.",
                "Arrange rocket, beets, and paneer on a plate; scatter walnuts and drizzle dressing."
            ),
            metabolicResetScore = 5,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 6,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "chole_bowl",
            name = "Chole Protein Bowl",
            description = "Chickpeas simmered in a rich, spiced tomato masala served over brown rice with cooling cucumber raita. A high-fiber, high-protein classic that respects blood sugar.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup cooked chickpeas",
                "1 can diced tomatoes",
                "1 medium onion (diced)",
                "2 garlic cloves (minced)",
                "1 tsp chole masala",
                "½ tsp cumin",
                "½ tsp turmeric",
                "½ cup cooked brown rice",
                "½ cup low-fat yogurt",
                "¼ cup cucumber (diced)",
                "1 tbsp olive oil"
            ),
            calories = 490, proteinGrams = 22.0, carbGrams = 72.0, fatGrams = 10.0, fiberGrams = 14.0,
            prepMinutes = 10, cookMinutes = 20,
            instructions = listOf(
                "Heat oil; sauté onion 5 minutes, add garlic and cook 2 more.",
                "Add cumin, turmeric, and chole masala; stir 30 seconds.",
                "Add tomatoes and chickpeas; simmer 15 minutes.",
                "Mix yogurt with cucumber for raita.",
                "Serve chickpea masala over brown rice with raita alongside."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "miso_tofu_bowl",
            name = "Miso-Ginger Tofu Soba Bowl",
            description = "Silken tofu and buckwheat soba in a miso-ginger broth with bok choy. Miso delivers probiotics; ginger and garlic are anti-inflammatory compounds in every sip.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "150g firm tofu (cubed)",
                "80g soba noodles (buckwheat)",
                "2 cups vegetable broth",
                "1 tbsp white miso paste",
                "1 tsp fresh ginger (grated)",
                "1 garlic clove (minced)",
                "1 cup bok choy (halved)",
                "1 tsp sesame oil",
                "1 tsp low-sodium soy sauce",
                "Spring onion and sesame seeds to garnish"
            ),
            calories = 380, proteinGrams = 22.0, carbGrams = 52.0, fatGrams = 10.0, fiberGrams = 6.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Cook soba noodles per packet; drain and rinse with cold water.",
                "Heat broth with ginger and garlic; whisk in miso paste.",
                "Add bok choy; simmer 3 minutes.",
                "Pan-fry tofu in sesame oil until golden; add to broth.",
                "Serve over soba; garnish with spring onion and sesame seeds."
            ),
            metabolicResetScore = 5,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "double_paneer_masala_bowl",
            name = "Double Paneer Masala Power Bowl",
            description = "250g of paneer — the highest paneer serving in this catalog — simmered in a spiced tomato masala with edamame for a complete amino acid profile. Designed for days when protein targets are hardest to hit.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "250g paneer (cubed)",
                "1 cup shelled edamame (cooked)",
                "1 can diced tomatoes",
                "1 medium onion (diced)",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "1 tsp cumin",
                "1 tsp coriander powder",
                "½ tsp garam masala",
                "½ tsp turmeric",
                "1 tbsp olive oil",
                "Salt to taste"
            ),
            calories = 560, proteinGrams = 44.0, carbGrams = 30.0, fatGrams = 30.0, fiberGrams = 10.0,
            prepMinutes = 10, cookMinutes = 18,
            instructions = listOf(
                "Pan-sear paneer cubes in ½ tbsp oil until golden on all sides; set aside.",
                "In the same pan, heat remaining oil; sauté onion 5 minutes until golden.",
                "Add garlic, ginger, cumin, coriander, and turmeric; cook 2 minutes.",
                "Add tomatoes and simmer 8 minutes until sauce thickens.",
                "Return paneer; add edamame and garam masala. Heat through and serve."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "tempeh_quinoa_bowl",
            name = "Teriyaki Tempeh & Quinoa Bowl",
            description = "200g of tempeh — the highest vegan protein per gram of any whole food — glazed in a quick teriyaki sauce over fluffy quinoa. At 42g of protein, this bowl rivals most meat-based lunches.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "200g tempeh (sliced)",
                "½ cup dry quinoa (cooked)",
                "2 cups baby spinach",
                "1 cup cucumber (sliced)",
                "2 tbsp low-sodium soy sauce",
                "1 tbsp rice vinegar",
                "1 tsp sesame oil",
                "1 tsp grated ginger",
                "1 tsp maple syrup",
                "1 tsp sesame seeds"
            ),
            calories = 490, proteinGrams = 42.0, carbGrams = 44.0, fatGrams = 16.0, fiberGrams = 8.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Cook quinoa per packet instructions.",
                "Whisk soy sauce, rice vinegar, sesame oil, ginger, and maple syrup for teriyaki glaze.",
                "Pan-fry tempeh slices in a dry pan over medium-high heat 4 minutes per side until golden.",
                "Toss tempeh with two-thirds of the glaze.",
                "Build bowl: quinoa, spinach, cucumber; top with tempeh and drizzle remaining glaze. Garnish with sesame seeds."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "large_soy_chunk_masala",
            name = "Large Soy Chunk Masala Bowl",
            description = "75g of dry soy chunks cooked in a rich tomato masala. Soy protein isolate is 90% protein by weight; this bowl delivers 42g from a single plant source.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "75g soy chunks (dry weight)",
                "1 can diced tomatoes",
                "1 medium onion (diced)",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "1 tsp curry powder",
                "½ tsp cumin",
                "½ tsp turmeric",
                "½ cup cooked brown rice",
                "1 tbsp olive oil",
                "Salt and coriander to garnish"
            ),
            calories = 450, proteinGrams = 42.0, carbGrams = 46.0, fatGrams = 8.0, fiberGrams = 10.0,
            prepMinutes = 15, cookMinutes = 20,
            instructions = listOf(
                "Soak soy chunks in boiling water 10 minutes; drain and squeeze out excess moisture.",
                "Heat oil; sauté onion 5 minutes, add garlic and ginger 2 minutes.",
                "Add cumin, turmeric, and curry powder; stir 30 seconds.",
                "Add tomatoes and simmer 8 minutes until sauce thickens.",
                "Add soy chunks; simmer 8 minutes. Serve over brown rice."
            ),
            storageNotes = "Refrigerates up to 3 days; flavour improves overnight.",
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "paneer_chickpea_power_bowl",
            name = "Paneer & Roasted Chickpea Power Bowl",
            description = "Dual protein sources: golden pan-seared paneer and crispy roasted chickpeas combine for 45g of protein — the highest-protein vegetarian lunch in the catalog. Brown rice and vegetables round out the macros.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "200g paneer (cubed)",
                "1 cup cooked chickpeas",
                "½ cup cooked brown rice",
                "2 cups baby spinach",
                "½ cup cherry tomatoes (halved)",
                "1 tbsp olive oil",
                "1 tsp smoked paprika",
                "½ tsp cumin",
                "½ tsp garlic powder",
                "1 tbsp lemon juice",
                "1 tbsp tahini",
                "Salt to taste"
            ),
            calories = 560, proteinGrams = 45.0, carbGrams = 50.0, fatGrams = 22.0, fiberGrams = 10.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Toss chickpeas with ½ tbsp olive oil, paprika, cumin, and salt; roast at 200°C for 20 minutes until crispy.",
                "Pan-sear paneer in remaining olive oil until golden on all sides.",
                "Whisk tahini with lemon juice and 2 tbsp water for dressing.",
                "Build bowl: brown rice, spinach, tomatoes, chickpeas, paneer; drizzle dressing."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "edamame_tofu_power_bowl",
            name = "Edamame & Crispy Tofu Power Bowl",
            description = "Two soy protein sources together: crispy pan-fried tofu and warm edamame over quinoa with hemp seeds for extra complete protein. Entirely vegan — the bowl for soy-tolerant plant-protein maximisers.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Gluten Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "200g firm tofu (pressed, cubed)",
                "1 cup shelled edamame (cooked)",
                "½ cup dry quinoa (cooked)",
                "2 tbsp hemp seeds",
                "1 cup cucumber (sliced)",
                "2 tbsp low-sodium soy sauce",
                "1 tsp sesame oil",
                "1 tsp rice vinegar",
                "1 tsp grated ginger",
                "Salt and pepper to taste"
            ),
            calories = 470, proteinGrams = 38.0, carbGrams = 42.0, fatGrams = 16.0, fiberGrams = 10.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Press tofu dry; pan-fry in sesame oil over high heat until golden, about 5 minutes.",
                "Cook quinoa per packet instructions; warm edamame.",
                "Whisk soy sauce, rice vinegar, and ginger for dressing.",
                "Build bowl: quinoa, cucumber, edamame, tofu; drizzle dressing and scatter hemp seeds."
            ),
            metabolicResetScore = 6,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "high_protein_tempeh_bowl",
            name = "Smoky Tempeh & Brown Rice Bowl",
            description = "Tempeh marinated in smoked paprika and tamari, pan-crisped, served over brown rice with roasted peppers. 40g of complete vegan protein from tempeh alone.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "200g tempeh (sliced)",
                "½ cup cooked brown rice",
                "1 red bell pepper (sliced)",
                "2 cups baby spinach",
                "1 tbsp tamari",
                "1 tsp smoked paprika",
                "1 tsp olive oil",
                "1 tsp apple cider vinegar",
                "Squeeze of lemon juice",
                "Salt to taste"
            ),
            calories = 480, proteinGrams = 40.0, carbGrams = 40.0, fatGrams = 18.0, fiberGrams = 8.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Whisk tamari, smoked paprika, and apple cider vinegar; toss with tempeh slices.",
                "Pan-fry tempeh in olive oil over medium-high heat 4–5 minutes per side until crispy.",
                "Char bell pepper strips in a dry pan or under the grill.",
                "Serve tempeh over brown rice with spinach and charred pepper; finish with lemon juice."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "paneer_edamame_bowl",
            name = "Paneer & Edamame Super Bowl",
            description = "Paneer and edamame are two of the densest vegetarian protein sources available. Together they hit 44g of protein in one lunch bowl with a sesame-ginger dressing tying the Indian-Japanese fusion together.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "150g paneer (cubed, pan-seared)",
                "1 cup shelled edamame (cooked)",
                "½ cup cooked brown rice",
                "1 cup cucumber (sliced)",
                "½ cup shredded purple cabbage",
                "1 tbsp low-sodium soy sauce",
                "1 tsp sesame oil",
                "1 tsp grated ginger",
                "1 tsp rice vinegar",
                "1 tsp sesame seeds"
            ),
            calories = 500, proteinGrams = 44.0, carbGrams = 40.0, fatGrams = 20.0, fiberGrams = 9.0,
            prepMinutes = 10, cookMinutes = 10,
            instructions = listOf(
                "Pan-sear paneer cubes in a dry non-stick pan over high heat until golden on all sides.",
                "Warm edamame.",
                "Whisk soy sauce, sesame oil, ginger, and rice vinegar for dressing.",
                "Build bowl: brown rice, cabbage, cucumber, edamame, paneer; drizzle dressing and garnish with sesame seeds."
            ),
            metabolicResetScore = 6,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 6,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "large_soy_chunk_curry",
            name = "High-Protein Soy Chunk Tikka Curry",
            description = "80g of dry soy chunks rehydrated in a rich tikka-style curry. Soy chunks are among the cheapest and highest-density plant protein sources — this bowl uses the maximum practical serving for a lunch portion.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "80g soy chunks (dry weight)",
                "1 can diced tomatoes",
                "1 medium onion (diced)",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "1 tsp tikka masala",
                "½ tsp turmeric",
                "½ tsp cumin",
                "1 tbsp olive oil",
                "Salt and fresh coriander to garnish"
            ),
            calories = 430, proteinGrams = 44.0, carbGrams = 30.0, fatGrams = 8.0, fiberGrams = 10.0,
            prepMinutes = 15, cookMinutes = 20,
            instructions = listOf(
                "Soak soy chunks in boiling water 10 minutes; drain and squeeze dry.",
                "Heat oil; sauté onion 5 minutes, add garlic, ginger, cumin, and turmeric.",
                "Add tomatoes and tikka masala; simmer 8 minutes.",
                "Add soy chunks; simmer 10 minutes until flavours absorb. Garnish with coriander."
            ),
            storageNotes = "Refrigerates up to 3 days.",
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "cottage_cheese_quinoa_bowl",
            name = "Cottage Cheese & Quinoa Protein Bowl",
            description = "A light but protein-dense bowl: creamy cottage cheese spooned over warm quinoa with roasted chickpeas and a lemon dressing. The cottage cheese melts slightly into the warm quinoa for a rich sauce-like texture.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup low-fat cottage cheese",
                "½ cup dry quinoa (cooked)",
                "1 cup cooked chickpeas",
                "1 cup cherry tomatoes (halved)",
                "1 cup cucumber (diced)",
                "2 tbsp fresh coriander (chopped)",
                "1 tbsp lemon juice",
                "1 tsp olive oil",
                "½ tsp cumin",
                "Salt and pepper to taste"
            ),
            calories = 480, proteinGrams = 36.0, carbGrams = 58.0, fatGrams = 8.0, fiberGrams = 12.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Cook quinoa per packet; while warm, toss with olive oil, lemon juice, cumin, salt, and pepper.",
                "Add cherry tomatoes, cucumber, chickpeas, and coriander; toss.",
                "Spoon cottage cheese over the top just before serving."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),
    )
}
