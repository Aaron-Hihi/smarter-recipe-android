package com.example.alpsefrontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpsefrontend.data.model.DietaryPreferences
import com.example.alpsefrontend.data.model.User
import com.example.alpsefrontend.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val user: User) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                repository.getUserProfile().collect { user ->
                    _uiState.value = ProfileUiState.Success(user)
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Failed to load profile")
            }
        }
    }
    // --- NEW: Update Name and Bio ---
    fun updateProfile(newName: String, newBio: String) {
        viewModelScope.launch {
            // We use the same repository logic to update the mock user
            val result = repository.updateProfile(newName, newBio)
            result.onSuccess { updatedUser ->
                _uiState.value = ProfileUiState.Success(updatedUser)
            }
        }
    }

    fun updateDietaryPrefs(preferences: DietaryPreferences) {
        viewModelScope.launch {
            val result = repository.updateDietaryPreferences(preferences)
            result.onSuccess { updatedUser ->
                _uiState.value = ProfileUiState.Success(updatedUser)
            }.onFailure { error ->
                // Keep the old state but maybe send an error event in a real app
                println("Failed to update preferences: ${error.message}")
            }
        }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = repository.logout()
            if (result.isSuccess) {
                onLogoutSuccess()
            }
        }
    }
}