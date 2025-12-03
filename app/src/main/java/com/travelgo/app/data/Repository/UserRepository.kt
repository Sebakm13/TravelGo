package com.travelgo.app.data.Repository

import com.travelgo.app.data.model.User

open class UserRepository(val api: UserApi) {

    open suspend fun fetchUser(userId: Int): Result<User> {
        return try {
            val user = api.getUserById(userId)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

interface UserApi {
    suspend fun getUserById(id: Int): User
}
interface IUserRepository {
    suspend fun getUserById(id: Int): Result<User>
}
