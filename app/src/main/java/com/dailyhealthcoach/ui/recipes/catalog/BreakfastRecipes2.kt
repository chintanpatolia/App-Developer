package com.dailyhealthcoach.ui.recipes.catalog

import com.dailyhealthcoach.ui.recipes.Recipe

object BreakfastRecipes2 {
    val ALL: List<Recipe> = listOf(

        Recipe(
            id = "masala_egg_bhurji",
            name = "Masala Egg Bhurji",
            description = "A bold, spiced Indian scrambled egg dish cooked with tomato, onion, and garam masala — one of the highest-protein breakfasts in the plan at 38 g. Ready in 10 minutes and naturally gluten-free.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein", "Whole Foods"),
            proteinSource = "Eggs",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "4 whole eggs + 2 egg whites",
                "1 medium tomato (finely chopped)",
                "1 small onion (finely chopped)",
                "1 green chilli (minced, optional)",
                "½ tsp cumin seeds",
                "½ tsp turmeric",
                "½ tsp coriander powder",
                "¼ tsp garam masala",
                "½ tsp oil",
                "Salt to taste",
                "2 tbsp fresh coriander (chopped)"
            ),
            calories = 380, proteinGrams = 38.0, carbGrams = 10.0, fatGrams = 22.0, fiberGrams = 2.0,
            prepMinutes = 5, cookMinutes = 10,
            instructions = listOf(
                "Heat oil in a non-stick pan; add cumin seeds and let them splutter.",
                "Add onion and cook 3 minutes until softened; add chilli and tomato.",
                "Cook tomato 3 minutes until broken down; add turmeric, coriander powder, and salt.",
                "Whisk eggs and egg whites together; pour into the pan.",
                "Fold gently on medium-low heat until just set — do not overcook.",
                "Finish with garam masala and fresh coriander."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "paneer_poha_bowl",
            name = "High-Protein Paneer Poha",
            description = "The classic Indian breakfast of flattened rice (poha) elevated with generous cubed paneer, green peas, and turmeric. The poha is soaked, not cooked — just lightly stir-fried — keeping the glucoseImpactScore at 4 despite the grain base.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "¾ cup thick poha / flattened rice (90g, rinsed and drained)",
                "150g paneer (cubed)",
                "¼ cup green peas (fresh or frozen)",
                "1 small onion (sliced)",
                "½ tsp mustard seeds",
                "8–10 curry leaves",
                "¼ tsp turmeric",
                "1 tsp lemon juice",
                "½ tsp oil",
                "Salt to taste",
                "2 tbsp fresh coriander (chopped)"
            ),
            calories = 430, proteinGrams = 32.0, carbGrams = 42.0, fatGrams = 16.0, fiberGrams = 4.0,
            prepMinutes = 8, cookMinutes = 8,
            instructions = listOf(
                "Rinse poha in a colander under cold water until softened, about 1 minute; drain thoroughly.",
                "Heat oil in a wide pan; add mustard seeds and curry leaves and let them crackle.",
                "Add onion and cook 3 minutes; add peas and cook 2 minutes.",
                "Add paneer and brown lightly 2–3 minutes.",
                "Add poha, turmeric, and salt; toss gently over low heat for 2 minutes.",
                "Finish with lemon juice and coriander."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 5,
            insulinResistanceScore = 7,
            womensHealthScore = 6,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "red_lentil_dosa_bowl",
            name = "Red Lentil Dosa with Coconut Chutney",
            description = "Thin, crispy dosa made purely from blended red lentils — no rice, no fermentation — served with a small side of fresh coconut chutney. A naturally gluten-free, high-fibre, 28 g protein breakfast.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 cup red lentils (soaked 2 h, drained)",
                "½ tsp cumin seeds",
                "¼ tsp turmeric",
                "1 green chilli (optional)",
                "Salt to taste",
                "Oil spray",
                "Coconut chutney to serve (2 tbsp desiccated coconut + 1 tbsp lemon juice + salt)"
            ),
            calories = 360, proteinGrams = 28.0, carbGrams = 54.0, fatGrams = 4.0, fiberGrams = 12.0,
            prepMinutes = 10, cookMinutes = 15,
            instructions = listOf(
                "Blend soaked lentils with ¾ cup water, cumin, turmeric, chilli, and salt until smooth.",
                "Heat a non-stick pan over medium-high heat; spray with oil.",
                "Pour a thin circle of batter (~⅓ cup); spread outward quickly.",
                "Cook 2–3 minutes until edges lift; flip and cook 1 minute.",
                "For chutney: mix desiccated coconut with lemon juice, salt, and 1 tbsp water.",
                "Serve dosas with coconut chutney immediately."
            ),
            mealPrepNotes = "Soaked lentils blend in 30 seconds; batter keeps refrigerated 24 h.",
            metabolicResetScore = 9,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "tempeh_breakfast_hash",
            name = "Tempeh & Sweet Potato Breakfast Hash",
            description = "Crumbled tempeh pan-fried with diced sweet potato, bell pepper, and smoky spices — a vegan breakfast hash delivering 28 g of plant protein and sustained energy from complex carbohydrates.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "150g tempeh (crumbled)",
                "150g sweet potato (peeled, diced small)",
                "½ red bell pepper (diced)",
                "1 small onion (diced)",
                "1 tsp smoked paprika",
                "½ tsp cumin",
                "½ tsp garlic powder",
                "¼ tsp chilli flakes",
                "1 tsp olive oil",
                "Salt and pepper to taste",
                "Fresh parsley or coriander to garnish"
            ),
            calories = 380, proteinGrams = 28.0, carbGrams = 38.0, fatGrams = 13.0, fiberGrams = 7.0,
            prepMinutes = 8, cookMinutes = 18,
            instructions = listOf(
                "Boil or microwave sweet potato 5 minutes until just tender; drain.",
                "Heat oil in a wide non-stick pan over medium-high heat.",
                "Add sweet potato and cook 5 minutes without stirring to develop colour.",
                "Add onion and pepper; cook 3 minutes. Add crumbled tempeh and all spices.",
                "Cook 5–6 minutes, stirring occasionally, until tempeh is lightly browned.",
                "Season and garnish with fresh herbs."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 7,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "soy_milk_quinoa_bowl",
            name = "Soy Milk Quinoa Protein Bowl",
            description = "Quinoa cooked in unsweetened soy milk instead of water, topped with chia seeds and sliced banana. The protein-rich cooking liquid boosts the bowl to 32 g of complete plant protein while keeping the prep time to just 15 minutes.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Soy",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "¾ cup dry quinoa (rinsed)",
                "1½ cups unsweetened soy milk",
                "1 tbsp chia seeds",
                "½ banana (sliced)",
                "1 tsp maple syrup",
                "¼ tsp cinnamon",
                "Pinch of salt"
            ),
            calories = 420, proteinGrams = 32.0, carbGrams = 58.0, fatGrams = 9.0, fiberGrams = 8.0,
            prepMinutes = 3, cookMinutes = 15,
            instructions = listOf(
                "Combine quinoa, soy milk, salt, and cinnamon in a saucepan.",
                "Bring to a boil; reduce heat to low, cover, and simmer 12–14 minutes until liquid is absorbed.",
                "Remove from heat; rest covered 2 minutes, then fluff with a fork.",
                "Transfer to a bowl; top with chia seeds, banana, and maple syrup."
            ),
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "tofu_chia_overnight_bowl",
            name = "Tofu & Chia Overnight Breakfast Bowl",
            description = "Silken tofu blended with chia seeds, vanilla, and almond milk creates a thick, mousse-like overnight bowl with 28 g of plant protein. Chill overnight and top with berries for a genuinely convenient high-protein vegan breakfast.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "300g silken tofu",
                "3 tbsp chia seeds",
                "¾ cup unsweetened almond milk",
                "1 tsp vanilla extract",
                "1 tsp maple syrup",
                "¼ tsp cinnamon",
                "½ cup mixed berries (topping)"
            ),
            calories = 370, proteinGrams = 28.0, carbGrams = 28.0, fatGrams = 16.0, fiberGrams = 9.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Blend silken tofu, almond milk, vanilla, maple syrup, and cinnamon until completely smooth.",
                "Stir in chia seeds; pour into a jar or bowl.",
                "Stir again after 15 minutes to prevent chia from clumping.",
                "Refrigerate overnight or at least 4 hours.",
                "Top with mixed berries before serving."
            ),
            storageNotes = "Keeps refrigerated up to 3 days; add berries fresh each morning.",
            metabolicResetScore = 7,
            antiInflammatoryScore = 8,
            insulinResistanceScore = 7,
            womensHealthScore = 8,
            glucoseImpactScore = 3
        ),

        Recipe(
            id = "greek_granola_protein_bowl",
            name = "Greek Yogurt Granola Protein Bowl",
            description = "A generous serving of thick non-fat Greek yogurt paired with low-sugar granola, walnuts, and fresh berries. At 38 g of protein from dairy alone, this is the highest-protein yogurt breakfast in the plan.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Greek Yogurt",
            restrictions = listOf("Soy Free", "Egg Free"),
            collection = listOf("Anti-Inflammatory"),
            ingredients = listOf(
                "1½ cups non-fat Greek yogurt (340g)",
                "¼ cup low-sugar granola (30g)",
                "2 tbsp walnuts (roughly chopped)",
                "½ cup mixed berries",
                "1 tsp honey",
                "¼ tsp cinnamon"
            ),
            calories = 450, proteinGrams = 38.0, carbGrams = 44.0, fatGrams = 11.0, fiberGrams = 4.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Spoon Greek yogurt into a wide bowl.",
                "Layer granola, walnuts, and berries over the yogurt.",
                "Drizzle with honey and dust with cinnamon.",
                "Serve immediately; granola softens if left to stand."
            ),
            metabolicResetScore = 7,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 7,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "egg_white_mini_muffins",
            name = "Spiced Egg White Mini Muffins",
            description = "A batch of six baked egg white muffins filled with spinach, onion, and cumin — 36 g of complete protein in a portable, meal-prep-friendly format that stores refrigerated for five days.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Eggs",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "8 egg whites + 2 whole eggs",
                "1 cup baby spinach (chopped)",
                "¼ cup red onion (finely diced)",
                "¼ cup red bell pepper (finely diced)",
                "½ tsp cumin powder",
                "¼ tsp turmeric",
                "¼ tsp chilli flakes",
                "Salt and pepper to taste",
                "Oil spray for muffin tin"
            ),
            calories = 320, proteinGrams = 36.0, carbGrams = 6.0, fatGrams = 15.0, fiberGrams = 1.0,
            prepMinutes = 10, cookMinutes = 20,
            instructions = listOf(
                "Preheat oven to 180°C. Spray a 6-cup muffin tin with oil.",
                "Whisk egg whites, whole eggs, cumin, turmeric, chilli, salt, and pepper.",
                "Divide spinach, onion, and pepper evenly among muffin cups.",
                "Pour egg mixture over the vegetables, filling cups ¾ full.",
                "Bake 18–20 minutes until the centres are set and lightly golden.",
                "Cool 5 minutes before removing from tin."
            ),
            storageNotes = "Refrigerate up to 5 days; microwave 45 seconds to reheat.",
            metabolicResetScore = 9,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 1
        ),

        Recipe(
            id = "paneer_spinach_egg_skillet",
            name = "Paneer & Spinach Egg Skillet",
            description = "A one-pan skillet of pan-seared paneer, wilted spinach, and whole eggs baked in a spiced tomato base. The combination of dairy and egg protein pushes the total to 40 g — the highest protein breakfast in the plan.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Paneer",
            restrictions = listOf("Gluten Free", "Nut Free", "Soy Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "100g paneer (cubed)",
                "3 whole eggs",
                "2 cups baby spinach",
                "1 medium tomato (chopped)",
                "1 small onion (sliced)",
                "½ tsp cumin",
                "¼ tsp turmeric",
                "¼ tsp garam masala",
                "½ tsp oil",
                "Salt and chilli flakes to taste"
            ),
            calories = 420, proteinGrams = 40.0, carbGrams = 12.0, fatGrams = 25.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 15,
            instructions = listOf(
                "Heat oil in an oven-safe skillet over medium heat; sear paneer cubes 2–3 minutes per side until golden.",
                "Add onion and cook 3 minutes; add tomato, cumin, turmeric, and salt.",
                "Cook tomato down 3 minutes; add spinach and stir until wilted.",
                "Make three wells in the mixture; crack one egg into each well.",
                "Cover and cook on low heat 5–6 minutes until egg whites are set but yolks are still runny.",
                "Dust with garam masala and chilli flakes; serve from the pan."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 9,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),

        Recipe(
            id = "ragi_protein_porridge",
            name = "Ragi & Lentil Protein Porridge",
            description = "Finger millet (ragi) cooked with moong dal in a savoury preparation — a traditional South Indian breakfast combination with exceptional fibre, iron, and a combined 28 g of plant protein.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "Whole Foods"),
            proteinSource = "Lentils",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset", "Anti-Inflammatory"),
            ingredients = listOf(
                "½ cup ragi flour (finger millet, 60g)",
                "¼ cup yellow moong dal (soaked 30 min)",
                "2 cups water",
                "¼ tsp cumin seeds",
                "¼ tsp turmeric",
                "1 tsp ghee or coconut oil",
                "Salt to taste",
                "1 tbsp fresh coriander (chopped)"
            ),
            calories = 350, proteinGrams = 28.0, carbGrams = 58.0, fatGrams = 5.0, fiberGrams = 7.0,
            prepMinutes = 5, cookMinutes = 20,
            instructions = listOf(
                "Cook soaked moong dal in 1½ cups water on medium heat until soft, about 12 minutes.",
                "Whisk ragi flour with ½ cup cold water until lump-free; add to the cooked dal.",
                "Stir continuously over low heat 6–8 minutes until thick and cooked through.",
                "In a small pan heat ghee; add cumin and let it splutter; pour over porridge.",
                "Season with salt; garnish with coriander. Serve warm."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 8,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "cottage_cheese_herb_wrap",
            name = "Cottage Cheese & Herb Breakfast Wrap",
            description = "A whole-grain wrap generously filled with herbed cottage cheese, sliced avocado, and spinach — 35 g of slow-digesting casein protein in a portable, no-cook breakfast that travels well.",
            mealType = "Breakfast",
            tags = listOf("Vegetarian", "High Protein"),
            proteinSource = "Cottage Cheese",
            restrictions = listOf("Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "1 large whole-grain wrap or tortilla",
                "¾ cup low-fat cottage cheese (170g)",
                "¼ avocado (sliced)",
                "1 cup baby spinach",
                "2 tbsp fresh chives or spring onion",
                "¼ tsp black pepper",
                "½ tsp dried herbs (oregano or dill)",
                "1 tsp lemon juice"
            ),
            calories = 430, proteinGrams = 35.0, carbGrams = 44.0, fatGrams = 10.0, fiberGrams = 7.0,
            prepMinutes = 5, cookMinutes = 0,
            instructions = listOf(
                "Mix cottage cheese with chives, dried herbs, black pepper, and lemon juice.",
                "Lay wrap flat; spread cottage cheese mixture in the centre.",
                "Layer spinach and avocado slices over the cottage cheese.",
                "Fold sides in and roll tightly; slice diagonally."
            ),
            storageNotes = "Wrap tightly in cling film and refrigerate up to 12 h; avocado browns after that.",
            metabolicResetScore = 8,
            antiInflammatoryScore = 6,
            insulinResistanceScore = 7,
            womensHealthScore = 7,
            glucoseImpactScore = 4
        ),

        Recipe(
            id = "tofu_masala_scramble",
            name = "Indian Spiced Tofu Masala Scramble",
            description = "Firm tofu crumbled and cooked with onion, tomato, and a sharp masala spice blend — a vegan egg-bhurji substitute with 30 g of plant protein that is ready in 12 minutes and indistinguishable from the real thing.",
            mealType = "Breakfast",
            tags = listOf("Vegan", "High Protein"),
            proteinSource = "Tofu",
            restrictions = listOf("Dairy Free", "Gluten Free", "Nut Free", "Soy Free", "Egg Free"),
            collection = listOf("Metabolic Reset"),
            ingredients = listOf(
                "300g firm tofu (pressed and crumbled)",
                "1 small onion (finely diced)",
                "1 medium tomato (finely chopped)",
                "1 green chilli (optional)",
                "½ tsp turmeric",
                "½ tsp cumin seeds",
                "½ tsp coriander powder",
                "¼ tsp garam masala",
                "1 tsp oil",
                "Salt to taste",
                "2 tbsp fresh coriander"
            ),
            calories = 285, proteinGrams = 30.0, carbGrams = 12.0, fatGrams = 14.0, fiberGrams = 3.0,
            prepMinutes = 5, cookMinutes = 12,
            instructions = listOf(
                "Press tofu between paper towels and crumble into coarse pieces resembling scrambled egg.",
                "Heat oil in a non-stick pan; add cumin seeds and let them splutter.",
                "Add onion and cook 3 minutes; add tomato and chilli and cook 3 minutes until soft.",
                "Stir in turmeric, coriander powder, and salt; add crumbled tofu.",
                "Cook on medium-high heat 4–5 minutes, stirring to coat tofu in spices.",
                "Finish with garam masala and fresh coriander."
            ),
            metabolicResetScore = 9,
            antiInflammatoryScore = 7,
            insulinResistanceScore = 8,
            womensHealthScore = 7,
            glucoseImpactScore = 2
        ),
    )
}
