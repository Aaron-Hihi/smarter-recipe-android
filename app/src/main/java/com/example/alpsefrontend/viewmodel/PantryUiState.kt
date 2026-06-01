package com.example.alpsefrontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpsefrontend.data.model.PantryItem
import com.example.alpsefrontend.data.repository.PantryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class PantryUiState {
    object Loading : PantryUiState()
    data class Success(val items: List<PantryItem>) : PantryUiState()
    data class Error(val message: String) : PantryUiState()
}

class PantryViewModel(private val repository: PantryRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<PantryUiState>(PantryUiState.Loading)
    val uiState: StateFlow<PantryUiState> = _uiState

    init {
        loadPantry()
    }

    fun loadPantry() {
        viewModelScope.launch {
            _uiState.value = PantryUiState.Loading
            try {
                repository.getPantryItems().collect { items ->
                    _uiState.value = PantryUiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = PantryUiState.Error(e.message ?: "Failed to load pantry")
            }
        }
    }

    fun removeItem(itemId: Int) {
        viewModelScope.launch {
            val result = repository.removePantryItem(itemId)
            if (result.isSuccess) {
                loadPantry() // Refresh the list
            }
        }
    }
}