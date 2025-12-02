package com.travelgo.app.data.Repository

import com.travelgo.app.data.model.User

class UserRepository(
    private val api: UserApi
) : IUserRepository {

    override suspend fun getUserById(id: Int): Result<User> {
        return try {
            val user = api.getUserById(id)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

interface IUserRepository {
    suspend fun getUserById(id: Int): Result<User>
}


interface UserApi {
    suspend fun getUserById(id: Int): User
}
