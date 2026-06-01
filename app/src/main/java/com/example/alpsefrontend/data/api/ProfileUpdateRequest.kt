package com.example.alpsefrontend.data.api

import com.example.alpsefrontend.data.model.DietaryPreferences
import com.example.alpsefrontend.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

// DTOs for Profile Updates
data class ProfileUpdateRequest(val name: String, val bio: String)
data class UserProfileResponse(val data: User)

interface ProfileApiService {

    // READ: Get the current user's full profile
    @GET("profile")
    suspend fun getProfile(): UserProfileResponse

    // UPDATE: Change name and bio
    @PUT("profile")
    suspend fun updateProfile(@Body request: ProfileUpdateRequest): UserProfileResponse

    // UPDATE: Change dietary toggles (Vegan, Halal, etc.)
    @PUT("profile/dietary")
    suspend fun updateDietaryPreferences(@Body request: DietaryPreferences): UserProfileResponse
}