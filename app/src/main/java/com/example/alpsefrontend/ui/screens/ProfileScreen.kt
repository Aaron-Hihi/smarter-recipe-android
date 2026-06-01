package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
// FIXED: Using safe base icons!
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

object ProfileColors {
    val Indigo = Color(0xFF5B61ED)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgWhite = Color(0xFFFFFFFF)
    val BorderLight = Color(0xFFE5E7EB)
    val LightGray = Color(0xFFF3F4F6)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController? = null) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Dummy posts for the Instagram-style grid
    val dummyPosts = (1..15).toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ProfileColors.BgWhite)
    ) {
        // Instagram-style Top Bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "@gregory_chef",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = ProfileColors.TextBlack
                )
            },
            actions = {
                // WIRED: Opens the Settings Screen
                IconButton(onClick = { navController?.navigate("settings") }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = ProfileColors.TextBlack)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = ProfileColors.BgWhite
            )
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize()
        ) {
            // HEADER SECTION
            item(span = { GridItemSpan(3) }) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Avatar and Stats Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Profile Avatar
                        Box(
                            modifier = Modifier
                                .size(86.dp)
                                .clip(CircleShape)
                                .background(ProfileColors.LightGray)
                                .border(2.dp, ProfileColors.BorderLight, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("GEC", color = ProfileColors.TextGray, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }

                        // Stats Counters
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.weight(1f).padding(start = 16.dp)
                        ) {
                            ProfileStat(number = "15", label = "Recipes")
                            ProfileStat(number = "1,204", label = "Followers")
                            ProfileStat(number = "150", label = "Following")
                        }
                    }

                    // Bio Section
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                        Text(
                            text = "Gregory Edgard Christian",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = ProfileColors.TextBlack
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Information Systems student turning caffeine into code and raw ingredients into culinary masterpieces. 🍳👨‍💻\n📍 Surabaya, Indonesia",
                            fontSize = 14.sp,
                            color = ProfileColors.TextBlack,
                            lineHeight = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action Buttons Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // WIRED: Opens the Edit Profile Screen
                        OutlinedButton(
                            onClick = { navController?.navigate("edit_profile") },
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ProfileColors.TextBlack),
                            border = BorderStroke(1.dp, ProfileColors.BorderLight)
                        ) {
                            Text("Edit Profile", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }

                        // WIRED: Opens the Dietary Profile Screen
                        Button(
                            onClick = { navController?.navigate("dietary_profile") },
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ProfileColors.Indigo)
                        ) {
                            Text("Dietary Profile", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Tab Row
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = ProfileColors.BgWhite,
                        contentColor = ProfileColors.TextBlack,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = ProfileColors.TextBlack
                            )
                        }
                    ) {
                        Tab(
                            selected = selectedTabIndex == 0,
                            onClick = { selectedTabIndex = 0 },
                            // FIXED ICON
                            icon = { Icon(Icons.Default.List, contentDescription = "Grid", modifier = Modifier.size(24.dp)) }
                        )
                        Tab(
                            selected = selectedTabIndex == 1,
                            onClick = { selectedTabIndex = 1 },
                            // FIXED ICON
                            icon = { Icon(Icons.Default.Favorite, contentDescription = "Saved", modifier = Modifier.size(26.dp)) }
                        )
                    }
                }
            }

            // GRID POSTS SECTION
            items(dummyPosts.size) { index ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .border(1.dp, ProfileColors.BgWhite)
                        .background(ProfileColors.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Recipe ${index + 1}",
                        fontSize = 12.sp,
                        color = ProfileColors.TextGray
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileStat(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = number,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = ProfileColors.TextBlack
        )
        Text(
            text = label,
            fontSize = 13.sp,
            color = ProfileColors.TextBlack
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}