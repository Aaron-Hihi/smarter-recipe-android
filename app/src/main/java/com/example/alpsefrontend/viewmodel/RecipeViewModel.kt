package com.example.alpsefrontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpsefrontend.data.model.Recipe
import com.example.alpsefrontend.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RecipeUiState {
    object Loading : RecipeUiState()
    data class Success(val recipes: List<Recipe>) : RecipeUiState()
    data class Error(val message: String) : RecipeUiState()
}

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    // --- State for Lists (Discover / Home) ---
    private val _uiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val uiState: StateFlow<RecipeUiState> = _uiState

    // --- State for a Single Recipe Detail ---
    private val _selectedRecipeState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val selectedRecipeState: StateFlow<RecipeUiState> = _selectedRecipeState

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch {
            _uiState.value = RecipeUiState.Loading
            try {
                // FIXED: Changed getAllRecipes() to getRecipes() to match your Repository
                repository.getRecipes().collect { recipeList ->
                    _uiState.value = RecipeUiState.Success(recipeList)
                }
            } catch (e: Exception) {
                _uiState.value = RecipeUiState.Error(e.message ?: "Failed to load recipes")
            }
        }
    }

    // --- Function to load details into the selected state ---
    fun loadRecipeDetail(id: Int) {
        viewModelScope.launch {
            _selectedRecipeState.value = RecipeUiState.Loading

            // FIXED: Handled the modern Result type returning from getRecipeDetail(id)
            val result = repository.getRecipeDetail(id)

            result.onSuccess { recipe ->
                _selectedRecipeState.value = RecipeUiState.Success(listOf(recipe))
            }.onFailure { error ->
                _selectedRecipeState.value = RecipeUiState.Error(error.message ?: "Failed to load recipe details")
            }
        }
    }

    // --- FIXED: Added the missing Upload CRUD mechanism for your Creator Studio ---
    fun uploadNewRecipe(title: String, ingredients: List<String>, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = repository.publishRecipe(title, ingredients)
            result.onSuccess {
                onSuccess() // Triggers the screen transition back to safety
            }.onFailure { error ->
                _uiState.value = RecipeUiState.Error(error.message ?: "Failed to upload recipe")
            }
        }
    }
}