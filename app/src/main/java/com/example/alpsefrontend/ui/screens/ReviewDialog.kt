package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

object ReviewColors {
    val Coral = Color(0xFFFF745C)
    val StarEmpty = Color(0xFFE5E7EB)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDialog(
    onDismiss: () -> Unit,
    onSubmit: (Int, String) -> Unit
) {
    var rating by remember { mutableStateOf(0) }
    var reviewText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Leave a Review",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ReviewColors.TextBlack
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "How was the recipe?",
                    fontSize = 14.sp,
                    color = ReviewColors.TextGray
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                // Interactive Stars
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in 1..5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star $i",
                            tint = if (i <= rating) ReviewColors.Coral else ReviewColors.StarEmpty,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { rating = i }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Review Text Area
                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    placeholder = { Text("Write your experience...", color = ReviewColors.TextGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ReviewColors.Coral,
                        unfocusedBorderColor = ReviewColors.StarEmpty
                    ),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = ReviewColors.TextGray, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { onSubmit(rating, reviewText) },
                        colors = ButtonDefaults.buttonColors(containerColor = ReviewColors.Coral),
                        shape = RoundedCornerShape(8.dp),
                        enabled = rating > 0 // Disable if no stars are selected!
                    ) {
                        Text("Submit", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// Dummy Wrapper for Previewing the Dialog safely
@Preview(showBackground = true)
@Composable
fun ReviewDialogPreview() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize().background(Color.Gray), contentAlignment = Alignment.Center) {
            ReviewDialog(onDismiss = {}, onSubmit = { _, _ -> })
        }
    }
}