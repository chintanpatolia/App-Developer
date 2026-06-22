package com.dailyhealthcoach.ui.recipes

import com.dailyhealthcoach.ui.recipes.catalog.BoosterRecipes
import com.dailyhealthcoach.ui.recipes.catalog.BoosterRecipes2
import com.dailyhealthcoach.ui.recipes.catalog.BreakfastRecipes
import com.dailyhealthcoach.ui.recipes.catalog.BreakfastRecipes2
import com.dailyhealthcoach.ui.recipes.catalog.BreakfastRecipes3
import com.dailyhealthcoach.ui.recipes.catalog.DinnerRecipes
import com.dailyhealthcoach.ui.recipes.catalog.DinnerRecipes2
import com.dailyhealthcoach.ui.recipes.catalog.DinnerRecipes3
import com.dailyhealthcoach.ui.recipes.catalog.LunchRecipes
import com.dailyhealthcoach.ui.recipes.catalog.LunchRecipes2
import com.dailyhealthcoach.ui.recipes.catalog.LunchRecipes3
import com.dailyhealthcoach.ui.recipes.catalog.SnackRecipes
import com.dailyhealthcoach.ui.recipes.catalog.SnackRecipes2

object RecipeCatalog {

    val ALL: List<Recipe> =
        BreakfastRecipes.ALL +
        BreakfastRecipes2.ALL +
        BreakfastRecipes3.ALL +
        LunchRecipes.ALL +
        LunchRecipes2.ALL +
        LunchRecipes3.ALL +
        DinnerRecipes.ALL +
        DinnerRecipes2.ALL +
        DinnerRecipes3.ALL +
        SnackRecipes.ALL +
        SnackRecipes2.ALL +
        BoosterRecipes.ALL +
        BoosterRecipes2.ALL
}
