package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

object CreatorsColors {
    val Indigo = Color(0xFF5B61ED)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgLight = Color(0xFFF9FAFB)
    val CardBg = Color(0xFFFFFFFF)
    val BorderLight = Color(0xFFE5E7EB)
    val Coral = Color(0xFFED765E)
}

// TODO (BACKEND): Dummy Data Models. Replace with your actual backend entities.
data class DummyMiniRecipe(val title: String, val rating: String)
data class DummyCreator(
    val id: Int, 
    val name: String, 
    val specialty: String, 
    val followers: String,
    val isFollowingInitially: Boolean,
    val topRecipes: List<DummyMiniRecipe>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatorsScreen(navController: NavController? = null) {
    var searchQuery by remember { mutableStateOf("") }

    // TODO (BACKEND): Fetch this list from your ViewModel/API
    val dummyCreators = listOf(
        DummyCreator(1, "Chef Gordon", "Master of Grills & Roasts", "1.2M", false, listOf(
            DummyMiniRecipe("Beef Wellington", "4.9"), DummyMiniRecipe("Smoky Ribs", "4.8"), DummyMiniRecipe("Garlic Mash", "4.7")
        )),
        DummyCreator(2, "Chef Jamie", "Quick & Healthy Meals", "850K", true, listOf(
            DummyMiniRecipe("Lentil Pie", "4.6"), DummyMiniRecipe("15-Min Pasta", "4.5"), DummyMiniRecipe("Green Salad", "4.8")
        )),
        DummyCreator(3, "Chef Anna", "Vegan Delights", "420K", false, listOf(
            DummyMiniRecipe("Falafel Wraps", "4.7"), DummyMiniRecipe("Quinoa Bowl", "4.9"), DummyMiniRecipe("Tofu Stir-fry", "4.5")
        ))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreatorsColors.BgLight)
    ) {
        // Top App Bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Discover Creators",
                    fontWeight = FontWeight.Bold,
                    color = CreatorsColors.TextBlack
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = CreatorsColors.BgLight
            )
        )

        // Search Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search chefs, bakers, cuisines...", color = CreatorsColors.TextGray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = CreatorsColors.TextGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CreatorsColors.Indigo,
                    unfocusedBorderColor = CreatorsColors.BorderLight,
                    focusedContainerColor = CreatorsColors.CardBg,
                    unfocusedContainerColor = CreatorsColors.CardBg
                ),
                singleLine = true
            )
        }

        // Creators List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
        ) {
            items(dummyCreators) { creator ->
                CreatorCard(creator)
            }
        }
    }
}

@Composable
fun CreatorCard(creator: DummyCreator) {
    // State to handle Follow/Unfollow interaction locally
    var isFollowing by remember { mutableStateOf(creator.isFollowingInitially) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = CreatorsColors.CardBg),
        border = BorderStroke(1.dp, CreatorsColors.BorderLight),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Avatar, Name, and Follow Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    // Profile Avatar Placeholder
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Img", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    // Name & Specialty
                    Column {
                        Text(
                            text = creator.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = CreatorsColors.TextBlack,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = creator.specialty,
                            fontSize = 13.sp,
                            color = CreatorsColors.TextGray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${creator.followers} followers",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = CreatorsColors.Indigo,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // Dynamic Follow Button
                Button(
                    onClick = { 
                        // TODO (BACKEND): Trigger Follow/Unfollow API here
                        isFollowing = !isFollowing 
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFollowing) CreatorsColors.BgLight else CreatorsColors.Indigo,
                        contentColor = if (isFollowing) CreatorsColors.TextBlack else Color.White
                    ),
                    border = if (isFollowing) BorderStroke(1.dp, CreatorsColors.BorderLight) else null,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.height(36.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text(
                        text = if (isFollowing) "Following" else "Follow",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Horizontal Scrollable Recipe Showcase
            Text(
                text = "Top Recipes",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = CreatorsColors.TextBlack,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(creator.topRecipes) { recipe ->
                    MiniRecipeCard(recipe)
                }
            }
        }
    }
}

@Composable
fun MiniRecipeCard(recipe: DummyMiniRecipe) {
    Column(
        modifier = Modifier.width(100.dp)
    ) {
        // Recipe Image Placeholder
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text("Food", color = Color.White, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(6.dp))
        
        Text(
            text = recipe.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = CreatorsColors.TextBlack,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, contentDescription = null, tint = CreatorsColors.Coral, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = recipe.rating, fontSize = 11.sp, color = CreatorsColors.TextGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatorsScreenPreview() {
    MaterialTheme {
        CreatorsScreen()
    }
}