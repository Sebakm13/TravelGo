package com.travelgo.app.data.Repository

import com.travelgo.app.data.model.User

class UserRepositoryImpl(
    private val api: UserApi
) : IUserRepository {

    override suspend fun getUserById(id: Int): Result<User> {
        return try {
            Result.success(api.getUserById(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
