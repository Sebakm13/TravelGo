package com.travelgo.app.data.Repository

import android.content.Context
import com.travelgo.app.data.remote.ApiService
import com.travelgo.app.data.remote.RetrofitClient
import com.travelgo.app.data.remote.dto.UserDto

class UserRepository(context: Context) {

    private val apiService: ApiService = RetrofitClient
        .create(context)
        .create(ApiService::class.java)

    suspend fun fetchUser(id: Int = 1): Result<UserDto> {
        return try {
            // Llamar a la API
            val user = apiService.getUser(id)

            // Retornar éxito
            Result.success(user)
        } catch (e: Exception) {
            // Si algo falla (sin internet, timeout, etc.)
            Result.failure(e)
        }
    }
}
