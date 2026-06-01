package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
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

object HomeColors {
    // Exact colors from your Figma designs
    val Coral = Color(0xFFED765E)
    val CoralLight = Color(0xFFF29B88) // For the plus button background
    val Indigo = Color(0xFF5B61ED)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgLight = Color(0xFFF9FAFB)
    val CardBg = Color(0xFFFFFFFF)
    val BorderLight = Color(0xFFE5E7EB)
    val DeleteIcon = Color(0xFFF08A76)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController? = null) {
    // State for the pantry input field
    var pantryInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeColors.BgLight)
    ) {
        // Top App Bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "My Kitchen",
                    fontWeight = FontWeight.Bold,
                    color = HomeColors.TextBlack
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = HomeColors.BgLight
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Welcome back, Chef!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = HomeColors.TextBlack
                )
                Text(
                    text = "What are we cooking today?",
                    fontSize = 15.sp,
                    color = HomeColors.TextGray
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // --- UPLOAD RECIPE SECTION ---
            item {
                Card(
                    onClick = {
                        // The master has wired this up for you!
                        navController?.navigate("upload")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    colors = CardDefaults.cardColors(containerColor = HomeColors.Coral),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
                            Text(
                                text = "Create New Recipe",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Share your culinary masterpiece with the world.",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                lineHeight = 20.sp
                            )
                        }

                        // The soft lighter coral box for the '+' icon
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(HomeColors.CoralLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(36.dp))
            }

            // --- MY PANTRY SECTION ---
            item {
                Text(
                    text = "My Pantry",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = HomeColors.TextBlack
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Add or remove items you currently own.",
                    fontSize = 14.sp,
                    color = HomeColors.TextGray
                )
                Spacer(modifier = Modifier.height(16.dp))

                // The Input Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = pantryInput,
                        onValueChange = { pantryInput = it },
                        placeholder = { Text("Add pantry item (e.g., Olive Oil)", color = HomeColors.TextGray) },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HomeColors.Indigo,
                            unfocusedBorderColor = HomeColors.BorderLight,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            // TODO (BACKEND): Trigger API to add item, then clear input
                            pantryInput = ""
                        },
                        modifier = Modifier
                            .height(56.dp)
                            .width(80.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HomeColors.Indigo),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Add", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // TODO (BACKEND): Loop through user's pantry items from API instead of hardcoding
            item { PantryItemCard("Olive oil — 500ml") }
            item { PantryItemCard("All-purpose flour — 1kg") }
            item { PantryItemCard("Canned tomatoes — 2 cans") }
            item { PantryItemCard("Dried basil — 50g") }
        }
    }
}

@Composable
fun PantryItemCard(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, HomeColors.BorderLight),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = HomeColors.TextBlack
            )

            // Delete IconButton with the soft red tint
            IconButton(
                onClick = { /* TODO (BACKEND): API Call to delete item */ },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = HomeColors.DeleteIcon,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}