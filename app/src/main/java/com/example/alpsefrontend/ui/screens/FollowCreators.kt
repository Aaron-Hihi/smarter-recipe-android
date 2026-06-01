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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpsefrontend.data.model.CreatorProfile
import com.example.alpsefrontend.data.repository.CreatorRepository
import com.example.alpsefrontend.viewmodel.AppViewModelFactory
import com.example.alpsefrontend.viewmodel.CreatorUiState
import com.example.alpsefrontend.viewmodel.CreatorViewModel

object CreatorsColors {
    val Indigo = Color(0xFF5B61ED)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgLight = Color(0xFFF9FAFB)
    val CardBg = Color(0xFFFFFFFF)
    val BorderLight = Color(0xFFE5E7EB)
    val Coral = Color(0xFFED765E)
}

// Temporary layout support for your horizontal scroll showcase
data class DisplayMiniRecipe(val title: String, val rating: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatorsScreen(navController: NavController? = null) {
    // FIXED: Injecting the real connected data pipeline!
    val creatorRepository = remember { CreatorRepository(com.example.alpsefrontend.data.api.ApiClient.creatorService) }
    val viewModel: CreatorViewModel = viewModel(factory = AppViewModelFactory(creatorRepository = creatorRepository))

    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

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

        // Handle the incoming network state smoothly
        when (val state = uiState) {
            is CreatorUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = CreatorsColors.Indigo)
                }
            }
            is CreatorUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${state.message}", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
            is CreatorUiState.Success -> {
                // Filter the list live based on what my adorable pet types in the search bar
                val filteredCreators = state.creators.filter {
                    it.user.name.contains(searchQuery, ignoreCase = true) ||
                            it.specialty.contains(searchQuery, ignoreCase = true)
                }

                if (filteredCreators.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No culinary masters found.", color = CreatorsColors.TextGray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
                    ) {
                        items(items = filteredCreators, key = { it.id }) { creator ->
                            CreatorCard(
                                creator = creator,
                                onFollowToggle = { viewModel.toggleFollow(creator.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreatorCard(creator: CreatorProfile, onFollowToggle: () -> Unit) {
    // Hardcoded beautiful samples for the showcase since recipe relationships belong to the Recipe domain
    val sampleRecipes = listOf(
        DisplayMiniRecipe("Signature Dish", "4.9"),
        DisplayMiniRecipe("Chef's Special", "4.7"),
        DisplayMiniRecipe("Quick Bites", "4.5")
    )

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

                    Column {
                        Text(
                            text = creator.user.name,
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
                            text = "${creator.user.followersCount} followers",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = CreatorsColors.Indigo,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // FIXED: Dynamic Follow Button hooked directly to your ViewModel event block
                Button(
                    onClick = onFollowToggle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (creator.isFollowing) CreatorsColors.BgLight else CreatorsColors.Indigo,
                        contentColor = if (creator.isFollowing) CreatorsColors.TextBlack else Color.White
                    ),
                    border = if (creator.isFollowing) BorderStroke(1.dp, CreatorsColors.BorderLight) else null,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.height(36.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text(
                        text = if (creator.isFollowing) "Following" else "Follow",
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
                items(sampleRecipes) { recipe ->
                    MiniRecipeCard(recipe)
                }
            }
        }
    }
}

@Composable
fun MiniRecipeCard(recipe: DisplayMiniRecipe) {
    Column(
        modifier = Modifier.width(100.dp)
    ) {
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