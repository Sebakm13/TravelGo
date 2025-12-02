package com.travelgo.app.data.Repository

import android.content.Context
import com.travelgo.app.network.ApiUserDto
import com.travelgo.app.network.UserApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryTest {

    // Mock del contexto (tu repo lo pide aunque no lo uses directamente)
    private val context = mockk<Context>(relaxed = true)

    // Mock de la API real
    private val api = mockk<UserApi>()

    // Creamos manualmente un repositorio pero con nuestro mock de API
    private val repo = UserRepository(context).apply {
        // reemplazamos el ApiClient.userApi con nuestro mock
        val apiField = UserRepository::class.java.getDeclaredField("api")
        apiField.isAccessible = true
        apiField.set(this, api)
    }

    @Test
    fun `fetchUser returns success`() = runTest {

        val dto = ApiUserDto(
            id = 1,
            name = "Sebastian",
            email = "seb@test.com",
            createdAt = 123456L,
            avatarUrl = null
        )

        coEvery { api.getUser(1) } returns dto

        val result = repo.fetchUser(1)

        assert(result.isSuccess)
        assertEquals("Sebastian", result.getOrNull()?.name)
        assertEquals("1", result.getOrNull()?.id)

        @Test
        fun `fetchUser returns failure`() = runTest {

            coEvery { api.getUser(99) } throws Exception("User not found")

            val result = repo.fetchUser(99)

            assert(result.isFailure)
        }

    }
}
