package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object LunchRecipes2 {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "paneer_tikka_salad",
            name = "Grilled Paneer Tikka Salad",
            description = "Charred paneer tikka chunks served over a crisp bed of cucumber, tomato, red onion, and roasted chickpeas with a lemon-cumin dressing. A 42 g protein lunch that feels restaurant-quality and comes together in 20 minutes.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "175g paneer (cubed)",
                "1 tsp tikka masala paste (or ½ tsp cumin + ½ tsp coriander + ¼ tsp chilli)",
                "½ cup roasted chickpeas (from can, patted dry and roasted 15 min at 200°C)",
                "1 cup cucumber (diced)",
                "1 medium tomato (diced)",
                "¼ red onion (thinly sliced)",
                "2 cups mixed salad leaves",
                "1 tsp olive oil",
                "1 tbsp lemon juice",
                "¼ tsp cumin powder",
                "Salt and black pepper to taste"
            ),
            calories = 490, proteinGrams = 42.0, carbGrams = 24.0, fatGrams = 26.0, fiberGrams = 6.0,
            prepMinutes = 10, cookMinutes = 10,
            instructions = listOf(
                "Toss paneer cubes with tikka paste; grill in a dry non-stick pan 3–4 minutes per side until charred.",
                "Roast chickpeas: toss drained chickpeas with a pinch of salt and cumin; roast at 200°C for 15 minutes.",
                "Make dressing: whisk lemon juice, olive oil, cumin, salt, and pepper.",
                "Arrange salad leaves, cucumber, tomato, and onion in a bowl.",
                "Top with paneer tikka and roasted chickpeas; drizzle dressing."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "moong_dal_khichdi",
            name = "Moong Dal Khichdi",
            description = "A one-pot Indian comfort dish of split yellow moong dal and basmati rice cooked with turmeric, cumin, and ghee. Exceptionally easy to digest, naturally high in protein and fibre, and an ideal insulin-resistance lunch.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "½ cup yellow moong dal (split, rinsed)",
                "¼ cup basmati rice (rinsed)",
                "2½ cups water",
                "½ tsp cumin seeds",
                "¼ tsp turmeric",
                "¼ tsp asafoetida (hing, optional)",
                "1 tsp ghee",
                "Salt to taste",
                "1 tbsp lemon juice (to serve)"
            ),
            calories = 380, proteinGrams = 30.0, carbGrams = 58.0, fatGrams = 5.0, fiberGrams = 10.0,
            prepMinutes = 5, cookMinutes = 25,
            instructions = listOf(
                "Heat ghee in a pressure cooker or heavy saucepan; add cumin seeds and hing.",
                "Add dal, rice, turmeric, and water; stir.",
                "Pressure cook 3 whistles OR simmer covered 25 minutes until porridge-like consistency.",
                "Season with salt; adjust water for desired thickness.",
                "Serve with a squeeze of lemon."
            ),
            storageNotes = "Thickens on cooling — add water when reheating.",
            metabolicResetScore = 9,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "egg_spinach_grain_bowl",
            name = "Six-Egg Spinach & Grain Bowl",
            description = "A meal-prep-first lunch bowl of farro, wilted spinach, and soft-boiled eggs dressed with tahini and lemon. The six-egg serving delivers 40 g of complete protein and keeps you satisfied for four to five hours.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Eggs",
            restrictions = listOf("Dairy Free", "Nut Free", "Soy Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "3 whole eggs + 3 egg whites (or 4 whole eggs)",
                "½ cup farro or hulled barley (cooked: ~1 cup)",
                "2 cups baby spinach",
                "1 garlic clove (minced)",
                "1 tbsp tahini",
                "1 tbsp lemon juice",
                "1 tsp olive oil",
                "Salt, black pepper, chilli flakes to taste"
            ),
            calories = 460, proteinGrams = 40.0, carbGrams = 40.0, fatGrams = 16.0, fiberGrams = 7.0,
            prepMinutes = 8, cookMinutes = 12,
            instructions = listOf(
                "Soft-boil eggs 7 minutes; cool in ice water and peel.",
                "Sauté garlic in olive oil 1 minute; add spinach and wilt 2 minutes.",
                "Whisk tahini with lemon juice and 2 tbsp warm water to make dressing.",
                "Layer farro, spinach, and halved eggs in a bowl; drizzle tahini dressing.",
                "Finish with chilli flakes and black pepper."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "tofu_peanut_noodle_bowl",
            name = "Tofu & Peanut Noodle Bowl",
            description = "Baked crispy tofu over soba noodles with a naturally sweetened peanut-ginger dressing, shredded purple cabbage, and edamame. A 36 g plant-protein lunch with bold, restaurant-quality flavours.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "200g extra-firm tofu (pressed and cubed)",
                "80g dry soba noodles (100% buckwheat for GF)",
                "½ cup shelled edamame (frozen, thawed)",
                "1 cup purple cabbage (shredded)",
                "1 spring onion (sliced)",
                "2 tbsp natural peanut butter",
                "1 tbsp soy sauce or tamari",
                "1 tsp grated fresh ginger",
                "1 tsp rice vinegar",
                "1 tsp sesame oil",
                "Chilli flakes to taste"
            ),
            calories = 510, proteinGrams = 36.0, carbGrams = 52.0, fatGrams = 18.0, fiberGrams = 7.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Press tofu; cut into cubes; bake at 200°C for 20 minutes until golden, turning once.",
                "Cook soba noodles per packet; drain and rinse under cold water.",
                "Whisk peanut butter, tamari, ginger, rice vinegar, sesame oil, and 2 tbsp warm water.",
                "Arrange noodles in a bowl; top with tofu, edamame, cabbage, and spring onion.",
                "Drizzle peanut dressing; garnish with chilli flakes."
            ),
            metabolicResetScore = 6,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 6,
            womensHealthScore = 8,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "chickpea_quinoa_power_bowl",
            name = "Spiced Chickpea & Quinoa Power Bowl",
            description = "A fully plant-based bowl of tri-colour quinoa, pan-fried spiced chickpeas, roasted cherry tomatoes, and tahini dressing. The chickpea–quinoa combination completes all essential amino acids and delivers 34 g of vegan protein.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "¾ cup dry quinoa (rinsed)",
                "1 can chickpeas (400g, drained and rinsed)",
                "½ cup cherry tomatoes (halved)",
                "½ tsp cumin",
                "½ tsp coriander",
                "¼ tsp smoked paprika",
                "1 tsp olive oil",
                "1 tbsp tahini",
                "1 tbsp lemon juice",
                "Salt to taste",
                "Fresh parsley or coriander to garnish"
            ),
            calories = 470, proteinGrams = 34.0, carbGrams = 64.0, fatGrams = 10.0, fiberGrams = 12.0,
            prepMinutes = 8, cookMinutes = 20,
            instructions = listOf(
                "Cook quinoa in 1½ cups water; bring to boil, reduce heat, cover and simmer 14 minutes.",
                "Toss chickpeas with cumin, coriander, paprika, oil, and salt.",
                "Pan-fry chickpeas on medium-high heat 8 minutes until lightly crispy.",
                "Roast cherry tomatoes alongside at 200°C for 10 minutes (optional).",
                "Make tahini dressing: whisk tahini, lemon juice, 2 tbsp water, and a pinch of salt.",
                "Bowl: quinoa base, chickpeas, tomatoes; drizzle dressing and garnish."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "paneer_matar_curry_bowl",
            name = "Paneer Matar Curry Bowl",
            description = "Classic paneer and green pea curry in a lightly spiced tomato gravy served over a small portion of brown rice. The dish keeps the glucoseImpactScore at 4 because brown rice and peas slow glucose absorption significantly.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "175g paneer (cubed)",
                "½ cup green peas (frozen)",
                "2 medium tomatoes (blended or finely chopped)",
                "1 small onion (finely diced)",
                "1 tsp ginger-garlic paste",
                "½ tsp cumin seeds",
                "½ tsp coriander powder",
                "¼ tsp turmeric",
                "¼ tsp garam masala",
                "1 tsp oil",
                "Salt to taste",
                "½ cup cooked brown rice (to serve)"
            ),
            calories = 500, proteinGrams = 38.0, carbGrams = 44.0, fatGrams = 22.0, fiberGrams = 6.0,
            prepMinutes = 8, cookMinutes = 20,
            instructions = listOf(
                "Heat oil; add cumin seeds and let them splutter.",
                "Add onion and cook 5 minutes; add ginger-garlic paste and cook 1 minute.",
                "Add blended tomato, turmeric, coriander, and salt; cook 8 minutes until oil separates.",
                "Add paneer and peas; simmer 6 minutes.",
                "Finish with garam masala; serve over brown rice."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 8,
            womensHealthScore = 6,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "greek_lentil_salad",
            name = "Greek-Style Lentil & Feta Salad",
            description = "A hearty Mediterranean salad of cooked green lentils, cherry tomatoes, Kalamata olives, cucumber, and crumbled feta with an oregano-lemon dressing. The lentil–feta pairing delivers 35 g of mixed plant and dairy protein with exceptional fibre.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup cooked green lentils (from ½ cup dry)",
                "60g feta cheese (crumbled)",
                "¾ cup cherry tomatoes (halved)",
                "1 cup cucumber (diced)",
                "¼ cup Kalamata olives (sliced)",
                "¼ red onion (finely sliced)",
                "1 tbsp olive oil",
                "2 tbsp lemon juice",
                "½ tsp dried oregano",
                "Salt and black pepper to taste"
            ),
            calories = 440, proteinGrams = 35.0, carbGrams = 42.0, fatGrams = 16.0, fiberGrams = 12.0,
            prepMinutes = 10, cookMinutes = 0,
            instructions = listOf(
                "Cook lentils: simmer in salted water 20 minutes until tender; drain and cool.",
                "Whisk olive oil, lemon juice, oregano, salt, and pepper to make dressing.",
                "Combine lentils, cherry tomatoes, cucumber, olives, and red onion in a large bowl.",
                "Drizzle dressing and toss gently.",
                "Top with crumbled feta before serving."
            ),
            storageNotes = "Store without feta; add just before eating. Keeps 3 days refrigerated.",
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "cottage_cheese_veggie_wrap",
            name = "High-Protein Cottage Cheese Veggie Wrap",
            description = "A whole-grain wrap loaded with herbed cottage cheese, roasted capsicum, cucumber, and spinach. A deceptively simple 36 g protein lunch that requires zero cooking and assembles in under five minutes.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "2 large whole-grain wraps or tortillas",
                "¾ cup low-fat cottage cheese (170g)",
                "½ cup roasted red capsicum (from jar, patted dry)",
                "1 cup baby spinach",
                "½ cup cucumber (sliced)",
                "¼ avocado (sliced)",
                "1 tsp lemon juice",
                "¼ tsp black pepper",
                "¼ tsp dried oregano",
                "Salt to taste"
            ),
            calories = 480, proteinGrams = 36.0, carbGrams = 52.0, fatGrams = 12.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Mix cottage cheese with lemon juice, pepper, oregano, and salt.",
                "Lay wraps flat; spread cottage cheese mixture down the centre.",
                "Layer spinach, capsicum, cucumber, and avocado.",
                "Fold sides in and roll tightly; slice in half diagonally."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "silken_tofu_miso_soup_bowl",
            name = "Silken Tofu Miso Soup & Brown Rice Bowl",
            description = "A large, warming miso soup with generous silken tofu, wakame seaweed, and spring onion served alongside a small bowl of brown rice. The generous tofu portion elevates this from a side soup to a 32 g protein main meal.",
            mealType = "Lunch",
            tags = listOf("Vegan", "Whole Foods"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "350g silken tofu (gently cubed)",
                "3 cups dashi stock or vegetable broth",
                "2 tbsp white miso paste",
                "2 tbsp dried wakame seaweed (rehydrated)",
                "2 spring onions (sliced)",
                "½ cup cooked brown rice (to serve)",
                "1 tsp sesame oil (finish)"
            ),
            calories = 390, proteinGrams = 32.0, carbGrams = 40.0, fatGrams = 10.0, fiberGrams = 4.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Bring broth to a gentle simmer; do NOT boil (destroys miso probiotic cultures).",
                "Dissolve miso paste in a ladleful of warm broth, then stir back into the pot.",
                "Add silken tofu and rehydrated wakame; warm 3 minutes without stirring.",
                "Ladle into a deep bowl; top with spring onion and a drizzle of sesame oil.",
                "Serve alongside brown rice."
            ),
            metabolicResetScore = 6,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 6,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "rajma_brown_rice",
            name = "Protein Rajma with Brown Rice",
            description = "Classic Punjabi kidney bean curry slow-simmered with whole spices, onion, and tomato, served over brown rice. The dish pairs two plant proteins for an exceptional 34 g amino acid profile and is a benchmark insulin-resistance lunch.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 can kidney beans (400g, drained and rinsed — or ¾ cup dry cooked)",
                "2 medium tomatoes (blended)",
                "1 medium onion (finely diced)",
                "1 tsp ginger-garlic paste",
                "1 bay leaf",
                "1 tsp cumin seeds",
                "½ tsp coriander powder",
                "¼ tsp turmeric",
                "¼ tsp garam masala",
                "1 tsp oil",
                "Salt to taste",
                "¾ cup cooked brown rice (to serve)"
            ),
            calories = 480, proteinGrams = 34.0, carbGrams = 78.0, fatGrams = 5.0, fiberGrams = 14.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Heat oil; add bay leaf and cumin; add onion and cook 6 minutes until golden.",
                "Add ginger-garlic paste and cook 1 minute; add blended tomato.",
                "Cook tomato 8 minutes until thick and oil separates; add turmeric and coriander.",
                "Add kidney beans and 1 cup water; simmer 12 minutes.",
                "Mash a few beans to thicken; finish with garam masala and salt.",
                "Serve over brown rice."
            ),
            storageNotes = "Rajma improves overnight — make in advance and reheat.",
            metabolicResetScore = 9,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 9,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "skyr_protein_lunch_bowl",
            name = "Skyr, Roasted Vegetables & Quinoa Bowl",
            description = "A cold bowl of quinoa topped with roasted courgette and pepper, a generous dollop of plain skyr, and a dressing of lemon and fresh herbs. Skyr brings 18 g of very slow-digesting Icelandic dairy protein; combined with quinoa this is a 35 g protein lunch with an excellent insulin response.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "½ cup dry quinoa (rinsed)",
                "170g plain skyr (or non-fat Greek yogurt)",
                "1 small courgette (sliced into rounds)",
                "½ red bell pepper (sliced)",
                "1 tsp olive oil",
                "½ tsp smoked paprika",
                "1 tbsp lemon juice",
                "1 tbsp fresh dill or mint",
                "Salt and black pepper to taste"
            ),
            calories = 430, proteinGrams = 35.0, carbGrams = 48.0, fatGrams = 8.0, fiberGrams = 6.0,
            prepMinutes = 8, cookMinutes = 20,
            instructions = listOf(
                "Cook quinoa in 1 cup water 14 minutes; cool to room temperature.",
                "Toss courgette and pepper with olive oil, paprika, and salt.",
                "Roast at 200°C for 15–18 minutes until caramelised; cool.",
                "Assemble: quinoa base, roasted vegetables, and a large scoop of skyr.",
                "Drizzle with lemon juice and scatter fresh dill."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "hemp_edamame_power_bowl",
            name = "Hemp Seed & Edamame Power Bowl",
            description = "A cold grain bowl built on a base of buckwheat and topped with shelled edamame, hemp seeds, shredded red cabbage, and avocado — four of the most nutrient-dense plant foods. At 38 g of plant protein and an antiInflammatoryScore of 9, this is the top anti-inflammatory lunch in the plan.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup dry buckwheat groats (cooked: 1 cup)",
                "1 cup shelled edamame (frozen, thawed)",
                "3 tbsp hemp seeds (shelled)",
                "1 cup red cabbage (finely shredded)",
                "¼ avocado (sliced)",
                "1 tbsp tamari",
                "1 tsp sesame oil",
                "1 tbsp rice vinegar",
                "¼ tsp ginger powder",
                "Sesame seeds to garnish"
            ),
            calories = 500, proteinGrams = 38.0, carbGrams = 52.0, fatGrams = 18.0, fiberGrams = 9.0,
            prepMinutes = 8, cookMinutes = 12,
            instructions = listOf(
                "Cook buckwheat: bring 1 cup water to boil; add buckwheat, cover and simmer 10 minutes.",
                "Whisk tamari, sesame oil, rice vinegar, and ginger powder to make dressing.",
                "Build bowl: buckwheat base, edamame, red cabbage, hemp seeds, avocado.",
                "Drizzle dressing; garnish with sesame seeds."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 7,
            womensHealthScore = 9,
            glucoseImpactScore = 3
        ),
    )
}
