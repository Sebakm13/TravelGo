package com.travelgo.app.data.Repository

import com.travelgo.app.data.remote.dto.LoginResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<LoginResponse>
}