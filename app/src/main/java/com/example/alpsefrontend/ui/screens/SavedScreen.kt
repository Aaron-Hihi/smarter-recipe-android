package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

object SavedColors {
    val Coral = Color(0xFFFF745C)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgLight = Color(0xFFF9FAFB)
    val CardBg = Color(0xFFFFFFFF)
    val BorderLight = Color(0xFFE5E7EB)
}

data class DummySavedRecipe(
    val id: Int,
    val title: String,
    val author: String,
    val rating: String,
    val time: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(navController: NavController? = null) {
    var searchQuery by remember { mutableStateOf("") }

    val savedRecipes = remember {
        mutableStateListOf(
            DummySavedRecipe(1, "Smoky Chipotle Tacos", "Chef Gordon", "4.6", "30 min"),
            DummySavedRecipe(2, "Creamy Mushroom Risotto", "Chef Luigi", "4.7", "40 min"),
            DummySavedRecipe(3, "Crispy Falafel Wraps", "Chef Anna", "4.5", "25 min")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SavedColors.BgLight)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Saved Recipes",
                    fontWeight = FontWeight.Bold,
                    color = SavedColors.TextBlack
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = SavedColors.BgLight
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search saved recipes...", color = SavedColors.TextGray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = SavedColors.TextGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SavedColors.Coral,
                    unfocusedBorderColor = SavedColors.BorderLight,
                    focusedContainerColor = SavedColors.CardBg,
                    unfocusedContainerColor = SavedColors.CardBg
                ),
                singleLine = true
            )
        }

        if (savedRecipes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No saved recipes yet.\nGo discover some!",
                    color = SavedColors.TextGray,
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = savedRecipes.filter { it.title.contains(searchQuery, ignoreCase = true) },
                    key = { it.id }
                ) { recipe ->
                    SavedRecipeCard(
                        recipe = recipe,
                        onClick = {
                            // WIRED: This now properly navigates!
                            navController?.navigate("recipe_detail/${recipe.id}")
                        },
                        onRemove = {
                            savedRecipes.remove(recipe)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SavedRecipeCard(
    recipe: DummySavedRecipe,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = SavedColors.CardBg),
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
                    .size(96.dp)
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
                Text(
                    text = recipe.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SavedColors.TextBlack,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "By ${recipe.author}",
                    fontSize = 13.sp,
                    color = SavedColors.TextGray
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = SavedColors.Coral,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = recipe.rating,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SavedColors.TextBlack
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "•  ${recipe.time}",
                        fontSize = 13.sp,
                        color = SavedColors.TextGray
                    )
                }
            }

            IconButton(
                onClick = onRemove,
                modifier = Modifier.align(Alignment.Top)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from Saved",
                    tint = SavedColors.TextGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedScreenPreview() {
    MaterialTheme {
        SavedScreen()
    }
}