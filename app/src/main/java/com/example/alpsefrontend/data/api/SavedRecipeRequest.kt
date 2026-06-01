package com.example.alpsefrontend.data.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// DTOs for Saved Recipes
data class SavedRecipeRequest(val recipeId: Int)
data class SavedRecipeResponse(val message: String)

interface SavedApiService {

    // READ: Get all recipes the user has bookmarked
    // (Note: We reuse RecipeListResponse from RecipeApiService to avoid duplicating models)
    @GET("saved-recipes")
    suspend fun getSavedRecipes(): RecipeListResponse

    // CREATE: Bookmark a new recipe
    @POST("saved-recipes")
    suspend fun saveRecipe(@Body request: SavedRecipeRequest): SavedRecipeResponse

    // DELETE: Remove a bookmark
    @DELETE("saved-recipes/{id}")
    suspend fun removeSavedRecipe(@Path("id") recipeId: Int): SavedRecipeResponse
}