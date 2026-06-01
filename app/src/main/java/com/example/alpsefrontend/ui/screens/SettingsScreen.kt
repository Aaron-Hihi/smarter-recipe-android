package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpsefrontend.data.repository.ProfileRepository
import com.example.alpsefrontend.viewmodel.AppViewModelFactory
import com.example.alpsefrontend.viewmodel.ProfileViewModel

object SettingsColors {
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgWhite = Color(0xFFFFFFFF)
    val BorderLight = Color(0xFFE5E7EB)
    val DangerRed = Color(0xFFDC2626)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController? = null) {
    // FIXED: Formally injected our API client network layer into the screen
    val profileRepository = remember { ProfileRepository(com.example.alpsefrontend.data.api.ApiClient.profileService) }
    val viewModel: ProfileViewModel = viewModel(factory = AppViewModelFactory(profileRepository = profileRepository))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SettingsColors.BgWhite)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Go Back", tint = SettingsColors.TextBlack)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = SettingsColors.TextBlack
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsRowItem(icon = Icons.Default.Person, title = "Account Information")
        SettingsRowItem(icon = Icons.Default.Notifications, title = "Notifications")
        SettingsRowItem(icon = Icons.Default.Lock, title = "Privacy & Security")

        Spacer(modifier = Modifier.height(32.dp))

        // Log Out Button - NOW LIVE-WIRED TO LARAVEL VIA PROFILE VIEWMODEL!
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.logout {
                        navController?.navigate("login") {
                            popUpTo(0) // Completely purges the backstack for security
                        }
                    }
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null, tint = SettingsColors.DangerRed)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Log Out", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = SettingsColors.DangerRed)
        }
    }
}

@Composable
fun SettingsRowItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO */ }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = SettingsColors.TextGray)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = SettingsColors.TextBlack)
    }
    HorizontalDivider(color = SettingsColors.BorderLight, thickness = 1.dp, modifier = Modifier.padding(horizontal = 24.dp))
}