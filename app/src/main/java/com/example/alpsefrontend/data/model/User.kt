package com.example.alpsefrontend.data.model


data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val bio: String? = null,
    val profileImageUrl: String? = null,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val dietaryPreferences: DietaryPreferences? = null
)

data class DietaryPreferences(
    val isVegan: Boolean = false,
    val isVegetarian: Boolean = false,
    val isHalal: Boolean = true,
    val isGlutenFree: Boolean = false,
    val isNutAllergy: Boolean = false,
    val isDairyFree: Boolean = false
)

// ---------------------------------------------------------------------------
// 2. RECIPE & CULINARY MODELS
// ---------------------------------------------------------------------------

data class Recipe(
    val id: Int,
    val title: String,
    val authorName: String,
    val authorId: Int,
    val cookTime: String,
    val servings: Int,
    val averageRating: Double = 0.0,
    val imageUrl: String? = null,
    val matchScore: Int? = null, // Used for the "95% Match" feature in Discover
    val isPublished: Boolean = true,
    val dietaryTags: List<String> = emptyList(),
    val ingredients: List<RecipeIngredient> = emptyList(),
    val steps: List<String> = emptyList()
)

data class RecipeIngredient(
    val id: Int,
    val name: String,
    val quantity: String,
    val isMissing: Boolean = false // Calculated dynamically against the user's Pantry
)

data class Review(
    val id: Int,
    val recipeId: Int,
    val username: String,
    val rating: Int,
    val comment: String,
    val timestamp: String
)


data class PantryItem(
    val id: Int,
    val name: String,
    val quantity: String,
    val category: String? = null
)

data class CreatorProfile(
    val id: Int,
    val user: User, // Inherits basic user info
    val specialty: String,
    val topRecipes: List<Recipe> = emptyList(),
    val isFollowing: Boolean = false
)