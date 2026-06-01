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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpsefrontend.data.repository.RecipeRepository
import com.example.alpsefrontend.viewmodel.AppViewModelFactory
import com.example.alpsefrontend.viewmodel.RecipeViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

object UploadColors {
    val Indigo = Color(0xFF5B61ED)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgWhite = Color(0xFFFFFFFF)
    val BorderLight = Color(0xFFE5E7EB)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadRecipeScreen(navController: NavController? = null) {
    // 1. Inject the Master Brain
    val recipeRepository = remember { RecipeRepository(com.example.alpsefrontend.data.api.ApiClient.recipeService) }
    val viewModel: RecipeViewModel = viewModel(factory = AppViewModelFactory(recipeRepository = recipeRepository))

    // Input States
    var recipeTitle by remember { mutableStateOf("") }
    var ing1 by remember { mutableStateOf("") }
    var unit1 by remember { mutableStateOf("") }
    var ing2 by remember { mutableStateOf("") }
    var unit2 by remember { mutableStateOf("") }
    var ing3 by remember { mutableStateOf("") }
    var unit3 by remember { mutableStateOf("") }

    var skilletChecked by remember { mutableStateOf(false) }
    var blenderChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(UploadColors.BgWhite)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { navController?.popBackStack() }, modifier = Modifier.padding(end = 8.dp).size(32.dp)) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back", tint = UploadColors.TextBlack)
            }
            Text(text = "Creator Studio — Upload", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = UploadColors.TextBlack)
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Title Section
        OutlinedCardContainer {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)).background(Color.LightGray), contentAlignment = Alignment.Center) {
                    Text("Img", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = recipeTitle,
                    onValueChange = { recipeTitle = it },
                    placeholder = { Text("Recipe title", color = UploadColors.TextGray) },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Ingredients Section
        OutlinedCardContainer {
            Text("Ingredients", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = UploadColors.TextBlack)
            Spacer(modifier = Modifier.height(16.dp))
            IngredientInputRow(ing1, { ing1 = it }, "1 cup quinoa", unit1, { unit1 = it }, "grams")
            Spacer(modifier = Modifier.height(12.dp))
            IngredientInputRow(ing2, { ing2 = it }, "2 tbsp olive oil", unit2, { unit2 = it }, "tbsp")
            Spacer(modifier = Modifier.height(12.dp))
            IngredientInputRow(ing3, { ing3 = it }, "Salt to taste", unit3, { unit3 = it }, "pinch")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Dietary Tags
        OutlinedCardContainer {
            Text("Dietary tags (strict validation)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = UploadColors.TextBlack)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DietaryTag("Vegan")
                DietaryTag("Halal")
                DietaryTag("Gluten-Free")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Equipment Selection
        OutlinedCardContainer {
            Text("Required equipment", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = UploadColors.TextBlack)
            Spacer(modifier = Modifier.height(8.dp))
            EquipmentCheckbox("Skillet", skilletChecked) { skilletChecked = it }
            EquipmentCheckbox("Blender", blenderChecked) { blenderChecked = it }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // FIXED: The Publish button now dynamically creates data and pushes it to Laravel!
        Button(
            onClick = {
                if (recipeTitle.isNotBlank()) {
                    val formattedIngredients = listOfNotNull(
                        if (ing1.isNotBlank()) "$ing1 $unit1" else null,
                        if (ing2.isNotBlank()) "$ing2 $unit2" else null,
                        if (ing3.isNotBlank()) "$ing3 $unit3" else null
                    )
                    viewModel.uploadNewRecipe(recipeTitle, formattedIngredients) {
                        navController?.popBackStack() // Smooth exit on success
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = UploadColors.Indigo),
            shape = RoundedCornerShape(8.dp),
            enabled = recipeTitle.isNotBlank()
        ) {
            Text("Publish Recipe", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

// Reusable structural helpers from your file remain completely intact below...
@Composable fun OutlinedCardContainer(content: @Composable () -> Unit) { Box(modifier = Modifier.fillMaxWidth().border(1.dp, UploadColors.BorderLight, RoundedCornerShape(12.dp)).padding(16.dp)) { Column { content() } } }
@Composable fun DietaryTag(text: String) { Box(modifier = Modifier.border(1.dp, UploadColors.BorderLight, RoundedCornerShape(20.dp)).padding(horizontal = 16.dp, vertical = 8.dp)) { Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = UploadColors.TextBlack) } }
@Composable fun EquipmentCheckbox(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) { Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) { Checkbox(checked = checked, onCheckedChange = onCheckedChange, colors = CheckboxDefaults.colors(checkedColor = UploadColors.Indigo)) ; Text(text = label, fontSize = 15.sp, color = UploadColors.TextBlack, modifier = Modifier.padding(start = 4.dp)) } }
@Composable fun IngredientInputRow(nameValue: String, nameChange: (String) -> Unit, namePlaceholder: String, unitValue: String, unitChange: (String) -> Unit, unitPlaceholder: String) { Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedTextField(value = nameValue, onValueChange = nameChange, placeholder = { Text(namePlaceholder, color = UploadColors.TextGray) }, modifier = Modifier.weight(1f).height(52.dp), shape = RoundedCornerShape(8.dp), singleLine = true) ; OutlinedTextField(value = unitValue, onValueChange = unitChange, placeholder = { Text(unitPlaceholder, color = UploadColors.TextGray) }, modifier = Modifier.width(90.dp).height(52.dp), shape = RoundedCornerShape(8.dp), singleLine = true) } }