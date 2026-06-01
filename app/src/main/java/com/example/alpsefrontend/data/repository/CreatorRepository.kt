package com.example.alpsefrontend.data.repository

import com.example.alpsefrontend.data.api.CreatorApiService
import com.example.alpsefrontend.data.model.CreatorProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CreatorRepository(private val api: CreatorApiService) {

    fun getCreators(): Flow<List<CreatorProfile>> = flow {
        val response = api.getCreators()
        emit(response.data)
    }

    suspend fun toggleFollow(creatorId: Int): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val response = api.toggleFollow(creatorId)
            // Returns the new boolean state of whether we are following them or not
            Result.success(response.isFollowing)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}