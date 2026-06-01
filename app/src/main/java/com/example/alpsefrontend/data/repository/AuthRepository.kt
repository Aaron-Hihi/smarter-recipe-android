package com.example.alpsefrontend.data.repository

import com.example.alpsefrontend.data.api.AuthApiService
import com.example.alpsefrontend.data.api.LoginRequest
import com.example.alpsefrontend.data.api.RegisterRequest
import com.example.alpsefrontend.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// FIXED: The API is no longer commented out! We demand a real service.
class AuthRepository(private val api: AuthApiService) {

    suspend fun login(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        return@withContext try {
            // 1. Fire the real network call to Laravel
            val response = api.login(LoginRequest(email, password))

            // TODO (BACKEND): You should save response.token to SharedPreferences/DataStore here later!

            // 2. Return the actual user data from the server
            Result.success(response.user)
        } catch (e: Exception) {
            // If Laravel returns a 401 Unauthorized or the server is down, we catch it here
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.register(RegisterRequest(name, email, password))
            Result.success(response.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}