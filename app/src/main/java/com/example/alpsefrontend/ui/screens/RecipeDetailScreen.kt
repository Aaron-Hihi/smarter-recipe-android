package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpsefrontend.data.repository.RecipeRepository
import com.example.alpsefrontend.viewmodel.AppViewModelFactory
import com.example.alpsefrontend.viewmodel.RecipeUiState
import com.example.alpsefrontend.viewmodel.RecipeViewModel

// Reusing the colors
object RecipeColors {
    val Coral = Color(0xFFFF745C)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BadgeBg = Color(0xFFEEF2FF)
    val BadgeText = Color(0xFF4F46E5)
    val BorderLight = Color(0xFFE5E7EB)
}

@Composable
fun RecipeDetailScreen(navController: NavController? = null, recipeId: Int = 0) {
    // 1. Inject the ViewModel
    val recipeRepository = remember { RecipeRepository(com.example.alpsefrontend.data.api.ApiClient.recipeService) }
    val viewModel: RecipeViewModel = viewModel(factory = AppViewModelFactory(recipeRepository = recipeRepository))

    // 2. Observe the state
    val uiState by viewModel.selectedRecipeState.collectAsState()

    // 3. Trigger the fetch ONLY when the screen opens or recipeId changes
    LaunchedEffect(recipeId) {
        viewModel.loadRecipeDetail(recipeId)
    }

    var showReviewDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

        when (val state = uiState) {
            is RecipeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = RecipeColors.Coral)
                }
            }
            is RecipeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.message}", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
            is RecipeUiState.Success -> {
                // We wrapped the single recipe in a list, so we take the first item
                val recipe = state.recipes.firstOrNull()

                if (recipe == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Recipe not found.", color = RecipeColors.TextGray)
                    }
                } else {
                    // --- THE ACTUAL UI WITH REAL DATA ---
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp)
                    ) {
                        // Top Bar
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                            IconButton(onClick = { navController?.popBackStack() }, modifier = Modifier.size(32.dp)) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back", tint = RecipeColors.TextBlack)
                            }
                        }

                        // Image Box
                        Box(modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(16.dp)).background(Color.DarkGray), contentAlignment = Alignment.Center) {
                            Text("Photo: ${recipe.title}", color = Color.White, fontWeight = FontWeight.Medium)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Real Title
                        Text(text = recipe.title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = RecipeColors.TextBlack, letterSpacing = (-0.5).sp)

                        Spacer(modifier = Modifier.height(12.dp))

                        // Real Metadata
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text("Serves ${recipe.servings}", fontSize = 15.sp, fontWeight = FontWeight.Medium, color = RecipeColors.TextBlack)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("•", fontSize = 15.sp, color = RecipeColors.TextBlack)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(recipe.cookTime, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = RecipeColors.TextBlack)

                            Spacer(modifier = Modifier.width(12.dp))
                            Box(modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(RecipeColors.BadgeBg).padding(horizontal = 10.dp, vertical = 4.dp)) {
                                Text("Published", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = RecipeColors.BadgeText)
                            }

                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.Star, contentDescription = "Rating", modifier = Modifier.size(18.dp), tint = RecipeColors.TextBlack)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(recipe.averageRating.toString(), fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RecipeColors.TextBlack)
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        Text("Ingredients", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RecipeColors.TextBlack)
                        Spacer(modifier = Modifier.height(12.dp))

                        // TODO: Map real recipe.ingredients here once you add them to the dummy data
                        IngredientRow(name = "Sample Ingredient", quantity = "1 cup", status = "available")

                        Spacer(modifier = Modifier.height(36.dp))

                        // Buttons
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedActionButton("Save", Icons.Default.Add, Modifier.weight(1f)) { /* TODO */ }
                            OutlinedActionButton("Review", Icons.Default.Edit, Modifier.weight(1f)) { showReviewDialog = true }
                            FilledDangerousActionButton("Delete", Modifier.weight(1.2f)) { navController?.popBackStack() }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }

        if (showReviewDialog) {
            ReviewDialog(onDismiss = { showReviewDialog = false }, onSubmit = { rating, text -> showReviewDialog = false })
        }
    }
}

// Keep your existing IngredientRow, OutlinedActionButton, and FilledDangerousActionButton below this...
@Composable
fun IngredientRow(name: String, quantity: String, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(text = name, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = RecipeColors.TextBlack)
            Text(text = " — $quantity", fontSize = 15.sp, fontWeight = FontWeight.Normal, color = RecipeColors.TextBlack)
        }
        Text(text = status, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = if (status == "missing") RecipeColors.Coral else RecipeColors.TextGray)
    }
}

@Composable
fun OutlinedActionButton(text: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = RecipeColors.TextBlack),
        border = BorderStroke(1.dp, RecipeColors.BorderLight),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun FilledDangerousActionButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = RecipeColors.Coral, contentColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
    }
}