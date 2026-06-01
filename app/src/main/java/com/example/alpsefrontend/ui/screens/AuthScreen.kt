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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpsefrontend.data.repository.AuthRepository
import com.example.alpsefrontend.viewmodel.AuthState
import com.example.alpsefrontend.viewmodel.AuthViewModel
import com.example.alpsefrontend.viewmodel.AuthViewModelFactory

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
@Composable
fun LoginScreen(navController: NavController? = null) {
    // FIXED: Now passing the real live internet service to the repository!
    val authRepository = remember { AuthRepository(com.example.alpsefrontend.data.api.ApiClient.authService) }
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(AuthColors.BgWhite).padding(24.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Smarter Recipe", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = AuthColors.Indigo)
        Text("Welcome back to your kitchen", fontSize = 15.sp, color = AuthColors.TextGray)

        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, leadingIcon = { Icon(Icons.Default.Email, null) }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it }, label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            trailingIcon = { IconButton(onClick = { passwordVisible = !passwordVisible }) { Icon(if (passwordVisible) Icons.Default.Check else Icons.Default.Lock, null) } },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Observe State
        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> {
                LaunchedEffect(Unit) {
                    navController?.navigate("main_app") { popUpTo(0) }
                }
            }
            is AuthState.Error -> Text("Error: ${(authState as AuthState.Error).message}", color = Color.Red)
            else -> {}
        }

        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AuthColors.Indigo)
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an account?", color = AuthColors.TextGray)
            TextButton(onClick = { navController?.navigate("signup") }) { Text("Sign Up", color = AuthColors.Coral, fontWeight = FontWeight.Bold) }
        }
    }
}

// ---------------------------------------------------------------------------
// SIGN UP SCREEN
// ---------------------------------------------------------------------------
@Composable
fun SignUpScreen(navController: NavController? = null) {
    // FIXED: Connected to the live API client architecture here too!
    val authRepository = remember { AuthRepository(com.example.alpsefrontend.data.api.ApiClient.authService) }
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))
    val authState by viewModel.authState.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(AuthColors.BgWhite).padding(24.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create Account", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = AuthColors.TextBlack)
        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, leadingIcon = { Icon(Icons.Default.Person, null) }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, leadingIcon = { Icon(Icons.Default.Email, null) }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, leadingIcon = { Icon(Icons.Default.Lock, null) }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(32.dp))

        // Observe State
        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> {
                LaunchedEffect(Unit) {
                    navController?.navigate("main_app") { popUpTo(0) }
                }
            }
            is AuthState.Error -> Text("Error: ${(authState as AuthState.Error).message}", color = Color.Red)
            else -> {}
        }

        Button(
            onClick = { viewModel.register(name, email, password) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AuthColors.Coral)
        ) {
            Text("Create Account")
        }
    }
}