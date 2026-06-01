package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpsefrontend.data.repository.ProfileRepository
import com.example.alpsefrontend.viewmodel.AppViewModelFactory
import com.example.alpsefrontend.viewmodel.ProfileUiState
import com.example.alpsefrontend.viewmodel.ProfileViewModel

object ProfileColors {
    val Indigo = Color(0xFF5B61ED)
    val Coral = Color(0xFFFF745C)
    val TextBlack = Color(0xFF111827)
    val TextGray = Color(0xFF6B7280)
    val BgLight = Color(0xFFF9FAFB)
    val CardBg = Color(0xFFFFFFFF)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController? = null) {
    // 1. Inject the Master Brain
    val profileRepository = remember { ProfileRepository(com.example.alpsefrontend.data.api.ApiClient.profileService) }
    val viewModel: ProfileViewModel = viewModel(factory = AppViewModelFactory(profileRepository = profileRepository))

    // 2. Observe the State
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ProfileColors.BgLight)
    ) {
        CenterAlignedTopAppBar(
            title = { Text("Profile", fontWeight = FontWeight.Bold, color = ProfileColors.TextBlack) },
            actions = {
                IconButton(onClick = { /* TODO: Navigate to Settings */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = ProfileColors.TextBlack)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = ProfileColors.BgLight)
        )

        when (val state = uiState) {
            is ProfileUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = ProfileColors.Indigo)
                }
            }
            is ProfileUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${state.message}", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
            is ProfileUiState.Success -> {
                val user = state.user

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    // Profile Picture Placeholder
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Photo", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // User Info from State
                    Text(text = user.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = ProfileColors.TextBlack)
                    Text(text = "@${user.username}", fontSize = 16.sp, color = ProfileColors.TextGray)

                    Spacer(modifier = Modifier.height(24.dp))

                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileStat(count = user.followersCount.toString(), label = "Followers")
                        ProfileStat(count = user.followingCount.toString(), label = "Following")
                        ProfileStat(count = "42", label = "Recipes") // Dummy stat for now
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Bio
                    Text(
                        text = user.bio ?: "No bio available.",
                        fontSize = 15.sp,
                        color = ProfileColors.TextBlack,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Action Buttons
                    Button(
                        onClick = { /* TODO: Navigate to Edit Profile */ },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ProfileColors.Indigo),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Edit Profile", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Logout Button wired to ViewModel!
                    OutlinedButton(
                        onClick = {
                            viewModel.logout(onLogoutSuccess = {
                                navController?.navigate("login") {
                                    popUpTo(0) // Clear the backstack so they can't hit "back" to return to the app
                                }
                            })
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ProfileColors.Coral),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Log Out", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileStat(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ProfileColors.TextBlack)
        Text(text = label, fontSize = 13.sp, color = ProfileColors.TextGray)
    }
}