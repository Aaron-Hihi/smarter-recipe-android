package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.example.alpsefrontend.data.model.PantryItem
import com.example.alpsefrontend.data.repository.PantryRepository
import com.example.alpsefrontend.viewmodel.AppViewModelFactory
import com.example.alpsefrontend.viewmodel.PantryUiState
import com.example.alpsefrontend.viewmodel.PantryViewModel

object HomeColors {
    val Indigo = Color(0xFF5B61ED)
    val Coral = Color(0xFFFF745C)
    val TextBlack = Color(0xFF111827)
    val TextGray = Color(0xFF6B7280)
    val BgLight = Color(0xFFF9FAFB)
    val CardBg = Color(0xFFFFFFFF)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController? = null) {
    // 1. Inject the ViewModel
    val pantryRepository = remember { PantryRepository(com.example.alpsefrontend.data.api.ApiClient.pantryService) }
    val viewModel: PantryViewModel = viewModel(factory = AppViewModelFactory(pantryRepository = pantryRepository))

    // 2. Observe State
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeColors.BgLight)
    ) {
        CenterAlignedTopAppBar(
            title = { Text("Smart Pantry", fontWeight = FontWeight.Bold, color = HomeColors.TextBlack) },
            actions = {
                IconButton(onClick = { /* TODO: Add new item dialog */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Item", tint = HomeColors.Indigo)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = HomeColors.BgLight)
        )

        when (val state = uiState) {
            is PantryUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = HomeColors.Coral)
                }
            }
            is PantryUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${state.message}", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
            is PantryUiState.Success -> {
                if (state.items.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Your pantry is empty.\nGo grocery shopping!", color = HomeColors.TextGray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(top = 8.dp, bottom = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items = state.items, key = { it.id }) { item ->
                            PantryItemCard(
                                item = item,
                                onRemove = { viewModel.removeItem(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PantryItemCard(item: PantryItem, onRemove: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = HomeColors.CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = HomeColors.TextBlack)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${item.quantity} • ${item.category}", fontSize = 13.sp, color = HomeColors.TextGray)
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = HomeColors.Coral)
            }
        }
    }
}