package com.example.alpsefrontend.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

object AuthColors {
    val Indigo = Color(0xFF5B61ED)
    val Coral = Color(0xFFED765E)
    val TextGray = Color(0xFF6B7280)
    val TextBlack = Color(0xFF111827)
    val BgWhite = Color(0xFFFFFFFF)
    val BorderLight = Color(0xFFE5E7EB)
}

// ---------------------------------------------------------------------------
// LOGIN SCREEN
// ---------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController? = null) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthColors.BgWhite)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // App Logo / Title
        Text(
            text = "Smarter Recipe",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AuthColors.Indigo
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Welcome back to your kitchen",
            fontSize = 15.sp,
            color = AuthColors.TextGray
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AuthColors.Indigo,
                unfocusedBorderColor = AuthColors.BorderLight
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                // FIXED: Using safe base icons!
                val image = if (passwordVisible) Icons.Default.Check else Icons.Default.Lock
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AuthColors.Indigo,
                unfocusedBorderColor = AuthColors.BorderLight
            ),
            singleLine = true
        )

        // Forgot Password Link
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(onClick = { /* TODO: Forgot Password Flow */ }) {
                Text("Forgot Password?", color = AuthColors.Indigo, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                // TODO (BACKEND): Validate credentials. On success:
                navController?.navigate("main_app") {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AuthColors.Indigo),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Divider
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = AuthColors.BorderLight)
            Text(" OR ", color = AuthColors.TextGray, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 8.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), color = AuthColors.BorderLight)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Dummy Google Login Button
        OutlinedButton(
            onClick = { /* TODO: Google Auth */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, AuthColors.BorderLight),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = AuthColors.TextBlack)
        ) {
            Text("Continue with Google", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Navigate to Sign Up
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an account?", color = AuthColors.TextGray)
            TextButton(onClick = { navController?.navigate("signup") }) {
                Text("Sign Up", color = AuthColors.Coral, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


// ---------------------------------------------------------------------------
// SIGN UP SCREEN
// ---------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController? = null) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthColors.BgWhite)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Create Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AuthColors.TextBlack,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Join our community of culinary creators.",
            fontSize = 15.sp,
            color = AuthColors.TextGray,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Name Input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AuthColors.Indigo,
                unfocusedBorderColor = AuthColors.BorderLight
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AuthColors.Indigo,
                unfocusedBorderColor = AuthColors.BorderLight
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                // FIXED: Using safe base icons!
                val image = if (passwordVisible) Icons.Default.Check else Icons.Default.Lock
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AuthColors.Indigo,
                unfocusedBorderColor = AuthColors.BorderLight
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Input
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AuthColors.Indigo,
                unfocusedBorderColor = AuthColors.BorderLight
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Sign Up Button
        Button(
            onClick = {
                // TODO (BACKEND): Register user. On success, navigate to Main App.
                navController?.navigate("main_app") {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AuthColors.Coral),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Create Account", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Navigate back to Login
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account?", color = AuthColors.TextGray)
            TextButton(onClick = { navController?.popBackStack() }) {
                Text("Log In", color = AuthColors.Indigo, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Previews
@Preview(showBackground = true)
@Composable
fun LoginPreview() { MaterialTheme { LoginScreen() } }

@Preview(showBackground = true)
@Composable
fun SignUpPreview() { MaterialTheme { SignUpScreen() } }