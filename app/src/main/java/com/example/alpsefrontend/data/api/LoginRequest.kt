package com.example.alpsefrontend.data.api

import com.example.alpsefrontend.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST

// ---------------------------------------------------------------------------
// REQUEST & RESPONSE DTOs (Data Transfer Objects)
// These map perfectly to the JSON body structure your Laravel API expects/returns.
// ---------------------------------------------------------------------------
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String, // The Bearer token Laravel drops for session tracking
    val user: User     // Your official User Domain Model
)

// ---------------------------------------------------------------------------
// THE API ROUTE INTERFACE
// ---------------------------------------------------------------------------
interface AuthApiService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): AuthResponse
}