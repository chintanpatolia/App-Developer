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

        Recipe(
            id = "cottage_cheese_pancakes",
            name = "Cottage Cheese Protein Pancakes",
            description = "Fluffy, high-protein pancakes made by blending cottage cheese, oats, eggs, and protein powder into a batter. No flour needed — the oats bind everything and keep the glycaemic load low.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup low-fat cottage cheese",
                "2 eggs",
                "½ cup rolled oats",
                "1 scoop vanilla protein powder",
                "1 tsp baking powder",
                "½ tsp vanilla extract",
                "Pinch of salt",
                "1 tsp olive oil (for cooking)"
            ),
            calories = 420, proteinGrams = 42.0, carbGrams = 34.0, fatGrams = 10.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Blend cottage cheese, eggs, oats, protein powder, baking powder, vanilla, and salt until smooth.",
                "Heat a non-stick pan over medium heat; brush with olive oil.",
                "Pour ¼ cup batter per pancake; cook 2–3 minutes until bubbles form, then flip.",
                "Cook 1–2 minutes more until golden. Serve with berries or a drizzle of honey."
            ),
            storageNotes = "Refrigerate cooked pancakes up to 3 days; reheat in a pan or microwave.",
            mealPrepNotes = "Make batter the night before and refrigerate; blend briefly before cooking.",
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "cottage_cheese_protein_bowl",
            name = "High-Protein Cottage Cheese Breakfast Bowl",
            description = "The fastest 44 g protein breakfast in the app: cottage cheese stirred with protein powder, topped with berries and hemp seeds. No cooking, no blending — just stir and eat.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup low-fat cottage cheese",
                "1 scoop vanilla protein powder",
                "2 tbsp hemp seeds",
                "½ cup mixed berries",
                "1 tsp honey",
                "¼ tsp cinnamon"
            ),
            calories = 390, proteinGrams = 44.0, carbGrams = 32.0, fatGrams = 10.0, fiberGrams = 5.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Stir protein powder into cottage cheese until fully combined.",
                "Top with mixed berries, hemp seeds, and a drizzle of honey.",
                "Sprinkle cinnamon and serve immediately."
            ),
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "double_protein_yogurt_bowl",
            name = "Double-Protein Greek Yogurt Breakfast Bowl",
            description = "Greek yogurt meets a full scoop of protein powder for a thick, spoonable bowl with 42 g of protein. Berries and low-fat granola add texture without spiking blood sugar.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup non-fat Greek yogurt",
                "1 scoop vanilla protein powder",
                "½ cup mixed berries",
                "2 tbsp low-fat granola",
                "1 tsp honey"
            ),
            calories = 380, proteinGrams = 42.0, carbGrams = 42.0, fatGrams = 4.0, fiberGrams = 4.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Stir protein powder into Greek yogurt until smooth and thick.",
                "Top with mixed berries, granola, and honey.",
                "Serve immediately."
            ),
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "silken_tofu_protein_smoothie",
            name = "Silken Tofu Protein Smoothie Bowl",
            description = "Blended silken tofu adds a velvety, dairy-free protein base to this thick smoothie bowl. One scoop of plant protein powder pushes it to 38 g without any powder taste.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "150g silken tofu",
                "1 scoop vanilla plant protein powder",
                "1 cup frozen mixed berries",
                "1 frozen banana",
                "1 cup oat milk",
                "1 tbsp chia seeds"
            ),
            calories = 400, proteinGrams = 38.0, carbGrams = 52.0, fatGrams = 9.0, fiberGrams = 8.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to a blender.",
                "Blend on high 60 seconds until completely smooth and thick.",
                "Pour into a bowl; top with extra berries or granola if desired."
            ),
            antiInflammatoryScore = 7
        ),

        Recipe(
            id = "paneer_egg_scramble",
            name = "Paneer & Egg High-Protein Scramble",
            description = "Crumbled paneer scrambled with eggs and vegetables delivers a powerful dual-protein breakfast. The combination of dairy protein from paneer and complete protein from eggs creates a fast-digesting, muscle-sparing morning meal.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "150g paneer (crumbled)",
                "3 eggs",
                "½ cup fresh spinach",
                "¼ cup bell pepper (diced)",
                "1 green chilli (minced)",
                "¼ tsp turmeric",
                "½ tsp cumin",
                "1 tsp olive oil",
                "Salt and black pepper to taste"
            ),
            calories = 440, proteinGrams = 40.0, carbGrams = 8.0, fatGrams = 28.0, fiberGrams = 2.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Heat olive oil in a non-stick pan over medium heat.",
                "Sauté bell pepper and chilli 2 minutes; add spinach and wilt 1 minute.",
                "Add crumbled paneer, turmeric, and cumin; stir 2 minutes.",
                "Whisk eggs, pour over paneer mixture; scramble gently until just set.",
                "Season and serve immediately."
            ),
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "mango_lassi_protein_smoothie",
            name = "High-Protein Mango Lassi Smoothie",
            description = "The classic Indian lassi elevated with a full scoop of protein powder. Frozen mango and Greek yogurt create a creamy, naturally sweet drink with 40 g of protein and a fraction of a traditional lassi's sugar.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup non-fat Greek yogurt",
                "1 scoop vanilla protein powder",
                "1 cup frozen mango chunks",
                "½ cup oat milk",
                "¼ tsp cardamom",
                "Pinch of saffron (optional)",
                "1 tsp honey"
            ),
            calories = 370, proteinGrams = 40.0, carbGrams = 48.0, fatGrams = 3.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to a blender.",
                "Blend on high 45 seconds until smooth and creamy.",
                "Taste; add honey if needed. Serve cold."
            ),
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "hemp_tofu_power_breakfast",
            name = "Hemp & Tofu High-Protein Breakfast Bowl",
            description = "Silken tofu blended smooth and folded with hemp seeds and plant protein powder — a completely dairy-free, egg-free breakfast hitting 38 g of protein. Hemp seeds are the only nut-free, soy-independent way to stack plant protein this high.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "200g silken tofu",
                "1 scoop vanilla plant protein powder",
                "3 tbsp hemp seeds",
                "½ cup mixed berries",
                "½ cup oat milk",
                "1 tsp maple syrup",
                "¼ tsp cinnamon"
            ),
            calories = 430, proteinGrams = 38.0, carbGrams = 32.0, fatGrams = 16.0, fiberGrams = 5.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Blend silken tofu, plant protein powder, oat milk, maple syrup, and cinnamon until completely smooth.",
                "Pour into a bowl; top with hemp seeds and berries.",
                "Serve immediately."
            ),
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "protein_cottage_oatmeal",
            name = "Protein-Boosted Cottage Cheese Oatmeal",
            description = "Cottage cheese stirred into warm oatmeal is the oldest protein-stacking trick in bodybuilding — and it works. The mild flavour disappears into the oats while pushing protein to 38 g with just two key ingredients.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "½ cup rolled oats",
                "½ cup low-fat cottage cheese",
                "1 scoop vanilla protein powder",
                "¾ cup oat milk",
                "1 banana (sliced)",
                "1 tsp honey",
                "¼ tsp cinnamon"
            ),
            calories = 430, proteinGrams = 38.0, carbGrams = 58.0, fatGrams = 6.0, fiberGrams = 5.0,
            prepMinutes = 2, cookMinutes = 5,
            instructions = listOf(
                "Cook oats in oat milk over medium heat 3–4 minutes, stirring.",
                "Remove from heat; stir in cottage cheese and protein powder until smooth.",
                "Top with banana, honey, and cinnamon. Serve warm."
            ),
            metabolicResetScore = 8, isGlucoseConscious = true
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

        Recipe(
            id = "double_paneer_masala_bowl",
            name = "Double Paneer Masala Power Bowl",
            description = "250 g of paneer — the highest paneer serving in this catalog — simmered in a spiced tomato masala with edamame for a complete amino acid profile. This bowl is designed for days when protein targets are hardest to hit.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
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
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "tempeh_quinoa_bowl",
            name = "Teriyaki Tempeh & Quinoa Bowl",
            description = "200 g of tempeh — the highest vegan protein per gram of any whole food — glazed in a quick teriyaki sauce over fluffy quinoa. At 42 g of protein, this bowl rivals most meat-based lunches.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
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
            metabolicResetScore = 8
        ),

        Recipe(
            id = "large_soy_chunk_masala",
            name = "Large Soy Chunk Masala Bowl",
            description = "75 g of dry soy chunks — the largest soy serving in the catalog — cooked in a rich tomato masala. Soy protein isolate is 90% protein by weight; this bowl delivers 42 g from a single plant source.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
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
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "paneer_chickpea_power_bowl",
            name = "Paneer & Roasted Chickpea Power Bowl",
            description = "Dual protein sources: golden pan-seared paneer and crispy roasted chickpeas combine for 45 g of protein — the highest-protein vegetarian lunch in the catalog. Brown rice and vegetables round out the macros.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
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
                "Toss chickpeas with ½ tbsp olive oil, paprika, cumin, and salt; roast at 200 °C for 20 minutes until crispy.",
                "Pan-sear paneer in remaining olive oil until golden on all sides.",
                "Whisk tahini with lemon juice and 2 tbsp water for dressing.",
                "Build bowl: brown rice, spinach, tomatoes, chickpeas, paneer; drizzle dressing."
            ),
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "edamame_tofu_power_bowl",
            name = "Edamame & Crispy Tofu Power Bowl",
            description = "Two soy protein sources together: crispy pan-fried tofu and warm edamame over quinoa with hemp seeds for extra complete protein. Entirely vegan and soy-based — the bowl for soy-tolerant plant-protein maximisers.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
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
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "high_protein_tempeh_bowl",
            name = "Smoky Tempeh & Brown Rice Bowl",
            description = "Tempeh marinated in smoked paprika and tamari, pan-crisped, served over brown rice with roasted peppers. 40 g of complete vegan protein from tempeh alone — the second-highest-protein vegan lunch in the catalog.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "200g tempeh (sliced)",
                "½ cup cooked brown rice",
                "1 red bell pepper (sliced)",
                "2 cups baby spinach",
                "1 tbsp tamari or soy sauce",
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
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "paneer_edamame_bowl",
            name = "Paneer & Edamame Super Bowl",
            description = "Paneer and edamame are two of the densest vegetarian protein sources available. Together they hit 44 g of protein in one lunch bowl — with a sesame-ginger dressing tying the Indian-Japanese fusion together.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
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
            antiInflammatoryScore = 7
        ),

        Recipe(
            id = "large_soy_chunk_curry",
            name = "High-Protein Soy Chunk Tikka Curry",
            description = "80 g of dry soy chunks rehydrated in a rich tikka-style curry. Soy chunks are among the cheapest and highest-density plant protein sources on the market — this bowl uses the maximum practical serving for a lunch portion.",
            mealType = "Lunch",
            tags = listOf("Vegan", "High Protein"),
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
            metabolicResetScore = 10, isGlucoseConscious = true
        ),

        Recipe(
            id = "cottage_cheese_quinoa_bowl",
            name = "Cottage Cheese & Quinoa Protein Bowl",
            description = "A light but surprisingly protein-dense bowl: creamy cottage cheese spooned over warm quinoa with roasted chickpeas and a lemon dressing. The cottage cheese melts slightly into the warm quinoa for a rich sauce-like texture.",
            mealType = "Lunch",
            tags = listOf("Vegetarian", "High Protein"),
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
            metabolicResetScore = 8, isGlucoseConscious = true
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

        Recipe(
            id = "paneer_tikka_masala",
            name = "High-Protein Paneer Tikka Masala",
            description = "The restaurant favourite made at home with a generous 250 g of paneer. The larger paneer serving is the single change that pushes this classic to 40 g of protein — the highest-protein vegetarian curry in the catalog.",
            mealType = "Dinner",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "250g paneer (cubed)",
                "½ cup low-fat yogurt",
                "1 tsp tikka masala",
                "1 can diced tomatoes",
                "1 medium onion (diced)",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "½ tsp cumin",
                "½ tsp turmeric",
                "½ tsp garam masala",
                "1 tbsp olive oil",
                "Salt and fresh coriander to garnish"
            ),
            calories = 540, proteinGrams = 40.0, carbGrams = 24.0, fatGrams = 32.0, fiberGrams = 5.0,
            prepMinutes = 10, cookMinutes = 20,
            instructions = listOf(
                "Marinate paneer in yogurt, tikka masala, turmeric, and a pinch of salt for 10 minutes.",
                "Grill or pan-sear paneer until charred at edges; set aside.",
                "Heat oil; sauté onion 5 minutes, add garlic, ginger, and cumin.",
                "Add tomatoes and simmer 10 minutes until sauce thickens.",
                "Add paneer, garam masala; simmer 5 minutes. Garnish with coriander."
            ),
            metabolicResetScore = 8
        ),

        Recipe(
            id = "soy_chunk_palak_curry",
            name = "High-Protein Soy Chunk Palak Curry",
            description = "80 g of dry soy chunks simmered in a velvety spiced spinach purée. This vegan twist on palak paneer delivers 44 g of protein — the highest-protein vegan dinner in the catalog — with a fraction of the fat.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "80g soy chunks (dry weight)",
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
            calories = 420, proteinGrams = 44.0, carbGrams = 24.0, fatGrams = 10.0, fiberGrams = 12.0,
            prepMinutes = 15, cookMinutes = 20,
            instructions = listOf(
                "Soak soy chunks in boiling water 10 minutes; drain and squeeze dry.",
                "Blend blanched spinach to a smooth purée.",
                "Heat oil; sauté onion, garlic, and ginger 5 minutes.",
                "Add cumin, turmeric, and tomato; cook until soft.",
                "Add spinach purée; simmer 5 minutes. Add soy chunks and garam masala; simmer 8 minutes more."
            ),
            antiInflammatoryScore = 9
        ),

        Recipe(
            id = "tempeh_coconut_curry",
            name = "Tempeh Coconut Curry",
            description = "200 g of tempeh simmered in a fragrant Thai-inspired coconut broth with lemongrass and red curry paste. Tempeh's dense protein structure holds up to long simmering, absorbing the curry flavour through every bite.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "200g tempeh (cubed)",
                "1 can light coconut milk",
                "1 cup vegetable broth",
                "1 lemongrass stalk (bruised)",
                "1 tbsp red curry paste",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "1 cup broccoli florets",
                "1 red bell pepper (sliced)",
                "1 tbsp low-sodium soy sauce",
                "1 tsp lime juice"
            ),
            calories = 490, proteinGrams = 40.0, carbGrams = 28.0, fatGrams = 26.0, fiberGrams = 8.0,
            prepMinutes = 10, cookMinutes = 20,
            instructions = listOf(
                "Pan-fry tempeh cubes in a dry pan until golden; set aside.",
                "In the same pan, sauté garlic, ginger, and curry paste 2 minutes.",
                "Add coconut milk, broth, and lemongrass; bring to a gentle simmer.",
                "Add broccoli and bell pepper; simmer 5 minutes.",
                "Add tempeh and soy sauce; simmer 5 more minutes. Finish with lime juice."
            ),
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "paneer_egg_masala",
            name = "Paneer & Egg Masala Curry",
            description = "Boiled eggs added to a paneer curry is a classical North Indian approach to maximising protein in a single pot. Two proteins, two textures — tender paneer and firm egg — in a rich tomato-onion masala.",
            mealType = "Dinner",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "200g paneer (cubed)",
                "3 hard-boiled eggs (halved)",
                "1 can diced tomatoes",
                "1 medium onion (diced)",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "1 tsp cumin",
                "1 tsp coriander powder",
                "½ tsp turmeric",
                "½ tsp garam masala",
                "1 tbsp olive oil",
                "Salt and fresh coriander to garnish"
            ),
            calories = 520, proteinGrams = 42.0, carbGrams = 18.0, fatGrams = 32.0, fiberGrams = 5.0,
            prepMinutes = 10, cookMinutes = 20,
            instructions = listOf(
                "Pan-sear paneer cubes until golden; set aside.",
                "Heat oil; sauté onion 5 minutes, add garlic, ginger, cumin, coriander, and turmeric.",
                "Add tomatoes; simmer 10 minutes until thick.",
                "Add paneer and halved eggs; simmer gently 5 minutes.",
                "Finish with garam masala and coriander."
            ),
            metabolicResetScore = 9
        ),

        Recipe(
            id = "tofu_edamame_stir_fry",
            name = "Crispy Tofu & Edamame Stir-Fry",
            description = "Double soy protein: crispy pan-fried tofu meets warming edamame in a ginger-garlic soy glaze over brown rice. Two complete soy proteins stacked in one bowl for 42 g without any animal products.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "200g firm tofu (pressed, cubed)",
                "1½ cups shelled edamame (cooked)",
                "½ cup cooked brown rice",
                "2 cups broccoli florets",
                "3 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "2 tbsp low-sodium soy sauce",
                "1 tsp sesame oil",
                "1 tsp cornstarch",
                "1 tsp sesame seeds"
            ),
            calories = 470, proteinGrams = 42.0, carbGrams = 44.0, fatGrams = 14.0, fiberGrams = 10.0,
            prepMinutes = 15, cookMinutes = 15,
            instructions = listOf(
                "Toss tofu with cornstarch; pan-fry in sesame oil over high heat until golden, about 5 minutes.",
                "Add garlic and ginger; stir 1 minute.",
                "Add broccoli; stir-fry 4 minutes.",
                "Add edamame and soy sauce; toss to coat.",
                "Serve over brown rice; garnish with sesame seeds."
            ),
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "high_protein_soya_biryani",
            name = "High-Protein Soya Biryani",
            description = "The highest-protein dinner in the catalog: 100 g of dry textured soy protein layered with fragrant basmati rice and whole spices. This biryani delivers 50 g of protein — the ceiling this catalog was designed to reach.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "100g textured soy protein (soy chunks, minced or small)",
                "½ cup basmati rice",
                "1 large onion (thinly sliced)",
                "1 tsp cumin seeds",
                "2 bay leaves",
                "4 cardamom pods",
                "1 cinnamon stick",
                "1 tsp fresh ginger (grated)",
                "2 garlic cloves (minced)",
                "½ tsp turmeric",
                "1 tsp biryani masala",
                "1 tbsp olive oil",
                "Salt and fresh coriander to garnish"
            ),
            calories = 560, proteinGrams = 50.0, carbGrams = 60.0, fatGrams = 10.0, fiberGrams = 12.0,
            prepMinutes = 15, cookMinutes = 30,
            instructions = listOf(
                "Soak soy protein in boiling water 10 minutes; drain and squeeze dry.",
                "Heat oil in a heavy pot; fry onion until golden-brown, about 10 minutes. Set half aside for garnish.",
                "Add cumin, bay leaves, cardamom, cinnamon, garlic, ginger, and biryani masala; stir 2 minutes.",
                "Add soy protein and turmeric; cook 5 minutes.",
                "Add rice and 1 cup water; bring to a boil, cover tightly, and cook on low 15 minutes.",
                "Fluff gently; garnish with reserved onions and coriander."
            ),
            storageNotes = "Refrigerates up to 3 days; add a splash of water when reheating.",
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "tempeh_black_bean_bowl",
            name = "Tempeh & Black Bean Dinner Bowl",
            description = "Tempeh and black beans supply protein from different biological pathways — combined, they hit 46 g with a complementary amino acid profile. Smoky cumin-lime seasoning ties this Latin-fusion dinner together.",
            mealType = "Dinner",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "200g tempeh (cubed)",
                "½ cup cooked black beans",
                "½ cup cooked brown rice",
                "1 cup cherry tomatoes (halved)",
                "1 cup baby spinach",
                "1 tsp cumin",
                "1 tsp smoked paprika",
                "1 tbsp tamari or soy sauce",
                "1 tsp olive oil",
                "Juice of ½ lime",
                "Fresh coriander to garnish"
            ),
            calories = 510, proteinGrams = 46.0, carbGrams = 46.0, fatGrams = 18.0, fiberGrams = 12.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Toss tempeh with cumin, paprika, and tamari; pan-fry in olive oil 4–5 minutes per side.",
                "Warm black beans; season with salt.",
                "Build bowl: brown rice, spinach, tomatoes, beans, tempeh.",
                "Drizzle lime juice and garnish with coriander."
            ),
            metabolicResetScore = 9, isGlucoseConscious = true
        ),

        Recipe(
            id = "paneer_dal_makhani",
            name = "Protein-Boosted Dal Makhani with Paneer",
            description = "Dal makhani — the slow-cooked black lentil and kidney bean dal — enriched with cubes of pan-seared paneer. Three protein sources in one pot: black lentils, kidney beans, and dairy protein from paneer, for 42 g total.",
            mealType = "Dinner",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "150g paneer (cubed, pan-seared)",
                "½ cup whole black lentils (urad dal, soaked overnight)",
                "¼ cup red kidney beans (cooked)",
                "1 can diced tomatoes",
                "1 medium onion (diced)",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "1 tsp cumin seeds",
                "½ tsp garam masala",
                "1 tbsp olive oil",
                "Salt and coriander to garnish"
            ),
            calories = 530, proteinGrams = 42.0, carbGrams = 50.0, fatGrams = 18.0, fiberGrams = 14.0,
            prepMinutes = 15, cookMinutes = 35,
            instructions = listOf(
                "Pressure-cook soaked black lentils and kidney beans until tender, about 20 minutes.",
                "Pan-sear paneer until golden; set aside.",
                "In a separate pan, heat olive oil; add cumin seeds, then onion, garlic, and ginger.",
                "Add tomatoes and cook until thick; stir into cooked dal.",
                "Simmer dal 10 minutes; add paneer and garam masala; heat through."
            ),
            storageNotes = "Refrigerates 4 days; freezes well.",
            metabolicResetScore = 10, isGlucoseConscious = true
        ),

        Recipe(
            id = "soy_paneer_double_curry",
            name = "Double-Protein Soy Chunk & Paneer Curry",
            description = "Soy chunks and paneer combined in one masala for 48 g of protein per serving — designed specifically for days when the protein target feels unreachable. Two complementary sources; one simple curry.",
            mealType = "Dinner",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "150g paneer (cubed)",
                "50g soy chunks (dry weight)",
                "1 can diced tomatoes",
                "1 medium onion (diced)",
                "2 garlic cloves (minced)",
                "1 tsp fresh ginger (grated)",
                "1 tsp cumin",
                "1 tsp coriander powder",
                "½ tsp turmeric",
                "½ tsp garam masala",
                "1 tbsp olive oil",
                "Salt and fresh coriander to garnish"
            ),
            calories = 530, proteinGrams = 48.0, carbGrams = 26.0, fatGrams = 24.0, fiberGrams = 8.0,
            prepMinutes = 15, cookMinutes = 22,
            instructions = listOf(
                "Soak soy chunks in boiling water 10 minutes; drain and squeeze dry.",
                "Pan-sear paneer until golden; set aside.",
                "Heat oil; sauté onion 5 minutes, add garlic, ginger, cumin, coriander, and turmeric.",
                "Add tomatoes and simmer 8 minutes until thick.",
                "Add soy chunks; simmer 8 minutes. Add paneer and garam masala; heat through."
            ),
            metabolicResetScore = 10, isGlucoseConscious = true
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
        ),

        Recipe(
            id = "protein_shake",
            name = "Protein Shake",
            description = "A quick 30-second protein hit — one scoop of protein powder blended with almond milk and ice. Simple, fast, and reliably 28g of protein with under 160 calories.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            collection = emptyList(),
            ingredients = listOf(
                "1 scoop vanilla or chocolate protein powder",
                "1 cup unsweetened almond milk",
                "3–4 ice cubes"
            ),
            calories = 160, proteinGrams = 28.0, carbGrams = 6.0, fatGrams = 3.0, fiberGrams = 1.0,
            prepMinutes = 2, cookMinutes = 0,
            instructions = listOf(
                "Add protein powder, almond milk, and ice to a shaker bottle or blender.",
                "Shake vigorously for 30 seconds or blend for 20 seconds.",
                "Serve immediately."
            )
        ),

        Recipe(
            id = "greek_yogurt_berry_bowl",
            name = "Greek Yogurt Berry Bowl",
            description = "A simple bowl of thick non-fat Greek yogurt topped with fresh mixed berries. 22g of slow-digesting protein, naturally sweet, and under 220 calories.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
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
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "cottage_cheese_bowl",
            name = "Cottage Cheese Protein Bowl",
            description = "Low-fat cottage cheese topped with pineapple chunks and cinnamon. 25g of slow-digesting casein protein keeps you full between meals without excess calories.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "¾ cup low-fat cottage cheese (170g)",
                "½ cup pineapple chunks (fresh or tinned in juice)",
                "¼ tsp cinnamon",
                "Pinch of black pepper (optional)"
            ),
            calories = 190, proteinGrams = 25.0, carbGrams = 18.0, fatGrams = 2.0, fiberGrams = 1.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Spoon cottage cheese into a bowl.",
                "Top with pineapple chunks.",
                "Sprinkle cinnamon and a pinch of pepper if using."
            ),
            metabolicResetScore = 7, isGlucoseConscious = true
        ),

        Recipe(
            id = "protein_oats_mini",
            name = "Protein Oats Mini Bowl",
            description = "A half-portion of protein oatmeal — the perfect bridge snack between meals. 20g of protein from a half-scoop of powder stirred into warm oats, under 200 calories.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
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
            )
        ),

        Recipe(
            id = "tofu_snack_bowl",
            name = "Quick Sesame Tofu Bowl",
            description = "Firm tofu cubed and pan-fried in sesame oil with soy sauce and chilli. Ready in 10 minutes with 22g of complete plant protein and under 200 calories.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
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
            )
        ),

        Recipe(
            id = "tempeh_bites_snack",
            name = "Crispy Marinated Tempeh Bites",
            description = "Tempeh sliced thin, marinated in tamari and smoked paprika, then pan-crisped. At 28g protein per serving it is the highest-protein vegan snack in the app.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            collection = emptyList(),
            ingredients = listOf(
                "150g tempeh (sliced)",
                "1 tbsp tamari or soy sauce",
                "½ tsp smoked paprika",
                "½ tsp garlic powder",
                "1 tsp olive oil",
                "1 tsp apple cider vinegar"
            ),
            calories = 220, proteinGrams = 28.0, carbGrams = 8.0, fatGrams = 10.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Mix tamari, smoked paprika, garlic powder, and apple cider vinegar.",
                "Toss tempeh slices in marinade; rest 5 minutes.",
                "Heat olive oil in a non-stick pan over medium-high heat.",
                "Fry tempeh 4–5 minutes per side until crispy and golden.",
                "Serve immediately."
            )
        ),

        Recipe(
            id = "soy_chunks_snack",
            name = "Spiced Soy Protein Chunks",
            description = "Rehydrated textured soy protein simmered in a bold cumin-coriander broth. 28g of protein and under 200 calories from one of the highest-protein plant foods available.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            collection = emptyList(),
            ingredients = listOf(
                "50g soy chunks / textured soy protein (dry weight)",
                "1 cup water or vegetable broth",
                "½ tsp cumin",
                "½ tsp coriander powder",
                "¼ tsp turmeric",
                "¼ tsp chilli powder",
                "Salt to taste",
                "1 tsp lemon juice"
            ),
            calories = 200, proteinGrams = 28.0, carbGrams = 10.0, fatGrams = 2.0, fiberGrams = 2.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Boil soy chunks in broth 8 minutes until tender; drain and squeeze dry.",
                "Return to pan; add cumin, coriander, turmeric, and chilli powder.",
                "Cook on medium heat 3–4 minutes, stirring often.",
                "Finish with lemon juice and salt. Serve hot."
            )
        ),

        Recipe(
            id = "paneer_protein_cup",
            name = "Chilli Lime Paneer Protein Cup",
            description = "Fresh paneer cubes tossed with lime juice, chilli powder, and chaat masala — no cooking needed. 26g of protein in 200 calories; the fastest high-protein vegetarian snack.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            collection = emptyList(),
            ingredients = listOf(
                "130g paneer (cubed)",
                "Juice of ½ lime",
                "¼ tsp chilli powder",
                "½ tsp chaat masala",
                "Salt to taste",
                "Fresh coriander (optional)"
            ),
            calories = 200, proteinGrams = 26.0, carbGrams = 4.0, fatGrams = 13.0, fiberGrams = 0.5,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Cube paneer into bite-sized pieces.",
                "Toss with lime juice, chilli powder, and chaat masala.",
                "Season with salt; garnish with coriander if using.",
                "Serve immediately — no cooking required."
            )
        ),

        Recipe(
            id = "cottage_cheese_protein_pudding",
            name = "Cottage Cheese Protein Pudding",
            description = "Cottage cheese blended smooth with protein powder becomes a thick, spoonable pudding with 30 g of protein in a snack under 250 calories. Chill overnight for an even firmer texture.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "½ cup low-fat cottage cheese",
                "1 scoop vanilla protein powder",
                "½ cup oat milk",
                "1 tsp honey",
                "¼ tsp vanilla extract"
            ),
            calories = 250, proteinGrams = 30.0, carbGrams = 26.0, fatGrams = 3.0, fiberGrams = 1.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Blend cottage cheese, protein powder, oat milk, honey, and vanilla until completely smooth.",
                "Pour into a bowl or jar; refrigerate 30 minutes for a thicker set.",
                "Top with berries or cinnamon before serving."
            ),
            storageNotes = "Keeps refrigerated for 3 days.",
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "double_protein_yogurt_snack",
            name = "Double-Protein Greek Yogurt Snack Cup",
            description = "Greek yogurt plus a generous serving of protein powder for 32 g of protein in one snack cup. Slower-digesting casein from yogurt and fast-digesting protein powder make this an ideal post-workout snack.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup non-fat Greek yogurt",
                "¾ scoop vanilla protein powder",
                "½ cup mixed berries",
                "1 tsp honey"
            ),
            calories = 250, proteinGrams = 32.0, carbGrams = 28.0, fatGrams = 2.0, fiberGrams = 3.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Stir protein powder into Greek yogurt until smooth.",
                "Top with berries and drizzle with honey.",
                "Serve immediately or refrigerate up to 2 hours."
            ),
            metabolicResetScore = 8, isGlucoseConscious = true
        ),

        Recipe(
            id = "large_roasted_edamame",
            name = "Spiced Dry-Roasted Edamame",
            description = "A generous 1.5-cup serving of edamame dry-roasted in the oven with cumin and chilli for a crunchy snack with 26 g of complete soy protein. The highest-protein fully vegan snack that requires no protein powder.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1½ cups shelled edamame (cooked)",
                "½ tsp cumin",
                "½ tsp chilli powder",
                "¼ tsp garlic powder",
                "½ tsp olive oil",
                "Pinch of sea salt",
                "Squeeze of lime juice"
            ),
            calories = 250, proteinGrams = 26.0, carbGrams = 20.0, fatGrams = 10.0, fiberGrams = 10.0,
            prepMinutes = 3, cookMinutes = 15,
            instructions = listOf(
                "Pat edamame dry; toss with olive oil, cumin, chilli powder, garlic powder, and salt.",
                "Spread on a baking sheet; roast at 200 °C for 12–15 minutes until slightly crispy.",
                "Squeeze lime juice over the top; serve warm or at room temperature."
            ),
            antiInflammatoryScore = 8
        ),

        Recipe(
            id = "big_soy_chunk_snack",
            name = "Double Soy Chunk Protein Snack",
            description = "65 g of dry soy chunks — the largest soy serving in the snack section — simmered in a bold spiced broth for 34 g of protein. At under 230 calories, it is the most protein-dense snack per calorie in the catalog.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            collection = emptyList(),
            ingredients = listOf(
                "65g soy chunks (dry weight)",
                "1 cup vegetable broth",
                "½ tsp cumin",
                "½ tsp coriander powder",
                "¼ tsp turmeric",
                "¼ tsp chilli powder",
                "1 tsp lemon juice",
                "Salt to taste"
            ),
            calories = 230, proteinGrams = 34.0, carbGrams = 12.0, fatGrams = 2.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Boil soy chunks in broth 8 minutes until tender; drain and squeeze dry.",
                "Return to pan with cumin, coriander, turmeric, and chilli powder.",
                "Cook on medium heat 3–4 minutes, stirring often.",
                "Finish with lemon juice and salt. Serve hot."
            )
        ),

        Recipe(
            id = "protein_shake_plus",
            name = "High-Protein Shake Plus",
            description = "One scoop of protein powder blended with Greek yogurt and oat milk — a creamy shake that delivers 35 g of protein in under 250 calories. The yogurt creates a thicker, creamier texture than almond milk alone.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            collection = emptyList(),
            ingredients = listOf(
                "1 scoop vanilla or chocolate protein powder",
                "½ cup non-fat Greek yogurt",
                "¾ cup oat milk",
                "3–4 ice cubes"
            ),
            calories = 240, proteinGrams = 35.0, carbGrams = 22.0, fatGrams = 3.0, fiberGrams = 1.0,
            prepMinutes = 2, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to a shaker bottle or blender.",
                "Shake or blend until smooth.",
                "Serve immediately."
            )
        ),

        Recipe(
            id = "tempeh_tikka_snack",
            name = "Tandoori Tempeh Protein Bites",
            description = "Tempeh sliced thin, marinated in a quick tandoori spice blend, and pan-crisped for 32 g of protein in a snack under 280 calories. A spicier, higher-protein vegan alternative to the Tandoori Paneer Bites.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            collection = emptyList(),
            ingredients = listOf(
                "170g tempeh (sliced)",
                "1 tbsp tamari or soy sauce",
                "1 tsp tandoori spice blend",
                "½ tsp garlic powder",
                "½ tsp smoked paprika",
                "1 tsp olive oil",
                "Squeeze of lemon juice"
            ),
            calories = 270, proteinGrams = 32.0, carbGrams = 10.0, fatGrams = 12.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Mix tamari, tandoori spice, garlic powder, and smoked paprika; toss with tempeh.",
                "Rest 5 minutes.",
                "Heat olive oil in a non-stick pan over medium-high heat.",
                "Fry tempeh 4–5 minutes per side until crispy and golden.",
                "Finish with a squeeze of lemon. Serve immediately."
            )
        ),

        Recipe(
            id = "soy_edamame_protein_bowl",
            name = "Soy Chunk & Edamame Protein Snack Bowl",
            description = "Warm edamame paired with a small serving of spiced soy chunks for a filling 33 g protein snack. Two soy foods — one chewy, one soft — seasoned with sesame and chilli.",
            mealType = "Snack",
            tags = listOf("Vegan", "High Protein"),
            collection = emptyList(),
            ingredients = listOf(
                "1 cup shelled edamame (cooked)",
                "30g soy chunks (dry weight)",
                "½ tsp sesame oil",
                "1 tsp low-sodium soy sauce",
                "¼ tsp chilli flakes",
                "½ tsp garlic powder",
                "1 tsp sesame seeds"
            ),
            calories = 280, proteinGrams = 33.0, carbGrams = 22.0, fatGrams = 10.0, fiberGrams = 10.0,
            prepMinutes = 10, cookMinutes = 8,
            instructions = listOf(
                "Soak soy chunks in boiling water 8 minutes; drain and squeeze dry.",
                "Heat sesame oil; toss soy chunks with soy sauce, chilli flakes, and garlic powder. Cook 3–4 minutes.",
                "Warm edamame; combine with soy chunks.",
                "Serve topped with sesame seeds."
            )
        ),

        Recipe(
            id = "cinnamon_cottage_cheese_snack",
            name = "Cinnamon Cottage Cheese Snack Bowl",
            description = "A sweet, satisfying snack bowl built on cottage cheese — one of the highest casein-protein foods available. A half-scoop of protein powder boosts it to 28 g without making it feel like a gym shake.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "¾ cup low-fat cottage cheese",
                "½ scoop vanilla protein powder",
                "1 banana (sliced)",
                "¼ tsp cinnamon",
                "1 tsp honey"
            ),
            calories = 270, proteinGrams = 28.0, carbGrams = 32.0, fatGrams = 3.0, fiberGrams = 2.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Stir protein powder into cottage cheese until smooth.",
                "Top with banana slices.",
                "Drizzle honey and sprinkle cinnamon. Serve immediately."
            ),
            metabolicResetScore = 7, isGlucoseConscious = true
        ),

        Recipe(
            id = "high_protein_smoothie_snack",
            name = "High-Protein Berry Smoothie",
            description = "A thicker, higher-protein version of the standard protein shake: Greek yogurt replaces half the liquid for 30 g of protein in a smooth drinkable snack that keeps hunger at bay for hours.",
            mealType = "Snack",
            tags = listOf("Vegetarian", "High Protein"),
            collection = emptyList(),
            ingredients = listOf(
                "¾ cup non-fat Greek yogurt",
                "½ scoop vanilla protein powder",
                "½ cup frozen mixed berries",
                "½ cup oat milk",
                "1 tsp honey",
                "3 ice cubes"
            ),
            calories = 240, proteinGrams = 30.0, carbGrams = 32.0, fatGrams = 2.0, fiberGrams = 4.0,
            prepMinutes = 3, cookMinutes = 0,
            instructions = listOf(
                "Add all ingredients to a blender.",
                "Blend on high 30 seconds until smooth.",
                "Serve immediately."
            )
        )
    )
}
