package com.example.alpsefrontend.data.api

import com.example.alpsefrontend.data.model.CreatorProfile
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// DTOs for Creators
data class CreatorListResponse(val data: List<CreatorProfile>)
data class FollowToggleResponse(val message: String, val isFollowing: Boolean)

interface CreatorApiService {

    // READ: Get the list of all creators
    @GET("creators")
    suspend fun getCreators(): CreatorListResponse

    // CREATE/DELETE (Toggle): Follow or Unfollow a creator
    @POST("creators/{id}/follow")
    suspend fun toggleFollow(@Path("id") creatorId: Int): FollowToggleResponse
}