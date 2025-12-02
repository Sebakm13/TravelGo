package com.travelgo.app.network

import com.travelgo.app.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserDto
}


