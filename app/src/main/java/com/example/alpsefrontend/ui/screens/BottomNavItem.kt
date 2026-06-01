package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// ---------------------------------------------------------------------------
// THE MASTER NAVIGATOR (Decides between Auth and Main App)
// ---------------------------------------------------------------------------
@Composable
fun RootAppNavigation() {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") { OnboardingScreen(rootNavController) }
        composable("login") { LoginScreen(rootNavController) }
        composable("signup") { SignUpScreen(rootNavController) }
        composable("main_app") { MainAppScreen(rootNavController) }
    }
}

// ---------------------------------------------------------------------------
// THE MANSION (Contains the Bottom Navigation Bar)
// ---------------------------------------------------------------------------

sealed class BottomNavItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Discover : BottomNavItem("discover", Icons.Default.Search, "Discover")
    object Saved : BottomNavItem("saved", Icons.Default.FavoriteBorder, "Saved")
    object Creators : BottomNavItem("creators", Icons.Default.CheckCircle, "Creators")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}

@Composable
fun MainAppScreen(rootNavController: NavController? = null) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen(navController) }
            composable(BottomNavItem.Discover.route) { DiscoverScreen(navController) }
            composable(BottomNavItem.Saved.route) { SavedScreen(navController) }
            composable(BottomNavItem.Creators.route) { CreatorsScreen(navController) }
            composable(BottomNavItem.Profile.route) { ProfileScreen(navController) }

            composable("upload") { UploadRecipeScreen(navController) }

            // FIXED: We are finally passing the navController to the Detail Screen here!
            composable("recipe_detail/{id}") { RecipeDetailScreen(navController) }

            composable("edit_profile") { EditProfileScreen(navController) }
            composable("dietary_profile") { DietaryProfileScreen(navController) }
            composable("settings") { SettingsScreen(navController) }
            // Extract the ID from the route and pass it to the screen!
            composable("recipe_detail/{id}") { backStackEntry ->
                val recipeIdString = backStackEntry.arguments?.getString("id")
                val recipeId = recipeIdString?.toIntOrNull() ?: 0
                RecipeDetailScreen(navController, recipeId)
            }
        }
    }
}

@Composable
fun AppBottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Discover,
        BottomNavItem.Saved,
        BottomNavItem.Creators,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, fontSize = 10.sp) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF745C),
                    selectedTextColor = Color(0xFFFF745C),
                    indicatorColor = Color(0xFFFF745C).copy(alpha = 0.1f)
                )
            )
        }
    }
}