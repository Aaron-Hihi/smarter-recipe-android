package com.example.alpsefrontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpsefrontend.data.model.CreatorProfile
import com.example.alpsefrontend.data.repository.CreatorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CreatorUiState {
    object Loading : CreatorUiState()
    data class Success(val creators: List<CreatorProfile>) : CreatorUiState()
    data class Error(val message: String) : CreatorUiState()
}

class CreatorViewModel(private val repository: CreatorRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CreatorUiState>(CreatorUiState.Loading)
    val uiState: StateFlow<CreatorUiState> = _uiState

    init {
        loadCreators()
    }

    private fun loadCreators() {
        viewModelScope.launch {
            _uiState.value = CreatorUiState.Loading
            try {
                repository.getCreators().collect { list ->
                    _uiState.value = CreatorUiState.Success(list)
                }
            } catch (e: Exception) {
                _uiState.value = CreatorUiState.Error(e.message ?: "Failed to load creators")
            }
        }
    }

    fun toggleFollow(creatorId: Int) {
        viewModelScope.launch {
            val result = repository.toggleFollow(creatorId)
            if (result.isSuccess) {
                loadCreators() // Refresh the list so the UI updates the follower count instantly!
            }
        }
    }
}