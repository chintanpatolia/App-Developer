package com.dailyhealthcoach.ui.recipes

import com.dailyhealthcoach.ui.recipes.catalog.BoosterRecipes
import com.dailyhealthcoach.ui.recipes.catalog.BreakfastRecipes
import com.dailyhealthcoach.ui.recipes.catalog.BreakfastRecipes2
import com.dailyhealthcoach.ui.recipes.catalog.DinnerRecipes
import com.dailyhealthcoach.ui.recipes.catalog.DinnerRecipes2
import com.dailyhealthcoach.ui.recipes.catalog.LunchRecipes
import com.dailyhealthcoach.ui.recipes.catalog.LunchRecipes2
import com.dailyhealthcoach.ui.recipes.catalog.SnackRecipes

object RecipeCatalog {

    val ALL: List<Recipe> =
        BreakfastRecipes.ALL +
        BreakfastRecipes2.ALL +
        LunchRecipes.ALL +
        LunchRecipes2.ALL +
        DinnerRecipes.ALL +
        DinnerRecipes2.ALL +
        SnackRecipes.ALL +
        BoosterRecipes.ALL
}
