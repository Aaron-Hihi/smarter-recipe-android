package com.example.alpsefrontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alpsefrontend.data.repository.*

class AppViewModelFactory(
    private val authRepository: AuthRepository? = null,
    private val recipeRepository: RecipeRepository? = null,
    private val savedRepository: SavedRepository? = null,
    private val profileRepository: ProfileRepository? = null,
    private val pantryRepository: PantryRepository? = null,
    private val creatorRepository: CreatorRepository? = null // Small 'c', mon cher!
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(authRepository!!) as T
            modelClass.isAssignableFrom(RecipeViewModel::class.java) ->
                RecipeViewModel(recipeRepository!!) as T
            modelClass.isAssignableFrom(SavedViewModel::class.java) ->
                SavedViewModel(savedRepository!!) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(profileRepository!!) as T
            modelClass.isAssignableFrom(PantryViewModel::class.java) ->
                PantryViewModel(pantryRepository!!) as T
            modelClass.isAssignableFrom(CreatorViewModel::class.java) ->
                CreatorViewModel(creatorRepository!!) as T // FIXED: Small 'c'!
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}