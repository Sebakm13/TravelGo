package com.travelgo.app.data.Repository

import android.content.Context
import com.travelgo.app.data.model.User
import com.travelgo.app.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserRepository(private val context: Context) {

    private val api = ApiClient.userApi

    suspend fun fetchUser(id: Int): Result<User> = withContext(Dispatchers.IO) {
        try {
            val dto = api.getUser(id)
            val user = User(
                id = dto.id.toString(),
                name = dto.name ?: "",
                email = dto.email ?: "",
                createdAt = dto.createdAt ?: System.currentTimeMillis(),
                avatarUrl = dto.avatarUrl
            )
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
