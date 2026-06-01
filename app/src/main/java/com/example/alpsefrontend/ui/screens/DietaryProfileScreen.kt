package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpsefrontend.data.model.DietaryPreferences
import com.example.alpsefrontend.data.repository.ProfileRepository
import com.example.alpsefrontend.viewmodel.AppViewModelFactory
import com.example.alpsefrontend.viewmodel.ProfileUiState
import com.example.alpsefrontend.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietaryProfileScreen(navController: NavController? = null) {
    // 1. Inject the ViewModel
    val profileRepository = remember { ProfileRepository(com.example.alpsefrontend.data.api.ApiClient.profileService) }
    val viewModel: ProfileViewModel = viewModel(factory = AppViewModelFactory(profileRepository = profileRepository))

    // 2. Observe State
    val uiState by viewModel.uiState.collectAsState()

    // Local state for the toggles before saving
    var isVegan by remember { mutableStateOf(false) }
    var isVegetarian by remember { mutableStateOf(false) }
    var isHalal by remember { mutableStateOf(false) }
    var isGlutenFree by remember { mutableStateOf(false) }
    var isNutAllergy by remember { mutableStateOf(false) }
    var isDairyFree by remember { mutableStateOf(false) }

    // Sync local state when the ViewModel successfully loads the user
    LaunchedEffect(uiState) {
        if (uiState is ProfileUiState.Success) {
            val prefs = (uiState as ProfileUiState.Success).user.dietaryPreferences
            if (prefs != null) {
                isVegan = prefs.isVegan
                isVegetarian = prefs.isVegetarian
                isHalal = prefs.isHalal
                isGlutenFree = prefs.isGlutenFree
                isNutAllergy = prefs.isNutAllergy
                isDairyFree = prefs.isDairyFree
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        CenterAlignedTopAppBar(
            title = { Text("Dietary Preferences", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFF9FAFB))
        )

        when (uiState) {
            is ProfileUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFF745C))
                }
            }
            is ProfileUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Failed to load preferences.", color = Color.Red)
                }
            }
            is ProfileUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Customize your recipe recommendations based on your dietary needs.",
                        color = Color(0xFF6B7280),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    DietaryToggleRow("Vegan", "No animal products", isVegan) { isVegan = it }
                    DietaryToggleRow("Vegetarian", "No meat", isVegetarian) { isVegetarian = it }
                    DietaryToggleRow("Halal", "Strictly Halal ingredients", isHalal) { isHalal = it }
                    DietaryToggleRow("Gluten-Free", "No wheat or gluten", isGlutenFree) { isGlutenFree = it }
                    DietaryToggleRow("Nut Allergy", "Strictly nut-free", isNutAllergy) { isNutAllergy = it }
                    DietaryToggleRow("Dairy-Free", "No milk or cheeses", isDairyFree) { isDairyFree = it }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = {
                            val newPrefs = DietaryPreferences(
                                isVegan = isVegan,
                                isVegetarian = isVegetarian,
                                isHalal = isHalal,
                                isGlutenFree = isGlutenFree,
                                isNutAllergy = isNutAllergy,
                                isDairyFree = isDairyFree
                            )
                            viewModel.updateDietaryPrefs(newPrefs)
                            navController?.popBackStack() // Go back to profile after saving
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF745C)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save Preferences", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun DietaryToggleRow(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF111827))
            Text(text = subtitle, fontSize = 13.sp, color = Color(0xFF6B7280))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFFFF745C))
        )
    }
}