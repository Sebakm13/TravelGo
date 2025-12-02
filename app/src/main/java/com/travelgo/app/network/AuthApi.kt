package com.travelgo.app.network

import com.travelgo.app.data.remote.dto.LoginRequest
import com.travelgo.app.data.remote.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}