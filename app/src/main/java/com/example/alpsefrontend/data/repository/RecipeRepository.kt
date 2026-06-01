package com.example.alpsefrontend.data.repository

import com.example.alpsefrontend.data.api.RecipeApiService
import com.example.alpsefrontend.data.api.UploadRecipeRequest
import com.example.alpsefrontend.data.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RecipeRepository(private val api: RecipeApiService) {

    fun getRecipes(): Flow<List<Recipe>> = flow {
        // Fetch real data from the API and emit it to the ViewModel
        val response = api.getAllRecipes()
        emit(response.data)
    }

    suspend fun getRecipeDetail(id: Int): Result<Recipe> = withContext(Dispatchers.IO) {
        try {
            val response = api.getRecipeById(id)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun publishRecipe(title: String, ingredients: List<String>): Result<Boolean> = withContext(Dispatchers.IO) {
        return@withContext try {
            api.uploadRecipe(UploadRecipeRequest(title, ingredients))
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}