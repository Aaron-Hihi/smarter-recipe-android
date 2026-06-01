package com.example.alpsefrontend.data.repository

import com.example.alpsefrontend.data.api.ProfileApiService
import com.example.alpsefrontend.data.api.ProfileUpdateRequest
import com.example.alpsefrontend.data.model.DietaryPreferences
import com.example.alpsefrontend.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ProfileRepository(private val api: ProfileApiService) {

    fun getUserProfile(): Flow<User> = flow {
        val response = api.getProfile()
        emit(response.data)
    }

    suspend fun updateProfile(newName: String, newBio: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = api.updateProfile(ProfileUpdateRequest(newName, newBio))
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateDietaryPreferences(newPreferences: DietaryPreferences): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = api.updateDietaryPreferences(newPreferences)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Boolean> = withContext(Dispatchers.IO) {
        // TODO: Later on, wipe the local SharedPreferences token here!
        Result.success(true)
    }
}