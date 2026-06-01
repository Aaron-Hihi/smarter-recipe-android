package com.example.alpsefrontend.data.api

import com.example.alpsefrontend.data.model.Recipe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// DTOs to catch Laravel's JSON responses safely
data class RecipeListResponse(
    val data: List<Recipe>
)

data class SingleRecipeResponse(
    val data: Recipe
)
data class UploadRecipeRequest(
    val title: String,
    val ingredients: List<String>
)

// Inside your interface RecipeApiService, add this endpoint:

interface RecipeApiService {
    
    // GET all recipes for the Discover Screen
    @GET("recipes")
    suspend fun getAllRecipes(): RecipeListResponse

    // GET a single recipe by its ID for the RecipeDetailScreen
    @GET("recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: Int): SingleRecipeResponse

    @POST("recipes")
    suspend fun uploadRecipe(@Body request: UploadRecipeRequest): SingleRecipeResponse
}
