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
import androidx.navigation.NavController

object DiscoverColors {
    val Coral = Color(0xFFFF745C)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgLight = Color(0xFFF9FAFB)
    val CardBg = Color(0xFFFFFFFF)
    val MatchGreen = Color(0xFF16A34A) // For the 95% Match text
}

// TODO (BACKEND): Updated dummy model to include Match Score
data class DummyRecipe(
    val id: Int,
    val title: String,
    val author: String,
    val rating: String,
    val time: String,
    val matchScore: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(navController: NavController? = null) {
    var searchQuery by remember { mutableStateOf("") }

    val dummyRecipes = listOf(
        DummyRecipe(1, "Smoky Chipotle Tacos", "Chef Gordon", "4.6", "30 min", 98),
        DummyRecipe(2, "Lentil Shepherd's Pie", "Chef Jamie", "4.8", "45 min", 85),
        DummyRecipe(3, "Crispy Falafel Wraps", "Chef Anna", "4.5", "25 min", 92),
        DummyRecipe(4, "Spicy Thai Basil Beef", "Chef Ken", "4.9", "20 min", 75),
        DummyRecipe(5, "Creamy Mushroom Risotto", "Chef Luigi", "4.7", "40 min", 60)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DiscoverColors.BgLight)
    ) {
        // Search Bar Section
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

        // List Section
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dummyRecipes) { recipe ->
                RecipeCard(
                    title = recipe.title,
                    author = recipe.author,
                    rating = recipe.rating,
                    time = recipe.time,
                    matchScore = recipe.matchScore,
                    onClick = {
                        // WIRED: This now properly navigates to the detail screen!
                        navController?.navigate("recipe_detail/${recipe.id}")
                    }
                )
            }
        }
    }
}

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
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        colors = CardDefaults.cardColors(containerColor = DiscoverColors.CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Img", color = Color.White, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                // Match Score text at the top
                Text(
                    text = "$matchScore% Match",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DiscoverColors.MatchGreen
                )
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DiscoverColors.TextBlack,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "By $author",
                    fontSize = 13.sp,
                    color = DiscoverColors.TextGray
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = DiscoverColors.Coral,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = rating,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DiscoverColors.TextBlack
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "•  $time",
                        fontSize = 13.sp,
                        color = DiscoverColors.TextGray
                    )
                }
            }
        }
    }
}