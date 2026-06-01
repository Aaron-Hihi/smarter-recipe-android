package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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

object EditProfileColors {
    val Indigo = Color(0xFF5B61ED)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgWhite = Color(0xFFFFFFFF)
    val BorderLight = Color(0xFFE5E7EB)
    val LightGray = Color(0xFFF3F4F6)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController? = null) {
    // State populated with your current dummy data
    var name by remember { mutableStateOf("Gregory Edgard Christian") }
    var username by remember { mutableStateOf("gregory_chef") }
    var bio by remember { mutableStateOf("Information Systems student turning caffeine into code and raw ingredients into culinary masterpieces. \uD83C\uDF73\uD83D\uDC68\u200D\uD83D\uDCBB\n\uD83D\uDCCD Surabaya, Indonesia") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EditProfileColors.BgWhite)
    ) {
        // --- Custom Top App Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Go Back", tint = EditProfileColors.TextBlack)
            }
            
            Text(
                text = "Edit Profile",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EditProfileColors.TextBlack
            )
            
            TextButton(
                onClick = { 
                    // TODO (BACKEND): Trigger API to update profile data
                    navController?.popBackStack() 
                }
            ) {
                Text("Save", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EditProfileColors.Indigo)
            }
        }

        // --- Scrollable Form Area ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Avatar Editing Section
            Box(
                modifier = Modifier.size(100.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                // The Avatar
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(EditProfileColors.LightGray)
                        .border(2.dp, EditProfileColors.BorderLight, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("GEC", color = EditProfileColors.TextGray, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                }
                
                // The Edit Icon Badge
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(EditProfileColors.Indigo)
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Change Photo",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))

            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", color = EditProfileColors.TextGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EditProfileColors.Indigo,
                    unfocusedBorderColor = EditProfileColors.BorderLight,
                    focusedTextColor = EditProfileColors.TextBlack
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(20.dp))

            // Username Field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = EditProfileColors.TextGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EditProfileColors.Indigo,
                    unfocusedBorderColor = EditProfileColors.BorderLight,
                    focusedTextColor = EditProfileColors.TextBlack
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(20.dp))

            // Bio Field (Multi-line)
            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Bio", color = EditProfileColors.TextGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp), // Make it taller for multi-line
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EditProfileColors.Indigo,
                    unfocusedBorderColor = EditProfileColors.BorderLight,
                    focusedTextColor = EditProfileColors.TextBlack
                ),
                maxLines = 5
            )
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    MaterialTheme {
        EditProfileScreen()
    }
}