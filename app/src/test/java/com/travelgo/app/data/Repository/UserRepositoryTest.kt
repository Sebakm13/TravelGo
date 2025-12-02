package com.travelgo.app.data.repository

import com.travelgo.app.data.repository.UserRepository
import com.travelgo.app.data.model.User

data class User(
    val id: Int,
    val nombre: String,
    val email: String
)

open class UserRepository(val api: UserApi) {

    open suspend fun fetchUser(userId: Int): Result<com.travelgo.app.data.model.User> {
        return try {
            val user = api.getUserById(userId)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Interfaz para simular API
interface UserApi {
    suspend fun getUserById(id: Int): User
}
