package com.dailyhealthcoach.ui.recipes

object RecipeCatalog {

    val ALL: List<Recipe> = listOf(

        // ── BREAKFASTS ─────────────────────────────────────────────────────────

        Recipe(
            id = "greek_yogurt_bowl",
            name = "High-Protein Greek Yogurt Bowl",
            description = "Thick Greek yogurt boosted with protein powder, layered with antioxidant-rich berries and a drizzle of honey for natural sweetness.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup non-fat Greek yogurt",
                "1 scoop protein powder",
                "½ cup mixed berries",
                "2 tbsp low-fat granola",
                "1 tbsp honey"
            ),
            calories = 380, proteinGrams = 38.0, carbGrams = 42.0, fatGrams = 4.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add Greek yogurt to a bowl.",
                "Stir in protein powder until smooth.",
                "Top with mixed berries and granola.",
                "Drizzle honey and serve immediately."
            ),
            storageNotes = "Best consumed fresh. Do not pre-mix protein powder.",
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "protein_oats",
            name = "Protein Overnight Oats with Berries",
            description = "Creamy overnight oats with protein powder, topped with anti-inflammatory berries and warming cinnamon. Make the night before for a stress-free morning.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup rolled oats",
                "1 scoop protein powder",
                "1 cup plant milk",
                "1 banana (sliced)",
                "½ cup blueberries",
                "1 tbsp almond butter",
                "½ tsp cinnamon"
            ),
            calories = 440, proteinGrams = 32.0, carbGrams = 58.0, fatGrams = 9.0, fiberGrams = 6.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Mix oats, protein powder, and plant milk in a jar.",
                "Stir well, cover, and refrigerate overnight.",
                "In the morning, top with banana, blueberries, almond butter, and cinnamon."
            ),
            mealPrepNotes = "Prepare 4 jars on Sunday for the whole week.",
            antiInflammatoryScore = 7
        ),

        Recipe(
            id = "egg_white_scramble",
            name = "Egg White Veggie Scramble",
            description = "Light, high-protein scramble loaded with colourful vegetables. Low in calories and carbs — ideal for blood sugar management.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein", "Low Calorie"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "6 egg whites",
                "½ cup fresh spinach",
                "¼ cup bell peppers (diced)",
                "¼ cup mushrooms (sliced)",
                "1 tsp olive oil",
                "Salt, pepper, chilli flakes to taste"
            ),
            calories = 180, proteinGrams = 26.0, carbGrams = 8.0, fatGrams = 3.0, fiberGrams = 2.0,
            prepMinutes = 5, cookMinutes = 8,
            instructions = listOf(
                "Heat olive oil in a non-stick pan over medium heat.",
                "Sauté bell peppers and mushrooms until soft, about 3 minutes.",
                "Add spinach and cook 1 minute until wilted.",
                "Pour in egg whites; scramble gently until just set.",
                "Season and serve immediately."
            ),
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "moong_dal_chilla",
            name = "Savory Moong Dal Chilla",
            description = "Protein-rich savory pancakes made from soaked split moong dal blended with ginger and spices. A classic Indian breakfast that keeps blood sugar steady.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup split moong dal (soaked 4 hours, drained)",
                "1 green chilli (chopped)",
                "1 tsp fresh ginger (grated)",
                "½ tsp cumin seeds",
                "¼ tsp turmeric",
                "½ cup fresh spinach (chopped)",
                "Salt to taste",
                "1 tsp olive oil (for cooking)"
            ),
            calories = 310, proteinGrams = 22.0, carbGrams = 40.0, fatGrams = 4.0, fiberGrams = 9.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Blend soaked moong dal with chilli, ginger, cumin, turmeric, and a splash of water to a thick batter.",
                "Fold in chopped spinach and season with salt.",
                "Heat a non-stick pan over medium heat; brush with a little oil.",
                "Pour a ladle of batter, spread into a thin circle, and cook 3 minutes per side until golden.",
                "Serve with mint chutney or low-fat yogurt."
            ),
            storageNotes = "Batter keeps in the fridge for 2 days.",
            mealPrepNotes = "Soak dal overnight for a quick morning cook.",
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "masala_omelette",
            name = "Masala Herb Omelette",
            description = "A fluffy 3-egg omelette seasoned with Indian spices, fresh tomatoes, and coriander. High in protein and naturally low in carbs.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein", "Low Carb"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "3 whole eggs",
                "1 medium tomato (diced)",
                "1 green chilli (finely chopped)",
                "2 tbsp fresh coriander (chopped)",
                "¼ tsp turmeric",
                "¼ tsp cumin",
                "1 tsp olive oil",
                "Salt and black pepper to taste"
            ),
            calories = 260, proteinGrams = 20.0, carbGrams = 6.0, fatGrams = 17.0, fiberGrams = 1.5,
            prepMinutes = 5, cookMinutes = 8,
            instructions = listOf(
                "Whisk eggs with turmeric, cumin, salt, and pepper.",
                "Heat olive oil in a pan over medium heat.",
                "Pour in egg mixture and let it set slightly at the edges.",
                "Scatter tomato, green chilli, and coriander over one half.",
                "Fold and cook 2 more minutes. Serve hot."
            ),
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "golden_milk_oats",
            name = "Golden Milk Overnight Oats",
            description = "Overnight oats infused with turmeric and ginger — two of the most potent anti-inflammatory compounds in the kitchen. Creamy, warming, and ready in the morning.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup rolled oats",
                "1 scoop vanilla protein powder",
                "1 cup unsweetened almond milk",
                "½ tsp turmeric",
                "¼ tsp ground ginger",
                "¼ tsp cinnamon",
                "Pinch of black pepper",
                "1 tbsp chia seeds",
                "1 tsp maple syrup"
            ),
            calories = 390, proteinGrams = 28.0, carbGrams = 50.0, fatGrams = 8.0, fiberGrams = 7.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Combine all ingredients in a jar and stir well.",
                "Refrigerate overnight (at least 6 hours).",
                "Stir before eating; top with sliced mango or banana if desired."
            ),
            mealPrepNotes = "Make a batch of 5 jars for the whole work week.",
            antiInflammatoryScore = 9
        ),

        Recipe(
            id = "green_protein_smoothie",
            name = "Green Protein & Ginger Smoothie",
            description = "A vibrant green smoothie powered by spinach, ginger, and protein powder. Anti-inflammatory ginger and leafy greens pair with banana for natural sweetness.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup fresh spinach",
                "1 frozen banana",
                "1 tsp fresh ginger (grated)",
                "1 scoop vanilla protein powder",
                "1 cup unsweetened almond milk",
                "1 tbsp chia seeds",
                "½ cup frozen pineapple chunks"
            ),
            calories = 340, proteinGrams = 30.0, carbGrams = 38.0, fatGrams = 7.0, fiberGrams = 6.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to a blender.",
                "Blend on high for 60 seconds until completely smooth.",
                "Taste and adjust ginger or sweetness as preferred.",
                "Pour and serve immediately."
            ),
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "besan_cheela",
            name = "High-Protein Besan Cheela",
            description = "Chickpea flour pancakes seasoned with ajwain and asafoetida — a traditional Indian breakfast that packs impressive plant protein and soluble fiber for sustained energy.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup chickpea flour (besan)",
                "¼ cup low-fat yogurt",
                "¼ tsp ajwain (carom seeds)",
                "¼ tsp turmeric",
                "Pinch of asafoetida",
                "½ cup onion (finely diced)",
                "½ cup tomato (finely diced)",
                "1 green chilli (minced)",
                "Salt to taste",
                "1 tsp olive oil"
            ),
            calories = 300, proteinGrams = 18.0, carbGrams = 36.0, fatGrams = 7.0, fiberGrams = 7.0,
            prepMinutes = 10, cookMinutes = 10,
            instructions = listOf(
                "Whisk chickpea flour with yogurt and enough water to form a pourable batter.",
                "Stir in ajwain, turmeric, asafoetida, onion, tomato, chilli, and salt.",
                "Heat a non-stick pan over medium heat; brush with oil.",
                "Pour a ladleful, spread thin, and cook 3 minutes per side until crisp and golden.",
                "Serve with green chutney."
            ),
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "avocado_egg_toast",
            name = "Avocado & Poached Egg Whole-Grain Toast",
            description = "Creamy avocado on whole-grain toast with a perfectly poached egg. Avocado's monounsaturated fats are anti-inflammatory; the egg provides complete protein.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "2 slices whole-grain bread",
                "½ ripe avocado",
                "2 eggs",
                "1 tsp lemon juice",
                "Pinch of red chilli flakes",
                "Salt and black pepper to taste",
                "1 tbsp white vinegar (for poaching)"
            ),
            calories = 400, proteinGrams = 22.0, carbGrams = 30.0, fatGrams = 22.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 8,
            instructions = listOf(
                "Toast bread until golden.",
                "Mash avocado with lemon juice, salt, and pepper; spread on toast.",
                "Bring a pot of water to a gentle simmer; add vinegar.",
                "Crack each egg into a small cup and slide into the water; poach 3–4 minutes.",
                "Place eggs on avocado toast; season with chilli flakes and serve."
            ),
            metabolicResetScore = 7, antiInflammatoryScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "berry_baked_oats",
            name = "Mixed Berry Baked Protein Oats",
            description = "Warm, comforting baked oats studded with antioxidant-rich berries and enriched with protein powder. Prep once; enjoy all week.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup rolled oats",
                "1 scoop protein powder",
                "1 cup unsweetened almond milk",
                "1 egg",
                "1 tbsp honey",
                "½ tsp baking powder",
                "½ tsp cinnamon",
                "¾ cup mixed frozen berries",
                "1 tbsp walnuts (chopped)"
            ),
            calories = 420, proteinGrams = 30.0, carbGrams = 54.0, fatGrams = 10.0, fiberGrams = 7.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Preheat oven to 190 °C (375 °F).",
                "Mix oats, protein powder, baking powder, and cinnamon in a baking dish.",
                "Whisk together almond milk, egg, and honey; pour over oat mixture.",
                "Scatter berries and walnuts on top.",
                "Bake 25 minutes until set and golden at the edges.",
                "Cut into portions; serve warm or cold."
            ),
            storageNotes = "Refrigerate up to 5 days; reheat with a splash of milk.",
            mealPrepNotes = "Bake a full tray Sunday evening for weekday breakfasts.",
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "paneer_bhurji",
            name = "Paneer Bhurji (Spiced Scrambled Paneer)",
            description = "The Indian equivalent of scrambled eggs — crumbled paneer tossed with onions, tomatoes, and aromatic spices. High in protein and ready in under 15 minutes.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "200g paneer (crumbled)",
                "1 medium onion (finely diced)",
                "1 medium tomato (diced)",
                "1 green chilli (minced)",
                "½ tsp cumin seeds",
                "¼ tsp turmeric",
                "½ tsp garam masala",
                "1 tbsp fresh coriander (chopped)",
                "1 tsp olive oil",
                "Salt to taste"
            ),
            calories = 360, proteinGrams = 28.0, carbGrams = 12.0, fatGrams = 22.0, fiberGrams = 2.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Heat oil in a pan; add cumin seeds and let them splutter.",
                "Add onion and cook until translucent, about 3 minutes.",
                "Add chilli and tomato; cook until soft and slightly dry.",
                "Add turmeric and crumbled paneer; toss to combine.",
                "Cook 3 minutes, sprinkle garam masala and coriander, and serve."
            ),
            metabolicResetScore = 8
        ),

        Recipe(
            id = "quinoa_breakfast_bowl",
            name = "Quinoa Protein Breakfast Bowl",
            description = "Warm cooked quinoa — a complete plant protein — topped with berries, crushed walnuts, and a drizzle of honey. Sustained energy from morning to midday.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup dry quinoa (cooked)",
                "1 cup unsweetened almond milk",
                "½ cup blueberries",
                "2 tbsp walnuts (crushed)",
                "1 tbsp chia seeds",
                "1 scoop vanilla protein powder",
                "1 tsp honey"
            ),
            calories = 420, proteinGrams = 28.0, carbGrams = 52.0, fatGrams = 12.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 15,
            instructions = listOf(
                "Cook quinoa in almond milk over medium heat, stirring, about 15 minutes until creamy.",
                "Remove from heat; stir in protein powder.",
                "Top with blueberries, walnuts, chia seeds, and honey.",
                "Serve warm."
            ),
            mealPrepNotes = "Cook a large batch of quinoa and refrigerate up to 5 days.",
            metabolicResetScore = 8, antiInflammatoryScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "blueberry_chia_bowl",
            name = "Blueberry Walnut Chia Bowl",
            description = "A thick overnight chia pudding loaded with omega-3 fats, topped with blueberries and walnuts — two of the most well-researched anti-inflammatory foods.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "4 tbsp chia seeds",
                "1 cup unsweetened almond milk",
                "1 scoop vanilla protein powder",
                "½ cup blueberries (fresh or frozen)",
                "2 tbsp walnuts (chopped)",
                "1 tsp honey",
                "¼ tsp cinnamon"
            ),
            calories = 380, proteinGrams = 24.0, carbGrams = 34.0, fatGrams = 18.0, fiberGrams = 14.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Whisk protein powder into almond milk until smooth.",
                "Stir in chia seeds and refrigerate overnight (or at least 4 hours).",
                "Top with blueberries, walnuts, honey, and cinnamon before serving."
            ),
            storageNotes = "Keeps refrigerated for 4 days.",
            antiInflammatoryScore = 9
        ),

        // ── LUNCHES ───────────────────────────────────────────────────────────

        Recipe(
            id = "paneer_burrito_bowl",
            name = "Paneer Burrito Bowl",
            description = "Pan-seared golden paneer cubes over brown rice and black beans — a hearty high-protein bowl with protein from two sources and blood-sugar-friendly fiber.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
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
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "chickpea_salad",
            name = "Mediterranean Chickpea Protein Salad",
            description = "Bright, vibrant salad with protein-dense chickpeas, crunchy cucumber, and cherry tomatoes tossed in a lemon-olive oil dressing. Anti-inflammatory and blood-sugar friendly.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
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
            metabolicResetScore = 8, antiInflammatoryScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "rajma_salad",
            name = "Spiced Rajma Bean Salad",
            description = "Cooked red kidney beans (rajma) tossed with crisp vegetables, roasted cumin, and fresh lime — a protein-and-fiber powerhouse that keeps hunger at bay.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
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
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "quinoa_power_bowl",
            name = "Roasted Chickpea & Quinoa Power Bowl",
            description = "Complete protein from quinoa paired with crispy roasted chickpeas, baby spinach, and a silky tahini-lemon dressing. Hits metabolic reset and anti-inflammatory goals simultaneously.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
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
                "Toss chickpeas with olive oil, paprika, and salt; roast at 200 °C for 20 minutes until crispy.",
                "Cook quinoa per packet instructions.",
                "Whisk tahini with lemon juice, garlic, and 2 tbsp water to make dressing.",
                "Build bowl: quinoa, spinach, tomatoes, chickpeas; drizzle with dressing."
            ),
            metabolicResetScore = 9, antiInflammatoryScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "turmeric_lentil_soup",
            name = "Golden Turmeric Red Lentil Soup",
            description = "A restorative, deeply golden soup with red lentils cooked in turmeric, ginger, and coconut milk. Turmeric and ginger are science-backed anti-inflammatory powerhouses.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
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
            antiInflammatoryScore = 10
        ),

        Recipe(
            id = "spinach_chickpea_curry",
            name = "Quick Spinach & Chickpea Curry",
            description = "A 20-minute curry packing iron-rich spinach and plant-protein chickpeas in a spiced tomato base. Garlic, ginger, and cumin give it a powerful anti-inflammatory profile.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
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
            antiInflammatoryScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "sweet_potato_lentil",
            name = "Roasted Sweet Potato & Red Lentil Bowl",
            description = "Caramelised roasted sweet potato over spiced red lentils with a lemon-tahini drizzle. Sweet potatoes are rich in beta-carotene; lentils provide sustained protein and fiber.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
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
                "Toss sweet potato with olive oil, paprika, salt; roast at 200 °C for 25 minutes.",
                "Cook red lentils in 1.5 cups water until soft, about 15 minutes.",
                "Whisk tahini with lemon juice and 2 tbsp water to make dressing.",
                "Plate spinach, lentils, roasted sweet potato; drizzle with tahini dressing."
            ),
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "greek_paneer_bowl",
            name = "Greek-Style Paneer & Quinoa Bowl",
            description = "Pan-seared paneer over quinoa with olives, cucumber, and a lemon-herb dressing — a Mediterranean-Indian fusion bowl high in protein and satisfying healthy fats.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
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
            metabolicResetScore = 8
        ),

        Recipe(
            id = "edamame_rice_bowl",
            name = "Ginger Edamame & Brown Rice Bowl",
            description = "Shelled edamame tossed with sesame-ginger dressing over brown rice — a complete protein with anti-inflammatory ginger and heart-healthy omega-6 from edamame.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
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
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "soya_chunk_salad",
            name = "Soya Chunk & Vegetable Protein Salad",
            description = "Rehydrated soya chunks — one of the highest plant protein sources — tossed with crisp vegetables, herbs, and a tangy lime dressing.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
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
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "walnut_beet_salad",
            name = "Roasted Beetroot & Walnut Paneer Salad",
            description = "Earthy roasted beets paired with creamy paneer, crunchy walnuts, and peppery rocket. Beets are natural nitric-oxide boosters; walnuts are one of the top anti-inflammatory nuts.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
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
                "Toss beetroot cubes with a little olive oil; roast at 200 °C for 25–30 minutes until tender.",
                "Sear paneer in a dry pan until golden.",
                "Whisk olive oil, balsamic, and honey for dressing.",
                "Arrange rocket, beets, and paneer on a plate; scatter walnuts and drizzle dressing."
            ),
            antiInflammatoryScore = 9
        ),

        Recipe(
            id = "chole_bowl",
            name = "Chole Protein Bowl",
            description = "Chickpeas simmered in a rich, spiced tomato masala served over brown rice with cooling cucumber raita. A high-fiber, high-protein classic that respects blood sugar.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
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
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "miso_tofu_bowl",
            name = "Miso-Ginger Tofu Soba Bowl",
            description = "Silken tofu and buckwheat soba in a miso-ginger broth with bok choy. Miso delivers probiotics; ginger and garlic are anti-inflammatory compounds in every sip.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
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
            antiInflammatoryScore = 8
        ),

        // ── DINNERS ───────────────────────────────────────────────────────────

        Recipe(
            id = "tofu_stir_fry",
            name = "Tofu & Broccoli Stir-Fry Bowl",
            description = "Crispy tofu and broccoli tossed in a ginger-garlic soy sauce over brown rice. Ginger and garlic are proven anti-inflammatory agents; broccoli provides sulforaphane.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "200g firm tofu (pressed, cubed)",
                "2 cups broccoli florets",
                "½ cup snap peas",
                "½ cup cooked brown rice",
                "2 tbsp low-sodium soy sauce",
                "1 tbsp sesame oil",
                "1 tsp fresh ginger (grated)",
                "2 garlic cloves (minced)",
                "1 tsp cornstarch"
            ),
            calories = 420, proteinGrams = 26.0, carbGrams = 44.0, fatGrams = 14.0, fiberGrams = 7.0,
            prepMinutes = 15, cookMinutes = 15,
            instructions = listOf(
                "Press tofu dry; toss with cornstarch and a pinch of salt.",
                "Pan-fry in sesame oil over high heat until golden, about 5 minutes; set aside.",
                "In the same pan, sauté garlic and ginger 1 minute.",
                "Add broccoli and snap peas; stir-fry 4 minutes.",
                "Return tofu; add soy sauce, toss to coat. Serve over brown rice."
            ),
            antiInflammatoryScore = 9
        ),

        Recipe(
            id = "lentil_rice_bowl",
            name = "Turmeric Lentil & Basmati Rice Bowl",
            description = "Comforting khichdi-style bowl of red lentils and basmati rice simmered with turmeric and cumin. Turmeric's curcumin is one of the most studied anti-inflammatory compounds.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup red lentils (rinsed)",
                "½ cup basmati rice",
                "1½ cups vegetable broth",
                "1 tsp cumin seeds",
                "1 tsp turmeric",
                "2 garlic cloves (minced)",
                "1 tbsp olive oil",
                "Salt and pepper to taste",
                "Lemon wedge to serve"
            ),
            calories = 460, proteinGrams = 20.0, carbGrams = 78.0, fatGrams = 6.0, fiberGrams = 12.0,
            prepMinutes = 5, cookMinutes = 25,
            instructions = listOf(
                "Heat oil; add cumin seeds, let them splutter, then add garlic.",
                "Add turmeric, lentils, rice, and broth.",
                "Bring to a boil, cover, and simmer on low 20 minutes until soft.",
                "Season well; serve with lemon wedge."
            ),
            storageNotes = "Refrigerates up to 4 days; add a splash of water when reheating.",
            metabolicResetScore = 8, antiInflammatoryScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "methi_dal",
            name = "Methi Dal (Fenugreek Lentil Curry)",
            description = "Red lentils cooked with fresh fenugreek leaves, a traditional ingredient in Ayurveda known to support blood sugar regulation. A dish used in metabolic reset protocols for generations.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "½ cup red lentils (rinsed)",
                "1 cup fresh fenugreek leaves (methi, roughly chopped)",
                "1 medium tomato (diced)",
                "½ onion (diced)",
                "2 garlic cloves (minced)",
                "1 tsp cumin seeds",
                "½ tsp turmeric",
                "½ tsp coriander powder",
                "1 tbsp olive oil",
                "Salt to taste"
            ),
            calories = 370, proteinGrams = 20.0, carbGrams = 50.0, fatGrams = 8.0, fiberGrams = 13.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Cook lentils in 2 cups water with turmeric until soft, about 15 minutes.",
                "Heat oil; sauté cumin seeds, then onion until golden.",
                "Add garlic, tomato, and coriander; cook until tomatoes break down.",
                "Add methi leaves and cook 3 minutes.",
                "Combine with cooked lentils; simmer together 5 minutes. Season and serve."
            ),
            metabolicResetScore = 10, isGlucoseConscious = true
        ),

        Recipe(
            id = "soya_keema",
            name = "Soya Keema Masala",
            description = "Minced soya (textured vegetable protein) cooked like keema with green peas, tomatoes, and whole spices. Among the highest plant-protein dinners in this library.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup soya mince (TVP, rehydrated)",
                "½ cup green peas",
                "1 medium onion (finely diced)",
                "1 can diced tomatoes",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "1 tsp cumin",
                "1 tsp coriander powder",
                "½ tsp garam masala",
                "1 tbsp olive oil",
                "Salt to taste"
            ),
            calories = 400, proteinGrams = 40.0, carbGrams = 30.0, fatGrams = 10.0, fiberGrams = 10.0,
            prepMinutes = 10, cookMinutes = 20,
            instructions = listOf(
                "Rehydrate soya mince in hot water 10 minutes; squeeze out moisture.",
                "Heat oil; sauté onion 5 minutes until golden.",
                "Add garlic, ginger, cumin, and coriander; cook 2 minutes.",
                "Add tomatoes and cook until sauce thickens.",
                "Add soya mince and peas; simmer 10 minutes. Finish with garam masala."
            ),
            storageNotes = "Refrigerates for 3 days; great in wraps next day.",
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "tofu_palak",
            name = "Palak Tofu (Spinach & Tofu Curry)",
            description = "Firm tofu simmered in a velvety spiced spinach gravy. Spinach provides iron and folate; tofu and turmeric deliver a potent anti-inflammatory combination.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "200g firm tofu (cubed)",
                "3 cups fresh spinach (blanched)",
                "1 medium onion (chopped)",
                "1 medium tomato (chopped)",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "½ tsp turmeric",
                "½ tsp cumin",
                "½ tsp garam masala",
                "1 tbsp olive oil",
                "Salt to taste"
            ),
            calories = 340, proteinGrams = 22.0, carbGrams = 20.0, fatGrams = 18.0, fiberGrams = 6.0,
            prepMinutes = 10, cookMinutes = 20,
            instructions = listOf(
                "Pan-fry tofu in 1 tsp oil until golden; set aside.",
                "Blend blanched spinach to a smooth purée.",
                "Heat remaining oil; sauté onion, garlic, and ginger 5 minutes.",
                "Add cumin, turmeric, tomato; cook until soft.",
                "Add spinach purée; simmer 5 minutes. Add tofu and garam masala; heat through."
            ),
            antiInflammatoryScore = 9
        ),

        Recipe(
            id = "broccoli_quinoa",
            name = "Roasted Broccoli & Quinoa Power Bowl",
            description = "Caramelised roasted broccoli over fluffy quinoa with a lemon-tahini dressing and hemp seeds. Broccoli's sulforaphane is one of the most potent anti-inflammatory phytonutrients.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "2 cups broccoli florets",
                "½ cup dry quinoa (cooked)",
                "2 tbsp tahini",
                "1 tbsp lemon juice",
                "1 garlic clove (minced)",
                "2 tbsp hemp seeds",
                "1 tbsp olive oil",
                "½ tsp chilli flakes",
                "Salt to taste"
            ),
            calories = 460, proteinGrams = 24.0, carbGrams = 50.0, fatGrams = 20.0, fiberGrams = 10.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Toss broccoli with olive oil, chilli flakes, and salt; roast at 200 °C for 20–25 minutes until crispy at edges.",
                "Cook quinoa in 1 cup water until fluffy.",
                "Whisk tahini, lemon juice, garlic, and 2 tbsp water to a smooth dressing.",
                "Plate quinoa and broccoli; drizzle dressing and scatter hemp seeds."
            ),
            antiInflammatoryScore = 9
        ),

        Recipe(
            id = "kitchari",
            name = "Kitchari (Mung Bean & Rice Cleanse Bowl)",
            description = "The Ayurvedic healing bowl — split mung dal and basmati rice cooked together with turmeric, cumin, and a touch of ghee. Deeply nourishing, easy to digest, and restorative.",
            mealType = "Dinner",
            tags = listOf("Vegetarian", "Whole Foods"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup split yellow mung dal",
                "¼ cup basmati rice",
                "1 tsp cumin seeds",
                "½ tsp turmeric",
                "½ tsp coriander powder",
                "1 tsp fresh ginger (grated)",
                "1 tbsp ghee",
                "3 cups water",
                "Salt to taste",
                "Fresh coriander to garnish"
            ),
            calories = 390, proteinGrams = 18.0, carbGrams = 64.0, fatGrams = 8.0, fiberGrams = 10.0,
            prepMinutes = 5, cookMinutes = 30,
            instructions = listOf(
                "Heat ghee in a pot; add cumin seeds and let them splutter.",
                "Add ginger, turmeric, and coriander; stir 30 seconds.",
                "Add rinsed mung dal, rice, and water; bring to a boil.",
                "Reduce to low, cover, and cook 25–30 minutes until soft and porridge-like.",
                "Season with salt; garnish with coriander."
            ),
            storageNotes = "Best eaten fresh; thickens as it sits — add water when reheating.",
            metabolicResetScore = 8, antiInflammatoryScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "walnut_mushroom_bowl",
            name = "Walnut & Portobello Mushroom Brown Rice Bowl",
            description = "Meaty portobello mushrooms and crunchy walnuts over brown rice in a tamari-thyme sauce. Walnuts are the richest nut source of anti-inflammatory omega-3 ALA.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "2 large portobello mushrooms (sliced)",
                "½ cup cooked brown rice",
                "3 tbsp walnuts (roughly chopped)",
                "2 garlic cloves (minced)",
                "1 tbsp low-sodium tamari",
                "1 tbsp olive oil",
                "1 tsp fresh thyme",
                "1 cup baby spinach",
                "Salt and pepper to taste"
            ),
            calories = 460, proteinGrams = 16.0, carbGrams = 54.0, fatGrams = 22.0, fiberGrams = 8.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Sauté garlic in olive oil 1 minute; add mushrooms and cook until golden.",
                "Add tamari and thyme; cook 3 more minutes.",
                "Toss in walnuts; stir 1 minute.",
                "Serve over brown rice with a bed of baby spinach."
            ),
            antiInflammatoryScore = 9
        ),

        Recipe(
            id = "paneer_tikka_wrap",
            name = "Paneer Tikka Whole-Wheat Wrap",
            description = "Charred paneer tikka with crisp vegetables and cooling mint-coriander chutney in a whole-wheat roti. A satisfying high-protein dinner that fits within a metabolic reset plan.",
            mealType = "Dinner",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "200g paneer (cubed)",
                "½ cup low-fat yogurt",
                "1 tsp tikka masala",
                "½ tsp turmeric",
                "½ tsp cumin",
                "2 whole-wheat rotis",
                "½ cup red onion (sliced)",
                "½ cup cucumber (sliced)",
                "2 tbsp mint-coriander chutney",
                "1 tsp lemon juice"
            ),
            calories = 540, proteinGrams = 34.0, carbGrams = 50.0, fatGrams = 20.0, fiberGrams = 6.0,
            prepMinutes = 15, cookMinutes = 15,
            instructions = listOf(
                "Marinate paneer in yogurt, tikka masala, turmeric, cumin, and lemon juice for 10 minutes.",
                "Grill or pan-sear paneer on high heat until charred at edges, about 4 minutes per side.",
                "Warm rotis; spread chutney over each.",
                "Layer with onion, cucumber, and paneer tikka; roll and serve."
            ),
            metabolicResetScore = 8
        ),

        Recipe(
            id = "omega_tofu_bowl",
            name = "Omega-3 Chia-Crusted Tofu Bowl",
            description = "Tofu encrusted in chia seeds for a crunchy omega-3 shell, served over quinoa with avocado and lemon tahini. A powerhouse of anti-inflammatory fats.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "200g extra-firm tofu (sliced)",
                "3 tbsp chia seeds",
                "½ cup dry quinoa (cooked)",
                "½ ripe avocado (sliced)",
                "2 tbsp tahini",
                "1 tbsp lemon juice",
                "1 cup baby spinach",
                "1 tsp olive oil",
                "Salt to taste"
            ),
            calories = 530, proteinGrams = 28.0, carbGrams = 44.0, fatGrams = 28.0, fiberGrams = 16.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Press tofu; coat each slice with chia seeds, pressing firmly.",
                "Pan-fry in olive oil over medium heat 4–5 minutes per side until golden and crisp.",
                "Cook quinoa; whisk tahini with lemon juice and 2 tbsp water.",
                "Plate spinach, quinoa, avocado, and tofu; drizzle with tahini dressing."
            ),
            antiInflammatoryScore = 10
        ),

        Recipe(
            id = "rajma_chawal",
            name = "Light Rajma Chawal",
            description = "The beloved North Indian comfort dish made lighter — red kidney beans in a tomato-onion masala served over brown basmati rice. High in plant protein and dietary fiber.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup cooked red kidney beans",
                "1 medium onion (diced)",
                "1 can diced tomatoes",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "1 tsp rajma masala",
                "½ tsp cumin seeds",
                "½ cup cooked brown basmati rice",
                "1 tbsp olive oil",
                "Salt and coriander to garnish"
            ),
            calories = 470, proteinGrams = 20.0, carbGrams = 78.0, fatGrams = 6.0, fiberGrams = 15.0,
            prepMinutes = 10, cookMinutes = 20,
            instructions = listOf(
                "Heat oil; add cumin seeds, then onion and cook until golden.",
                "Add garlic and ginger; cook 2 minutes.",
                "Add tomatoes and rajma masala; simmer until thick.",
                "Add kidney beans; cook 10 minutes on low until flavours meld.",
                "Serve over brown rice; garnish with coriander."
            ),
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "ginger_veggie_soup",
            name = "Ginger Turmeric Healing Vegetable Soup",
            description = "A light, mineral-rich soup with ginger, turmeric, and seasonal vegetables. Black pepper enhances curcumin absorption by 2000% — a detail most people miss.",
            mealType = "Dinner",
            tags = listOf("Vegan", "Whole Foods"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup carrot (sliced)",
                "1 cup zucchini (cubed)",
                "1 cup spinach",
                "½ cup red lentils",
                "3 cups vegetable broth",
                "1 tsp turmeric",
                "1 tsp fresh ginger (grated)",
                "2 garlic cloves (minced)",
                "Juice of ½ lemon",
                "Freshly cracked black pepper",
                "1 tbsp olive oil"
            ),
            calories = 260, proteinGrams = 14.0, carbGrams = 36.0, fatGrams = 6.0, fiberGrams = 10.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Heat oil in a large pot; sauté garlic and ginger 2 minutes.",
                "Add turmeric, carrots, and lentils; stir 1 minute.",
                "Add broth and zucchini; bring to a boil.",
                "Simmer 20 minutes; add spinach in last 2 minutes.",
                "Finish with lemon juice and generous black pepper."
            ),
            storageNotes = "Freezes well for up to 1 month.",
            antiInflammatoryScore = 10
        ),

        Recipe(
            id = "paneer_sheet_pan",
            name = "Sheet-Pan Paneer & Roasted Vegetables",
            description = "One-pan dinner with herb-marinated paneer and colourful roasted vegetables. Minimal cleanup, maximum protein, and a satisfying variety of textures.",
            mealType = "Dinner",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "200g paneer (cubed)",
                "1 bell pepper (sliced)",
                "1 zucchini (sliced)",
                "1 cup cherry tomatoes",
                "1 tbsp olive oil",
                "1 tsp Italian herbs",
                "½ tsp garlic powder",
                "½ tsp smoked paprika",
                "Salt and pepper to taste",
                "1 tbsp fresh basil to garnish"
            ),
            calories = 470, proteinGrams = 30.0, carbGrams = 18.0, fatGrams = 32.0, fiberGrams = 5.0,
            prepMinutes = 10, cookMinutes = 25,
            instructions = listOf(
                "Preheat oven to 200 °C (400 °F).",
                "Toss paneer and vegetables with olive oil, herbs, garlic powder, paprika, salt, and pepper.",
                "Spread on a baking sheet in a single layer.",
                "Roast 20–25 minutes until paneer is golden and vegetables are caramelised.",
                "Garnish with fresh basil."
            ),
            metabolicResetScore = 8
        ),

        // ── SNACKS ────────────────────────────────────────────────────────────

        Recipe(
            id = "protein_smoothie_bowl",
            name = "Protein Smoothie Bowl",
            description = "A thick-blended protein shake poured into a bowl and topped with crunchy granola, chia seeds, and mixed berries. Thick enough to eat with a spoon.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            collection = emptyList(),
            ingredients = listOf(
                "1 scoop vanilla protein powder",
                "½ cup frozen banana",
                "½ cup frozen mixed berries",
                "½ cup unsweetened almond milk",
                "1 tbsp chia seeds",
                "2 tbsp low-fat granola"
            ),
            calories = 320, proteinGrams = 30.0, carbGrams = 36.0, fatGrams = 5.0, fiberGrams = 5.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Blend protein powder, banana, berries, and almond milk until thick.",
                "Pour into a bowl; should be thicker than a drink.",
                "Top with chia seeds and granola; serve immediately."
            ),
            metabolicResetScore = 6
        ),

        Recipe(
            id = "cottage_cheese_toast",
            name = "Cottage Cheese & Veggie Toast",
            description = "Creamy cottage cheese heaped onto whole-grain toast and topped with fresh cucumber or tomato. Simple, high in protein, and blood-sugar friendly.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
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
            metabolicResetScore = 7, isGlucoseConscious = true
        ),

        Recipe(
            id = "protein_chia_pudding",
            name = "Protein Chia Pudding",
            description = "Silky overnight chia pudding with protein powder and a honey-vanilla finish. Chia seeds provide 5g of omega-3 ALA per serving — a natural anti-inflammatory.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "3 tbsp chia seeds",
                "1 scoop vanilla protein powder",
                "1 cup unsweetened almond milk",
                "1 tbsp honey",
                "¼ tsp vanilla extract"
            ),
            calories = 300, proteinGrams = 25.0, carbGrams = 28.0, fatGrams = 9.0, fiberGrams = 10.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Whisk protein powder into almond milk until dissolved.",
                "Add chia seeds, honey, and vanilla; stir vigorously.",
                "Refrigerate at least 4 hours or overnight.",
                "Stir well before eating; top with berries if desired."
            ),
            storageNotes = "Keeps refrigerated for 4 days.",
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "moong_sprout_chaat",
            name = "Moong Sprout Chaat",
            description = "Freshly sprouted mung beans tossed with cucumber, tomato, and tangy chaat masala. Sprouting increases protein bioavailability and dramatically reduces the glycaemic impact.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
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
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "edamame_snack",
            name = "Chilli Lime Spiced Edamame",
            description = "Warm shelled edamame seasoned with chilli flakes, lime zest, and a pinch of sea salt. Each cup delivers 17g of complete soy protein with a satisfying anti-inflammatory flavour punch.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
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
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "paneer_skewers",
            name = "Tandoori Paneer Bites",
            description = "Bite-sized paneer marinated in tandoori yogurt spices and grilled until charred. 24g of protein in a snack that tastes like it came from a restaurant.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
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
            metabolicResetScore = 8
        ),

        Recipe(
            id = "walnut_energy_balls",
            name = "Walnut & Date Energy Balls",
            description = "No-bake energy balls made with walnuts, Medjool dates, chia seeds, and raw cacao. Walnuts top the nut charts for anti-inflammatory ALA omega-3; dates provide natural fibre and minerals.",
            mealType = "Snack",
            tags = listOf("Vegan"),
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
            antiInflammatoryScore = 9
        ),

        Recipe(
            id = "turmeric_almonds",
            name = "Turmeric Spiced Almonds",
            description = "Dry-roasted almonds coated in turmeric, black pepper, and a touch of sea salt. The black pepper activates curcumin absorption — turning a simple snack into an anti-inflammatory one.",
            mealType = "Snack",
            tags = listOf("Vegan"),
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
            antiInflammatoryScore = 9
        ),

        Recipe(
            id = "green_detox_smoothie",
            name = "Anti-Inflammatory Green Smoothie",
            description = "A clean, bright smoothie with spinach, cucumber, ginger, and lemon — the classic anti-inflammatory green combination with added protein for satiety.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1 cup fresh spinach",
                "½ cucumber (roughly chopped)",
                "1 tsp fresh ginger (grated)",
                "Juice of ½ lemon",
                "1 scoop vanilla protein powder",
                "1 cup unsweetened almond milk",
                "1 tbsp chia seeds",
                "3 ice cubes"
            ),
            calories = 260, proteinGrams = 26.0, carbGrams = 18.0, fatGrams = 6.0, fiberGrams = 6.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to a blender.",
                "Blend on high 60 seconds until completely smooth.",
                "Adjust ginger or lemon to taste; serve immediately."
            ),
            antiInflammatoryScore = 9
        ),

        Recipe(
            id = "berry_protein_parfait",
            name = "Mixed Berry Protein Parfait",
            description = "Layered Greek yogurt, mixed berries, and crushed walnuts — anti-inflammatory antioxidants from the berries meet metabolic-reset protein from the yogurt in every bite.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
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
            metabolicResetScore = 8, antiInflammatoryScore = 8, isGlucoseConscious = true
        )
    )
}
