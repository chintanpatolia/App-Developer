package com.dailyhealthcoach.ui.recipes

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dailyhealthcoach.ui.premium.MainFeatureCard
import com.dailyhealthcoach.ui.theme.AccentBlue
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MainCard
import com.dailyhealthcoach.ui.theme.MutedControl
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PositiveAccent
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.dailyhealthcoach.ui.theme.SecondaryCard
import com.dailyhealthcoach.ui.theme.WarningAccent

@Composable
fun RecipesRoute(viewModel: RecipesViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val calState by viewModel.mealCalendarUiState.collectAsState()
    val draftState by viewModel.draftPlanUiState.collectAsState()
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current

    // ── Recipe selection grocery list ──────────────────────────────────────
    if (uiState.groceryListOpen) {
        GroceryListDialog(
            title = "Grocery List",
            items = uiState.groceryItems,
            checkedKeys = uiState.checkedGroceryKeys,
            onToggleItem = viewModel::toggleGroceryItem,
            onCopy = { clipboard.setText(AnnotatedString(viewModel.getGroceryShareText())) },
            onShare = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, viewModel.getGroceryShareText())
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share Grocery List"))
            },
            onDismiss = viewModel::closeGroceryList
        )
    }

    // ── Week plan grocery list ─────────────────────────────────────────────
    if (calState.weekGroceryListOpen) {
        GroceryListDialog(
            title = "Week Grocery List",
            items = calState.weekGroceryItems,
            checkedKeys = calState.weekGroceryCheckedKeys,
            onToggleItem = viewModel::toggleWeekGroceryItem,
            onCopy = { clipboard.setText(AnnotatedString(viewModel.getWeekGroceryShareText())) },
            onShare = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, viewModel.getWeekGroceryShareText())
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share Week Grocery List"))
            },
            onDismiss = viewModel::closeWeekGroceryList
        )
    }

    // ── Recipe detail (from browse/recommendations) ────────────────────────
    uiState.selectedRecipe?.let { recipe ->
        RecipeDetailDialog(
            recipe = recipe,
            onDismiss = viewModel::deselectRecipe,
            onLog = { mealName -> viewModel.logRecipe(recipe, mealName) }
        )
    }

    // ── Calendar meal detail ───────────────────────────────────────────────
    calState.selectedSlot?.let { slot ->
        slot.recipe?.let { recipe ->
            CalendarMealDetailDialog(
                slot = slot,
                recipe = recipe,
                replacementCandidates = calState.replacementCandidates,
                onDismiss = viewModel::dismissCalendarMeal,
                onLog = { mealName -> viewModel.logCalendarMeal(recipe, mealName) },
                onLoadAlternatives = { viewModel.loadCalendarMealAlternatives(slot.date, slot.mealType) },
                onReplaceWith = { chosen -> viewModel.replaceCalendarMealWith(slot.date, slot.mealType, chosen) },
                onClearAlternatives = viewModel::clearCalendarAlternatives
            )
        }
    }

    // ── Draft plan review ─────────────────────────────────────────────────
    draftState?.let { draft ->
        PlanReviewDialog(
            draft = draft,
            calorieTarget = uiState.calorieTarget,
            proteinTarget = uiState.proteinTarget,
            onAccept = viewModel::acceptDraftPlan,
            onRegenerate = viewModel::regenerateWeekPlan,
            onCancel = viewModel::cancelDraftPlan,
            onReplaceMeal = { date, mealType -> viewModel.replaceDraftMeal(date, mealType) }
        )
    }

    MainFeatureCard(modifier = Modifier.padding(top = 22.dp)) {
        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
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
                OutlinedButton(
                    onClick = viewModel::openGroceryList,
                    shape = RoundedCornerShape(999.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        "Grocery List",
                        color = CyanAccent,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            MacroHeroBanner(
                remainingCalories = uiState.remainingCalories,
                remainingProtein = uiState.remainingProtein,
                calorieTarget = uiState.calorieTarget,
                proteinTarget = uiState.proteinTarget
            )

            WeeklyMealCalendarSection(
                calState = calState,
                onPrevWeek = { viewModel.navigateCalendarWeek(-1) },
                onNextWeek = { viewModel.navigateCalendarWeek(1) },
                onGenerate = viewModel::generateWeekPlan,
                onRegenerate = viewModel::regenerateWeekPlan,
                onMealTap = { date, mealType -> viewModel.selectCalendarMeal(date, mealType) },
                onWeekGrocery = viewModel::openWeekGroceryList,
                onModeChange = viewModel::setMealPlanMode
            )

            uiState.noAlternateMessage?.let { msg ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(msg, color = MutedText, style = MaterialTheme.typography.bodySmall)
                    TextButton(
                        onClick = viewModel::dismissNoAlternate,
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Text("✕", color = MutedText, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

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

            if (!calState.isGenerated) {
                if (uiState.recommendedRecipes.isNotEmpty()) {
                    RecipeSection(
                        title = "Recommended for Today",
                        recipes = uiState.recommendedRecipes,
                        selectedIds = uiState.selectedRecipeIds,
                        onViewRecipe = viewModel::selectRecipe,
                        onToggleSelect = viewModel::toggleSelection,
                        onTryAnother = viewModel::tryAnother,
                        highlightFirst = true
                    )
                }

                if (uiState.breakfastRecipes.isNotEmpty()) {
                    RecipeSection(
                        title = "Breakfast",
                        recipes = uiState.breakfastRecipes,
                        selectedIds = uiState.selectedRecipeIds,
                        onViewRecipe = viewModel::selectRecipe,
                        onToggleSelect = viewModel::toggleSelection,
                        onTryAnother = null
                    )
                }

                if (uiState.lunchRecipes.isNotEmpty()) {
                    RecipeSection(
                        title = "Lunch",
                        recipes = uiState.lunchRecipes,
                        selectedIds = uiState.selectedRecipeIds,
                        onViewRecipe = viewModel::selectRecipe,
                        onToggleSelect = viewModel::toggleSelection,
                        onTryAnother = null
                    )
                }

                if (uiState.dinnerRecipes.isNotEmpty()) {
                    RecipeSection(
                        title = "Dinner",
                        recipes = uiState.dinnerRecipes,
                        selectedIds = uiState.selectedRecipeIds,
                        onViewRecipe = viewModel::selectRecipe,
                        onToggleSelect = viewModel::toggleSelection,
                        onTryAnother = null
                    )
                }

                if (uiState.snackRecipes.isNotEmpty()) {
                    RecipeSection(
                        title = "Snacks",
                        recipes = uiState.snackRecipes,
                        selectedIds = uiState.selectedRecipeIds,
                        onViewRecipe = viewModel::selectRecipe,
                        onToggleSelect = viewModel::toggleSelection,
                        onTryAnother = null
                    )
                }
            }
        }
    }
}

@Composable
private fun MacroHeroBanner(
    remainingCalories: Int,
    remainingProtein: Double,
    calorieTarget: Int,
    proteinTarget: Double
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val calConsumed = (calorieTarget - remainingCalories).coerceAtLeast(0)
        val proConsumed = (proteinTarget - remainingProtein).coerceAtLeast(0.0)
        MacroHeroTile(
            value = "${remainingCalories.coerceAtLeast(0)}",
            label = "kcal remaining",
            progress = (calConsumed.toFloat() / calorieTarget.toFloat()).coerceIn(0f, 1f),
            color = AccentBlue,
            modifier = Modifier.weight(1f)
        )
        MacroHeroTile(
            value = "${remainingProtein.coerceAtLeast(0.0).let { if (it == kotlin.math.floor(it)) it.toLong().toString() else "%.0f".format(it) }}g",
            label = "protein remaining",
            progress = (proConsumed / proteinTarget).coerceIn(0.0, 1.0).toFloat(),
            color = if (remainingProtein > 30) WarningAccent else PositiveAccent,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun MacroHeroTile(
    value: String,
    label: String,
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = SecondaryCard
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = value,
                color = color,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(text = label, color = MutedText, style = MaterialTheme.typography.labelSmall)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(MutedControl.copy(alpha = 0.4f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(4.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(color)
                )
            }
        }
    }
}

@Composable
private fun RecipeSection(
    title: String,
    recipes: List<Recipe>,
    selectedIds: Set<String>,
    onViewRecipe: (Recipe) -> Unit,
    onToggleSelect: (String) -> Unit,
    onTryAnother: ((String) -> Unit)?,
    highlightFirst: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                title,
                color = PrimaryText,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall
            )
            if (highlightFirst) {
                androidx.compose.material3.Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = AccentBlue.copy(alpha = 0.18f)
                ) {
                    Text(
                        text = "AI Pick",
                        color = AccentBlue,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 3.dp)
                    )
                }
            }
        }
        recipes.forEach { recipe ->
            RecipeCard(
                recipe = recipe,
                isSelected = recipe.id in selectedIds,
                onView = { onViewRecipe(recipe) },
                onToggleSelect = { onToggleSelect(recipe.id) },
                onTryAnother = onTryAnother?.let { { it(recipe.id) } }
            )
        }
    }
}

@Composable
private fun RecipeCard(
    recipe: Recipe,
    isSelected: Boolean,
    onView: () -> Unit,
    onToggleSelect: () -> Unit,
    onTryAnother: (() -> Unit)?
) {
    val bgColor by animateColorAsState(
        if (isSelected) CyanAccent.copy(alpha = 0.10f) else SecondaryCard.copy(alpha = 0.72f),
        label = "recipe_bg"
    )
    val borderColor by animateColorAsState(
        if (isSelected) CyanAccent.copy(alpha = 0.6f) else Color.Transparent,
        label = "recipe_border"
    )
    val pressScale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = pressScale.value; scaleY = pressScale.value }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        scope.launch { pressScale.animateTo(0.97f, tween(80)) }
                        tryAwaitRelease()
                        scope.launch { pressScale.animateTo(1f, spring(stiffness = Spring.StiffnessMediumLow)) }
                    }
                )
            }
            .clip(RoundedCornerShape(18.dp))
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .background(bgColor)
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onView,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(999.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text(
                    "View Recipe",
                    color = CyanAccent,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            FilterChip(
                selected = isSelected,
                onClick = onToggleSelect,
                label = {
                    Text(
                        if (isSelected) "Selected" else "Select",
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyanAccent,
                    selectedLabelColor = Color.Black
                )
            )
        }
        if (onTryAnother != null) {
            TextButton(
                onClick = onTryAnother,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 2.dp)
            ) {
                Text(
                    "Try Another →",
                    color = MutedText,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
private fun GroceryListDialog(
    title: String = "Grocery List",
    items: List<GroceryItem>,
    checkedKeys: Set<String>,
    onToggleItem: (String) -> Unit,
    onCopy: () -> Unit,
    onShare: () -> Unit,
    onDismiss: () -> Unit
) {
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
            Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                Text(
                    title,
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(12.dp))

                if (items.isEmpty()) {
                    Text(
                        "Select recipes to build a grocery list.",
                        color = MutedText,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.weight(1f))
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onCopy,
                            shape = RoundedCornerShape(999.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text("Copy", color = CyanAccent, style = MaterialTheme.typography.labelSmall)
                        }
                        OutlinedButton(
                            onClick = onShare,
                            shape = RoundedCornerShape(999.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text("Share", color = CyanAccent, style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val grouped = items.groupBy { it.category }
                        GroceryCategory.values().forEach { cat ->
                            val catItems = grouped[cat] ?: return@forEach
                            Column {
                                Text(
                                    cat.name.replace("_", "/")
                                        .lowercase()
                                        .replaceFirstChar { it.uppercase() },
                                    color = PrimaryText,
                                    fontWeight = FontWeight.SemiBold,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                catItems.forEach { item ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Checkbox(
                                            checked = item.key in checkedKeys,
                                            onCheckedChange = { onToggleItem(item.key) },
                                            colors = CheckboxDefaults.colors(checkedColor = CyanAccent)
                                        )
                                        Text(
                                            item.displayLine,
                                            modifier = Modifier.weight(1f),
                                            color = if (item.key in checkedKeys) MutedText else PrimaryText,
                                            style = MaterialTheme.typography.bodySmall,
                                            textDecoration = if (item.key in checkedKeys)
                                                TextDecoration.LineThrough else null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) {
                    Text("Close", color = MutedText)
                }
            }
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

                Text("Ingredients", color = PrimaryText, fontWeight = FontWeight.Bold)
                recipe.ingredients.forEach { ingredient ->
                    Text("• $ingredient", color = MutedText, style = MaterialTheme.typography.bodySmall)
                }

                Spacer(Modifier.height(4.dp))

                Text("Instructions", color = PrimaryText, fontWeight = FontWeight.Bold)
                recipe.instructions.forEachIndexed { index, step ->
                    Text(
                        "${index + 1}. $step",
                        color = PrimaryText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text("Log this meal", color = PrimaryText, fontWeight = FontWeight.Bold)

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

// ── Plan Review Dialog ──────────────────────────────────────────────────────

@Composable
private fun PlanReviewDialog(
    draft: DraftPlanUiState,
    calorieTarget: Int,
    proteinTarget: Double,
    onAccept: () -> Unit,
    onRegenerate: () -> Unit,
    onCancel: () -> Unit,
    onReplaceMeal: (date: String, mealType: String) -> Unit
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .padding(horizontal = 12.dp),
            shape = RoundedCornerShape(20.dp),
            color = MainCard
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Review Weekly Meal Plan",
                            color = PrimaryText, fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            draft.weekLabel,
                            color = MutedText, style = MaterialTheme.typography.labelSmall
                        )
                    }
                    TextButton(onClick = onCancel, contentPadding = PaddingValues(4.dp)) {
                        Text("✕", color = MutedText, style = MaterialTheme.typography.titleMedium)
                    }
                }
                Text(
                    "Tap any meal to swap it before accepting.",
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                // Scrollable day list
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    draft.days.forEach { day ->
                        DraftDayCard(
                            day = day,
                            calorieTarget = calorieTarget,
                            proteinTarget = proteinTarget,
                            onMealTap = { mealType -> onReplaceMeal(day.date, mealType) }
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                }
                Spacer(Modifier.height(10.dp))
                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onRegenerate,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(999.dp),
                        contentPadding = PaddingValues(vertical = 10.dp)
                    ) {
                        Text(
                            "Regenerate",
                            color = CyanAccent,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                        contentPadding = PaddingValues(vertical = 10.dp)
                    ) {
                        Text(
                            "Accept Plan",
                            color = Color.Black,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DraftDayCard(
    day: DayMealPlanUiState,
    calorieTarget: Int,
    proteinTarget: Double,
    onMealTap: (mealType: String) -> Unit
) {
    val dayCalories = day.meals.values.filterNotNull().sumOf { it.calories }
    val dayProtein = day.meals.values.filterNotNull().sumOf { it.proteinGrams }
    val dayCarbs = day.meals.values.filterNotNull().sumOf { it.carbGrams }
    val dayFat = day.meals.values.filterNotNull().sumOf { it.fatGrams }
    val dayFiber = day.meals.values.filterNotNull().sumOf { it.fiberGrams ?: 0.0 }
    val proteinColor = when {
        proteinTarget <= 0 -> MutedText
        dayProtein >= proteinTarget * 0.9 -> PositiveAccent
        dayProtein >= proteinTarget * 0.6 -> WarningAccent
        else -> Color(0xFFE57373)
    }
    androidx.compose.material3.Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = SecondaryCard
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${day.dayLabel}  ${day.dateNumber}",
                    color = CyanAccent, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    "$dayCalories kcal${if (calorieTarget > 0) " / $calorieTarget" else ""}",
                    color = if (calorieTarget > 0 && dayCalories >= (calorieTarget * 0.85).toInt()) PositiveAccent else MutedText,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Text(
                "P ${dayProtein.clean()}g · C ${dayCarbs.clean()}g · F ${dayFat.clean()}g · Fiber ${dayFiber.clean()}g",
                color = proteinColor,
                style = MaterialTheme.typography.labelSmall
            )
            listOf("Breakfast", "Lunch", "Dinner", "Snack").forEach { mealType ->
                val recipe = day.meals[mealType]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MainCard)
                        .pointerInput(Unit) { detectTapGestures { onMealTap(mealType) } }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(mealType, color = MutedText, style = MaterialTheme.typography.labelSmall)
                        Text(
                            recipe?.name ?: "—",
                            color = if (recipe != null) PrimaryText else MutedText,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = if (recipe != null) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                    if (recipe != null) {
                        Text(
                            "P${recipe.proteinGrams.clean()}g",
                            color = CyanAccent, style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

// ── Weekly Meal Calendar ────────────────────────────────────────────────────

@Composable
private fun WeeklyMealCalendarSection(
    calState: MealCalendarUiState,
    onPrevWeek: () -> Unit,
    onNextWeek: () -> Unit,
    onGenerate: () -> Unit,
    onRegenerate: () -> Unit,
    onMealTap: (date: String, mealType: String) -> Unit,
    onWeekGrocery: () -> Unit,
    onModeChange: (String) -> Unit = {}
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Header row: label + week nav
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "Meal Calendar",
                    color = PrimaryText, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    calState.weekLabel,
                    color = MutedText, style = MaterialTheme.typography.labelSmall
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = onPrevWeek, contentPadding = PaddingValues(4.dp)) {
                    Text("‹", color = CyanAccent, style = MaterialTheme.typography.titleMedium)
                }
                TextButton(onClick = onNextWeek, contentPadding = PaddingValues(4.dp)) {
                    Text("›", color = CyanAccent, style = MaterialTheme.typography.titleMedium)
                }
            }
        }

        // Meal Plan Mode toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Variety", "Repeat Weekly").forEach { mode ->
                FilterChip(
                    selected = calState.mealPlanMode == mode,
                    onClick = { onModeChange(mode) },
                    label = { Text(mode, style = MaterialTheme.typography.labelSmall, maxLines = 1) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CyanAccent,
                        selectedLabelColor = androidx.compose.ui.graphics.Color.Black,
                        labelColor = MutedText
                    )
                )
            }
        }
        if (calState.mealPlanMode == "Repeat Weekly") {
            Text(
                "Repeat Weekly is useful for meal prep and bulk grocery shopping.",
                color = MutedText,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (!calState.isGenerated) {
            // Empty state — prompt to generate
            androidx.compose.material3.Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = SecondaryCard
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "No plan for ${calState.weekLabel.lowercase()}.",
                        color = MutedText, style = MaterialTheme.typography.bodySmall
                    )
                    Button(
                        onClick = onGenerate,
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                    ) {
                        Text("Generate Plan", color = Color.Black, fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        } else {
            // Day cards
            calState.days.forEach { day ->
                CalendarDayCard(
                    day = day,
                    onMealTap = { mealType -> onMealTap(day.date, mealType) }
                )
            }

            // Action row: regenerate + grocery list
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onRegenerate,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(999.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Text("Regenerate Week", color = CyanAccent,
                        style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                }
                OutlinedButton(
                    onClick = onWeekGrocery,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(999.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Text("Week Groceries", color = CyanAccent,
                        style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun CalendarDayCard(
    day: DayMealPlanUiState,
    onMealTap: (mealType: String) -> Unit
) {
    androidx.compose.material3.Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = SecondaryCard
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                "${day.dayLabel}  ${day.dateNumber}",
                color = CyanAccent, fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium
            )
            listOf("Breakfast", "Lunch", "Dinner", "Snack").forEach { mealType ->
                val recipe = day.meals[mealType]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MainCard)
                        .pointerInput(Unit) { detectTapGestures { onMealTap(mealType) } }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            mealType,
                            color = MutedText, style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            recipe?.name ?: "—",
                            color = if (recipe != null) PrimaryText else MutedText,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = if (recipe != null) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                    if (recipe != null) {
                        Text(
                            "P${recipe.proteinGrams.clean()}g",
                            color = CyanAccent, style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarMealDetailDialog(
    slot: PlannedMealSlot,
    recipe: Recipe,
    replacementCandidates: List<Recipe>,
    onDismiss: () -> Unit,
    onLog: (mealName: String) -> Unit,
    onLoadAlternatives: () -> Unit,
    onReplaceWith: (Recipe) -> Unit,
    onClearAlternatives: () -> Unit
) {
    var selectedMeal by remember(recipe.id) { mutableStateOf(slot.mealType) }

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
                // Context badge
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    androidx.compose.material3.Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = AccentBlue.copy(alpha = 0.15f)
                    ) {
                        Text(
                            slot.mealType,
                            color = AccentBlue, style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    recipe.collection.forEach { col ->
                        val colColor = if ("Metabolic" in col) PositiveAccent else WarningAccent
                        androidx.compose.material3.Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = colColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                col,
                                color = colColor, style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Text(
                    recipe.name,
                    color = PrimaryText, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                if (recipe.description.isNotBlank()) {
                    Text(
                        recipe.description,
                        color = MutedText, style = MaterialTheme.typography.bodySmall
                    )
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

                val timeStr = buildString {
                    if (recipe.prepMinutes > 0) append("Prep: ${recipe.prepMinutes} min")
                    if (recipe.cookMinutes > 0) {
                        if (isNotEmpty()) append("  ·  ")
                        append("Cook: ${recipe.cookMinutes} min")
                    }
                }
                if (timeStr.isNotBlank()) {
                    Text(timeStr, color = MutedText, style = MaterialTheme.typography.bodySmall)
                }

                Spacer(Modifier.height(2.dp))

                Text("Ingredients", color = PrimaryText, fontWeight = FontWeight.Bold)
                recipe.ingredients.forEach { ingredient ->
                    Text("• $ingredient", color = MutedText, style = MaterialTheme.typography.bodySmall)
                }

                Text("Instructions", color = PrimaryText, fontWeight = FontWeight.Bold)
                recipe.instructions.forEachIndexed { i, step ->
                    Text("${i + 1}. $step", color = PrimaryText, style = MaterialTheme.typography.bodySmall)
                }

                recipe.storageNotes?.let {
                    Text("Storage: $it", color = MutedText, style = MaterialTheme.typography.bodySmall)
                }
                recipe.mealPrepNotes?.let {
                    Text("Meal Prep: $it", color = MutedText, style = MaterialTheme.typography.bodySmall)
                }

                Spacer(Modifier.height(4.dp))
                Text("Log this meal", color = PrimaryText, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").forEach { meal ->
                        FilterChip(
                            selected = selectedMeal == meal,
                            onClick = { selectedMeal = meal },
                            label = { Text(meal.take(5), style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CyanAccent, selectedLabelColor = Color.Black
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

                if (replacementCandidates.isNotEmpty()) {
                    Text(
                        "Choose a replacement",
                        color = PrimaryText, fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    replacementCandidates.forEach { alt ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SecondaryCard)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    alt.name,
                                    color = PrimaryText,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    "P${alt.proteinGrams.clean()}g · ${alt.calories} kcal",
                                    color = MutedText,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            TextButton(
                                onClick = { onReplaceWith(alt) },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    "Select",
                                    color = CyanAccent,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                    TextButton(onClick = onClearAlternatives, modifier = Modifier.fillMaxWidth()) {
                        Text("Cancel", color = MutedText, style = MaterialTheme.typography.labelSmall)
                    }
                } else {
                    OutlinedButton(
                        onClick = onLoadAlternatives,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text(
                            "Replace This Meal",
                            color = CyanAccent, fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                    Text("Close", color = MutedText)
                }
            }
        }
    }
}
