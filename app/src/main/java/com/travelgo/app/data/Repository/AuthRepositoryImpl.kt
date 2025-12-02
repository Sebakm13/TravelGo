package com.travelgo.app.data.Repository

import com.travelgo.app.network.AuthApi
import com.travelgo.app.data.remote.dto.LoginRequest
import com.travelgo.app.data.remote.dto.LoginResponse

class AuthRepositoryImpl(
    private val api: AuthApi
) {

    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return try {
            val request = LoginRequest(username = username, password = password)
            val response = api.login(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}