package com.dailyhealthcoach.ui.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dailyhealthcoach.ui.premium.MainFeatureCard
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PositiveAccent
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard
import com.dailyhealthcoach.ui.theme.WarningAccent

@Composable
fun RecipesRoute(viewModel: RecipesViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    uiState.selectedRecipe?.let { recipe ->
        RecipeDetailDialog(
            recipe = recipe,
            onDismiss = viewModel::deselectRecipe,
            onLog = { mealName -> viewModel.logRecipe(recipe, mealName) }
        )
    }

    MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "Recipes",
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    "Meal ideas based on your goals and remaining macros.",
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            RemainingMacrosCard(
                remainingCalories = uiState.remainingCalories,
                remainingProtein = uiState.remainingProtein
            )

            uiState.logSuccessMessage?.let { msg ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(msg, color = PositiveAccent, style = MaterialTheme.typography.bodySmall)
                    TextButton(onClick = viewModel::dismissLogMessage) {
                        Text("Dismiss", color = MutedText, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            if (uiState.recommendedRecipes.isNotEmpty()) {
                RecipeSection(
                    title = "Recommended for Today",
                    recipes = uiState.recommendedRecipes,
                    onViewRecipe = viewModel::selectRecipe
                )
            }

            if (uiState.breakfastRecipes.isNotEmpty()) {
                RecipeSection(
                    title = "Breakfast",
                    recipes = uiState.breakfastRecipes,
                    onViewRecipe = viewModel::selectRecipe
                )
            }

            if (uiState.lunchRecipes.isNotEmpty()) {
                RecipeSection(
                    title = "Lunch",
                    recipes = uiState.lunchRecipes,
                    onViewRecipe = viewModel::selectRecipe
                )
            }

            if (uiState.dinnerRecipes.isNotEmpty()) {
                RecipeSection(
                    title = "Dinner",
                    recipes = uiState.dinnerRecipes,
                    onViewRecipe = viewModel::selectRecipe
                )
            }

            if (uiState.snackRecipes.isNotEmpty()) {
                RecipeSection(
                    title = "Snacks",
                    recipes = uiState.snackRecipes,
                    onViewRecipe = viewModel::selectRecipe
                )
            }
        }
    }
}

@Composable
private fun RemainingMacrosCard(remainingCalories: Int, remainingProtein: Double) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = SecondaryCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "${remainingCalories.coerceAtLeast(0)}",
                    color = CyanAccent,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text("kcal remaining", color = MutedText, style = MaterialTheme.typography.labelSmall)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "${remainingProtein.coerceAtLeast(0.0).let { if (it == kotlin.math.floor(it)) it.toLong().toString() else "%.1f".format(it) }}g",
                    color = if (remainingProtein > 30) WarningAccent else PositiveAccent,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text("protein remaining", color = MutedText, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun RecipeSection(
    title: String,
    recipes: List<Recipe>,
    onViewRecipe: (Recipe) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(title, color = PrimaryText, fontWeight = FontWeight.Bold)
        recipes.forEach { recipe ->
            RecipeCard(recipe = recipe, onView = { onViewRecipe(recipe) })
        }
    }
}

@Composable
private fun RecipeCard(recipe: Recipe, onView: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SecondaryCard.copy(alpha = 0.72f))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                recipe.name,
                color = PrimaryText,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                "${recipe.prepMinutes}m",
                color = MutedText,
                style = MaterialTheme.typography.labelSmall
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                "${recipe.calories} kcal  P${recipe.proteinGrams.clean()}g",
                color = CyanAccent,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            recipe.tags.take(3).forEach { tag ->
                Text(
                    tag,
                    color = MutedText,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MainCard)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
        Text(
            recipe.ingredients.take(3).joinToString(" · "),
            color = MutedText,
            style = MaterialTheme.typography.bodySmall
        )
        OutlinedButton(
            onClick = onView,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(999.dp)
        ) {
            Text("View Recipe", color = CyanAccent, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun RecipeDetailDialog(
    recipe: Recipe,
    onDismiss: () -> Unit,
    onLog: (String) -> Unit
) {
    var selectedMeal by remember(recipe.id) { mutableStateOf(recipe.mealType) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            color = MainCard
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    recipe.name,
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    recipe.tags.forEach { tag ->
                        Text(
                            tag,
                            color = MutedText,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SecondaryCard)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MacroChip("${recipe.calories}", "kcal")
                    MacroChip("${recipe.proteinGrams.clean()}g", "protein")
                    MacroChip("${recipe.carbGrams.clean()}g", "carbs")
                    MacroChip("${recipe.fatGrams.clean()}g", "fat")
                }

                Text(
                    "Prep: ${recipe.prepMinutes} min",
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    "Ingredients",
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold
                )
                recipe.ingredients.forEach { ingredient ->
                    Text("• $ingredient", color = MutedText, style = MaterialTheme.typography.bodySmall)
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    "Instructions",
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold
                )
                recipe.instructions.forEachIndexed { index, step ->
                    Text(
                        "${index + 1}. $step",
                        color = PrimaryText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    "Log this meal",
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").forEach { meal ->
                        FilterChip(
                            selected = selectedMeal == meal,
                            onClick = { selectedMeal = meal },
                            label = {
                                Text(meal.take(5), style = MaterialTheme.typography.labelSmall)
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CyanAccent,
                                selectedLabelColor = Color.Black
                            )
                        )
                    }
                }

                Button(
                    onClick = { onLog(selectedMeal) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                ) {
                    Text("Add to Nutrition Log", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close", color = MutedText)
                }
            }
        }
    }
}

@Composable
private fun MacroChip(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = CyanAccent, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
        Text(label, color = MutedText, style = MaterialTheme.typography.labelSmall)
    }
}

private fun Double.clean(): String =
    if (this == kotlin.math.floor(this) && this < 1_000_000) this.toLong().toString()
    else "%.1f".format(this).trimEnd('0').trimEnd('.')
