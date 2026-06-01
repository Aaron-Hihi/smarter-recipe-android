package com.example.alpsefrontend.data.repository

import com.example.alpsefrontend.data.api.SavedApiService
import com.example.alpsefrontend.data.api.SavedRecipeRequest
import com.example.alpsefrontend.data.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class SavedRepository(private val api: SavedApiService) {

    fun getSavedRecipes(): Flow<List<Recipe>> = flow {
        val response = api.getSavedRecipes()
        emit(response.data)
    }

    suspend fun saveRecipe(recipeId: Int): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            api.saveRecipe(SavedRecipeRequest(recipeId))
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removeSavedRecipe(recipeId: Int): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            api.removeSavedRecipe(recipeId)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}