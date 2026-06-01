package com.example.alpsefrontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpsefrontend.data.model.Recipe
import com.example.alpsefrontend.data.repository.SavedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SavedViewModel(private val repository: SavedRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val uiState: StateFlow<RecipeUiState> = _uiState

    init {
        loadSavedRecipes()
    }

    private fun loadSavedRecipes() {
        viewModelScope.launch {
            _uiState.value = RecipeUiState.Loading
            try {
                repository.getSavedRecipes().collect { savedList ->
                    _uiState.value = RecipeUiState.Success(savedList)
                }
            } catch (e: Exception) {
                _uiState.value = RecipeUiState.Error(e.message ?: "Failed to load saved recipes")
            }
        }
    }

    fun removeRecipe(recipeId: Int) {
        viewModelScope.launch {
            val result = repository.removeSavedRecipe(recipeId)
            if (result.isSuccess) {
                // Reload the list from the repository to update the UI
                loadSavedRecipes()
            } else {
                // In a real app, you might trigger a Toast/Snackbar error state here
                println("Failed to remove: ${result.exceptionOrNull()?.message}")
            }
        }
    }
}