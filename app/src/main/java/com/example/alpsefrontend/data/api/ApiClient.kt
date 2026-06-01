package com.example.alpsefrontend.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // --- THE MISSING WIRE ---
    // This is exactly what AuthScreen was looking for!
    val authService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
    // The engine for Recipes
    val recipeService: RecipeApiService by lazy {
        retrofit.create(RecipeApiService::class.java)
    }

    // The engine for the Smart Pantry
    val pantryService: PantryApiService by lazy {
        retrofit.create(PantryApiService::class.java)
    }
    // --- ADD THESE TO THE BOTTOM OF ApiClient OBJECT ---

    // The engine for Saved Recipes
    val savedService: SavedApiService by lazy {
        retrofit.create(SavedApiService::class.java)
    }

    // The engine for Following Creators
    val creatorService: CreatorApiService by lazy {
        retrofit.create(CreatorApiService::class.java)
    }

    // The engine for Profile & Dietary Updates
    val profileService: ProfileApiService by lazy {
        retrofit.create(ProfileApiService::class.java)
    }
}