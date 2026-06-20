package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object BreakfastRecipes {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "greek_yogurt_bowl",
            name = "High-Protein Greek Yogurt Bowl",
            description = "Thick Greek yogurt boosted with protein powder, layered with antioxidant-rich berries and a drizzle of honey for natural sweetness.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Nut Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "protein_oats",
            name = "Protein Overnight Oats with Berries",
            description = "Creamy overnight oats with protein powder, topped with anti-inflammatory berries and warming cinnamon. Make the night before for a stress-free morning.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "",
            restrictions = listOf("Dairy Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 5,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 5,
            womensHealthScore = 6,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "egg_white_scramble",
            name = "Egg White Veggie Scramble",
            description = "Light, high-protein scramble loaded with colourful vegetables. Low in calories and carbs — ideal for blood sugar management.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein", "Low Calorie"),
            proteinSource = "Eggs",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free"),
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
            metabolicResetScore = 9,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 8,
            womensHealthScore = 5,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "moong_dal_chilla",
            name = "Savory Moong Dal Chilla",
            description = "Protein-rich savory pancakes made from soaked split moong dal blended with ginger and spices. A classic Indian breakfast that keeps blood sugar steady.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 9,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "masala_omelette",
            name = "Masala Herb Omelette",
            description = "A fluffy 3-egg omelette seasoned with Indian spices, fresh tomatoes, and coriander. High in protein and naturally low in carbs.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein", "Low Carb"),
            proteinSource = "Eggs",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free"),
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
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 5,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "golden_milk_oats",
            name = "Golden Milk Overnight Oats",
            description = "Overnight oats infused with turmeric and ginger — two of the most potent anti-inflammatory compounds in the kitchen. Creamy, warming, and ready in the morning.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "",
            restrictions = listOf("Dairy Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 6,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "green_protein_smoothie",
            name = "Green Protein & Ginger Smoothie",
            description = "A vibrant green smoothie powered by spinach, ginger, and protein powder. Anti-inflammatory ginger and leafy greens pair with banana for natural sweetness.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "",
            restrictions = listOf("Dairy Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 5,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 5,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "besan_cheela",
            name = "High-Protein Besan Cheela",
            description = "Chickpea flour pancakes seasoned with ajwain and asafoetida — a traditional Indian breakfast that packs impressive plant protein and soluble fiber for sustained energy.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "avocado_egg_toast",
            name = "Avocado & Poached Egg Whole-Grain Toast",
            description = "Creamy avocado on whole-grain toast with a perfectly poached egg. Avocado's monounsaturated fats are anti-inflammatory; the egg provides complete protein.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Eggs",
            restrictions = listOf("Dairy Free", "Nut Free", "Soy Free"),
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
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "berry_baked_oats",
            name = "Mixed Berry Baked Protein Oats",
            description = "Warm, comforting baked oats studded with antioxidant-rich berries and enriched with protein powder. Prep once; enjoy all week.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Eggs",
            restrictions = listOf("Dairy Free", "Soy Free"),
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
                "Preheat oven to 190°C (375°F).",
                "Mix oats, protein powder, baking powder, and cinnamon in a baking dish.",
                "Whisk together almond milk, egg, and honey; pour over oat mixture.",
                "Scatter berries and walnuts on top.",
                "Bake 25 minutes until set and golden at the edges.",
                "Cut into portions; serve warm or cold."
            ),
            storageNotes = "Refrigerate up to 5 days; reheat with a splash of milk.",
            mealPrepNotes = "Bake a full tray Sunday evening for weekday breakfasts.",
            metabolicResetScore = 6,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 5,
            womensHealthScore = 6,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "paneer_bhurji",
            name = "Paneer Bhurji (Spiced Scrambled Paneer)",
            description = "The Indian equivalent of scrambled eggs — crumbled paneer tossed with onions, tomatoes, and aromatic spices. High in protein and ready in under 15 minutes.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "quinoa_breakfast_bowl",
            name = "Quinoa Protein Breakfast Bowl",
            description = "Warm cooked quinoa — a complete plant protein — topped with berries, crushed walnuts, and a drizzle of honey. Sustained energy from morning to midday.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein", "Whole Foods"),
            proteinSource = "",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 6,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "blueberry_chia_bowl",
            name = "Blueberry Walnut Chia Bowl",
            description = "A thick overnight chia pudding loaded with omega-3 fats, topped with blueberries and walnuts — two of the most well-researched anti-inflammatory foods.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "",
            restrictions = listOf("Dairy Free", "Gluten Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 6,
            antiInflammatoryScore = 9,
            insulinResistanceScore = 7,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "cottage_cheese_pancakes",
            name = "Cottage Cheese Protein Pancakes",
            description = "Fluffy, high-protein pancakes made by blending cottage cheese, oats, eggs, and protein powder into a batter. No flour needed — the oats bind everything and keep the glycaemic load low.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Nut Free", "Soy Free"),
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
            metabolicResetScore = 8,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "cottage_cheese_protein_bowl",
            name = "High-Protein Cottage Cheese Breakfast Bowl",
            description = "The fastest 44g protein breakfast in the app: cottage cheese stirred with protein powder, topped with berries and hemp seeds. No cooking, no blending — just stir and eat.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "double_protein_yogurt_bowl",
            name = "Double-Protein Greek Yogurt Breakfast Bowl",
            description = "Greek yogurt meets a full scoop of protein powder for a thick, spoonable bowl with 42g of protein. Berries and low-fat granola add texture without spiking blood sugar.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Nut Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 8,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "silken_tofu_protein_smoothie",
            name = "Silken Tofu Protein Smoothie Bowl",
            description = "Blended silken tofu adds a velvety, dairy-free protein base to this thick smoothie bowl. One scoop of plant protein powder pushes it to 38g without any powder taste.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
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
            metabolicResetScore = 5,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 5,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),

        Recipe(
            id = "paneer_egg_scramble",
            name = "Paneer & Egg High-Protein Scramble",
            description = "Crumbled paneer scrambled with eggs and vegetables delivers a powerful dual-protein breakfast. The combination of dairy protein from paneer and complete protein from eggs creates a fast-digesting, muscle-sparing morning meal.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free"),
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
            metabolicResetScore = 9,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "mango_lassi_protein_smoothie",
            name = "High-Protein Mango Lassi Smoothie",
            description = "The classic Indian lassi elevated with a full scoop of protein powder. Frozen mango and Greek yogurt create a creamy, naturally sweet drink with 40g of protein.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 7,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 5,
            womensHealthScore = 7,
            glucoseImpactScore = 6
        ),

        Recipe(
            id = "hemp_tofu_power_breakfast",
            name = "Hemp & Tofu High-Protein Breakfast Bowl",
            description = "Silken tofu blended smooth and folded with hemp seeds and plant protein powder — a completely dairy-free, egg-free breakfast hitting 38g of protein.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
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
            metabolicResetScore = 5,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 6,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "protein_cottage_oatmeal",
            name = "Protein-Boosted Cottage Cheese Oatmeal",
            description = "Cottage cheese stirred into warm oatmeal is the oldest protein-stacking trick in bodybuilding — and it works. The mild flavour disappears into the oats while pushing protein to 38g.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Nut Free", "Soy Free", "Egg Free"),
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
            metabolicResetScore = 7,
            antiInflammatoryScore = 4,
            insulinResistanceScore = 6,
            womensHealthScore = 7,
            glucoseImpactScore = 5
        ),
    )
}
