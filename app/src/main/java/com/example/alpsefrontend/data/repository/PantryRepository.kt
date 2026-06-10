package com.example.alpsefrontend.data.repository

import com.example.alpsefrontend.data.api.PantryApiService
import com.example.alpsefrontend.data.api.PantryRequest
import com.example.alpsefrontend.data.model.PantryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class PantryRepository(private val api: PantryApiService) {

    fun getPantryItems(): Flow<List<PantryItem>> = flow {
        val response = api.getPantryItems()
        emit(response.data)
    }

    suspend fun addPantryItem(item: PantryItem): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            api.addPantryItem(PantryRequest(item.name, item.quantity, item.category ?: ""))
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removePantryItem(itemId: Int): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            api.deletePantryItem(itemId)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}