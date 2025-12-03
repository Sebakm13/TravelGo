package com.travelgo.app.data.Repository

import com.travelgo.app.data.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class UserRepositoryTest {

    @Test
    fun fetchUser_returnsSuccess_whenApiReturnsUser() = runBlocking {
        val fakeApi = object : UserApi {
            override suspend fun getUserById(id: Int): User {
                return User(id = id, name = "Sebastián", email = "sebastian@test.com")
            }
        }

        val repo = UserRepository(fakeApi)

        val result = repo.fetchUser(1)

        assertTrue(result.isSuccess)
        assertEquals("Sebastián", result.getOrNull()?.name)
        assertEquals("sebastian@test.com", result.getOrNull()?.email)
    }

    @Test
    fun fetchUser_returnsFailure_whenApiThrowsException() = runBlocking {
        val fakeApi = object : UserApi {
            override suspend fun getUserById(id: Int): User {
                throw RuntimeException("API error")
            }
        }

        val repo = UserRepository(fakeApi)

        val result = repo.fetchUser(1)

        assertTrue(result.isFailure)
    }
}
