package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

object DietaryColors {
    val Indigo = Color(0xFF5B61ED)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgWhite = Color(0xFFFFFFFF)
    val BorderLight = Color(0xFFE5E7EB)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietaryProfileScreen(navController: NavController? = null) {
    // Dummy States for toggles
    var isVegan by remember { mutableStateOf(false) }
    var isVegetarian by remember { mutableStateOf(false) }
    var isHalal by remember { mutableStateOf(true) }
    var isGlutenFree by remember { mutableStateOf(false) }
    var isNutAllergy by remember { mutableStateOf(false) }
    var isDairyFree by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DietaryColors.BgWhite)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Go Back", tint = DietaryColors.TextBlack)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Dietary Preferences",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DietaryColors.TextBlack
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tailor your experience. We will highlight recipes that match your dietary needs and hide those containing your allergens.",
                fontSize = 14.sp,
                color = DietaryColors.TextGray,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text("Diets", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DietaryColors.Indigo)
            Spacer(modifier = Modifier.height(12.dp))
            DietaryToggleRow(title = "Vegan", checked = isVegan) { isVegan = it }
            DietaryToggleRow(title = "Vegetarian", checked = isVegetarian) { isVegetarian = it }
            DietaryToggleRow(title = "Halal", checked = isHalal) { isHalal = it }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Allergies & Intolerances", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DietaryColors.Indigo)
            Spacer(modifier = Modifier.height(12.dp))
            DietaryToggleRow(title = "Gluten-Free", checked = isGlutenFree) { isGlutenFree = it }
            DietaryToggleRow(title = "Nut Allergy", checked = isNutAllergy) { isNutAllergy = it }
            DietaryToggleRow(title = "Dairy-Free", checked = isDairyFree) { isDairyFree = it }

            Spacer(modifier = Modifier.height(40.dp))
            
            Button(
                onClick = { navController?.popBackStack() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DietaryColors.Indigo),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Preferences", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun DietaryToggleRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = DietaryColors.TextBlack)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedTrackColor = DietaryColors.Indigo)
        )
    }
    HorizontalDivider(color = DietaryColors.BorderLight, thickness = 1.dp)
}

@Preview(showBackground = true)
@Composable
fun DietaryPreview() { MaterialTheme { DietaryProfileScreen() } }