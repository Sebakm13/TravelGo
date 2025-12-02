package com.travelgo.app.data.remote

import com.travelgo.app.data.remote.dto.*
import retrofit2.http.*

interface ApiService {

    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("user/me")
    suspend fun getCurrentUser(): UserDto

    @GET("users")
    suspend fun getUsers(): UsersResponse

    @GET("users/search")
    suspend fun searchUsers(@Query("q") query: String): UsersResponse

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDto

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserDto


    // ===== DESTINATIONS =====
    @GET("api/destinations")
    suspend fun getDestinations(
        @Query("category") category: String? = null,
        @Query("q") search: String? = null
    ): List<DestinationDto>

    @GET("api/destinations/{id}")
    suspend fun getDestinationById(@Path("id") id: Long): DestinationDto

    @POST("api/destinations")
    suspend fun createDestination(
        @Body request: DestinationCreateRequest
    ): DestinationDto

    @PUT("api/destinations/{id}")
    suspend fun updateDestination(
        @Path("id") id: Long,
        @Body request: DestinationCreateRequest
    ): DestinationDto

    @DELETE("api/destinations/{id}")
    suspend fun deleteDestination(
        @Path("id") id: Long
    ): Unit
}

annotation class DestinationCreateRequest

annotation class DestinationDto
