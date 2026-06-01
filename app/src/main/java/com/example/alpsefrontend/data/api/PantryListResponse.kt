package com.example.alpsefrontend.data.api

import com.example.alpsefrontend.data.model.PantryItem
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// DTOs
data class PantryListResponse(val data: List<PantryItem>)
data class PantryRequest(val name: String, val quantity: String, val category: String)
data class GenericMessageResponse(val message: String)

interface PantryApiService {

    // READ: Get all items in the user's pantry
    @GET("pantry")
    suspend fun getPantryItems(): PantryListResponse

    // CREATE: Add a new ingredient
    @POST("pantry")
    suspend fun addPantryItem(@Body request: PantryRequest): PantryItem

    // DELETE: Remove an ingredient by ID
    @DELETE("pantry/{id}")
    suspend fun deletePantryItem(@Path("id") id: Int): GenericMessageResponse
}