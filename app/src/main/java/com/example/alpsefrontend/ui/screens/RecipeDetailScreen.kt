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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
// FIXED: Using the shiny new AutoMirrored version!
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.navigation.NavController

object RecipeColors {
    val Coral = Color(0xFFFF745C)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BadgeBg = Color(0xFFEEF2FF)
    val BadgeText = Color(0xFF4F46E5)
    val BorderLight = Color(0xFFE5E7EB)
}

@Composable
fun RecipeDetailScreen(navController: NavController? = null) {
    // State to control the visibility of the Review Dialog
    var showReviewDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            // --- TOP BAR WITH BACK BUTTON ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                IconButton(
                    onClick = { navController?.popBackStack() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        // FIXED: Replaced the deprecated icon
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back",
                        tint = RecipeColors.TextBlack
                    )
                }
            }

            // Image Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Taco Photo Here",
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Title
            Text(
                text = "Smoky Chipotle Tacos",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = RecipeColors.TextBlack,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Metadata Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Serves 4",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = RecipeColors.TextBlack
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "•", fontSize = 15.sp, color = RecipeColors.TextBlack)
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "30 min",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = RecipeColors.TextBlack
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Published Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(RecipeColors.BadgeBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Published",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = RecipeColors.BadgeText
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Rating
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(18.dp),
                    tint = RecipeColors.TextBlack
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "4.6",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = RecipeColors.TextBlack
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Ingredients Header
            Text(
                text = "Ingredients",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = RecipeColors.TextBlack
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Ingredients List
            IngredientRow(name = "Corn tortillas", quantity = "8", status = "available")
            IngredientRow(name = "Chipotle paste", quantity = "2 tbsp", status = "missing")
            IngredientRow(name = "Lime", quantity = "2", status = "available")

            Spacer(modifier = Modifier.height(16.dp))

            // Substitution Suggestions Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, RecipeColors.BorderLight, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Substitution suggestions",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = RecipeColors.TextBlack
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "For chipotle paste: use 1 tbsp smoked paprika + 1 tbsp tomato paste + 1 tsp cayenne.",
                        fontSize = 14.sp,
                        color = RecipeColors.TextGray,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Steps Header
            Text(
                text = "Steps",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = RecipeColors.TextBlack
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Steps Paragraphs
            Text(
                text = "Marinate protein with chipotle mixture for 15 minutes.\n\nHeat tortillas on a hot pan until charred at edges.\n\nAssemble tacos, garnish with lime and cilantro.",
                fontSize = 15.sp,
                color = RecipeColors.TextBlack,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedActionButton(
                    text = "Save",
                    icon = Icons.Default.Add,
                    modifier = Modifier.weight(1f),
                    onClick = { /* TODO: Trigger API Save Bookmark */ }
                )
                OutlinedActionButton(
                    text = "Review",
                    icon = Icons.Default.Edit,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        showReviewDialog = true
                    }
                )
                FilledDangerousActionButton(
                    text = "Delete Recipe",
                    modifier = Modifier.weight(1.2f),
                    onClick = {
                        // TODO: Trigger Delete API
                        navController?.popBackStack()
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // --- THE REVIEW DIALOG POPUP ---
        if (showReviewDialog) {
            ReviewDialog(
                onDismiss = { showReviewDialog = false },
                onSubmit = { rating, text ->
                    // FIXED: We are now actually "using" the variables so the compiler stops crying!
                    println("Review Submitted: $rating stars. Comment: $text")
                    showReviewDialog = false
                }
            )
        }
    }
}

@Composable
fun IngredientRow(name: String, quantity: String, status: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(
                text = name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = RecipeColors.TextBlack
            )
            Text(
                text = " — $quantity",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = RecipeColors.TextBlack
            )
        }
        Text(
            text = status,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (status == "missing") RecipeColors.Coral else RecipeColors.TextGray
        )
    }
}

@Composable
fun OutlinedActionButton(text: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = RecipeColors.TextBlack
        ),
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
        colors = ButtonDefaults.buttonColors(
            containerColor = RecipeColors.Coral,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun RecipeDetailScreenPreview() {
    MaterialTheme {
        RecipeDetailScreen()
    }
}