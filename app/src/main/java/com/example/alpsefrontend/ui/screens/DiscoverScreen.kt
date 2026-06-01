package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpsefrontend.data.repository.RecipeRepository
import com.example.alpsefrontend.viewmodel.AppViewModelFactory
import com.example.alpsefrontend.viewmodel.RecipeUiState
import com.example.alpsefrontend.viewmodel.RecipeViewModel

object DiscoverColors {
    val Coral = Color(0xFFFF745C)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgLight = Color(0xFFF9FAFB)
    val CardBg = Color(0xFFFFFFFF)
    val MatchGreen = Color(0xFF16A34A)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(navController: NavController? = null) {
    // 1. Inject the ViewModel
    val recipeRepository = remember { RecipeRepository(com.example.alpsefrontend.data.api.ApiClient.recipeService) }
    val viewModel: RecipeViewModel = viewModel(factory = AppViewModelFactory(recipeRepository = recipeRepository))

    // 2. Observe the state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DiscoverColors.BgLight)
    ) {
        // --- Search Bar Section ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search recipes, ingredients...", color = DiscoverColors.TextGray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = DiscoverColors.TextGray) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DiscoverColors.Coral,
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedContainerColor = DiscoverColors.BgLight,
                    unfocusedContainerColor = DiscoverColors.BgLight
                ),
                singleLine = true
            )
        }

        // --- Reactive List Section ---
        when (val state = uiState) {
            is RecipeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = DiscoverColors.Coral)
                }
            }
            is RecipeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${state.message}", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
            is RecipeUiState.Success -> {
                // Filter the list based on the search query
                val filteredRecipes = state.recipes.filter {
                    it.title.contains(searchQuery, ignoreCase = true)
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredRecipes, key = { it.id }) { recipe ->
                        RecipeCard(
                            title = recipe.title,
                            author = recipe.authorName,
                            rating = recipe.averageRating.toString(),
                            time = recipe.cookTime,
                            matchScore = recipe.matchScore ?: 0,
                            onClick = {
                                navController?.navigate("recipe_detail/${recipe.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

// ... Keep your existing RecipeCard composable exactly as it is below this ...
@Composable
fun RecipeCard(
    title: String,
    author: String,
    rating: String,
    time: String,
    matchScore: Int,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(130.dp),
        colors = CardDefaults.cardColors(containerColor = DiscoverColors.CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(12.dp)).background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Img", color = Color.White, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "$matchScore% Match", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = DiscoverColors.MatchGreen)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DiscoverColors.TextBlack, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "By $author", fontSize = 13.sp, color = DiscoverColors.TextGray)
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Rating", tint = DiscoverColors.Coral, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = rating, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = DiscoverColors.TextBlack)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "•  $time", fontSize = 13.sp, color = DiscoverColors.TextGray)
                }
            }
        }
    }
}