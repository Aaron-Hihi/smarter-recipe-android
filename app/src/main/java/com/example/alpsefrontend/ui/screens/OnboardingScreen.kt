package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import kotlinx.coroutines.launch

object OnboardingColors {
    val Indigo = Color(0xFF5B61ED)
    val Coral = Color(0xFFED765E)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgWhite = Color(0xFFFFFFFF)
    val IndicatorInactive = Color(0xFFE5E7EB)
}

data class OnboardingPageData(
    val title: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun OnboardingScreen(navController: NavController? = null) {
    val pages = listOf(
        OnboardingPageData(
            title = "Discover New Recipes",
            description = "Explore thousands of culinary masterpieces created by top chefs and food enthusiasts around the world.",
            icon = Icons.Default.Search
        ),
        OnboardingPageData(
            title = "Save Your Favorites",
            description = "Never lose a recipe again. Bookmark your favorite meals and organize your own digital cookbook.",
            icon = Icons.Default.Favorite
        ),
        OnboardingPageData(
            title = "Manage Your Pantry",
            description = "Keep track of your ingredients and find out exactly what you can cook today with what you have.",
            icon = Icons.Default.Check
        )
    )

    val pagerState = rememberPagerState { pages.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OnboardingColors.BgWhite)
    ) {
        // Skip Button (Top Right)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            TextButton(
                onClick = {
                    navController?.navigate("login") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            ) {
                Text("Skip", color = OnboardingColors.TextGray, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }

        // Horizontal Pager (The Carousel)
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { position ->
            OnboardingPageContent(page = pages[position])
        }

        // Bottom Section: Indicators & Navigation Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Page Indicators (The little dots)
            Row(
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { iteration ->
                    val isSelected = pagerState.currentPage == iteration
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) OnboardingColors.Indigo else OnboardingColors.IndicatorInactive)
                            .size(if (isSelected) 10.dp else 8.dp)
                    )
                }
            }

            // Next / Get Started Button
            Button(
                onClick = {
                    if (pagerState.currentPage < pages.lastIndex) {
                        // Animate to next page
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        // Finish onboarding, go to Login
                        navController?.navigate("login") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OnboardingColors.Indigo),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Next",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPageData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Placeholder (Simulating an illustration)
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(OnboardingColors.Indigo.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = page.icon,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = OnboardingColors.Indigo
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Text Content
        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = OnboardingColors.TextBlack,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = page.description,
            fontSize = 15.sp,
            color = OnboardingColors.TextGray,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    MaterialTheme {
        OnboardingScreen()
    }
}