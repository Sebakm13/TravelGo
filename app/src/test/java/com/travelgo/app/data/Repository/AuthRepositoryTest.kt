package com.travelgo.app.data.repository

import com.travelgo.app.data.Repository.AuthRepositoryImpl
import com.travelgo.app.network.AuthApi
import com.travelgo.app.data.remote.dto.LoginResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthRepositoryTest {

    private val api = mockk<AuthApi>()
    private val repo = AuthRepositoryImpl(api)

    @Test
    fun `login success`() = runTest {
        val mockResponse = LoginResponse(
            id = 1,
            username = "sebastian",
            email = "seb@test.com",
            firstName = "Sebastian",
            lastName = "Aird",
            accessToken = "abc123",
            refreshToken = "refresh123"
        )

        coEvery { api.login(any()) } returns mockResponse

        val result = repo.login("sebastian", "1234")

        assertTrue(result.isSuccess)
        assertEquals("abc123", result.getOrNull()?.accessToken)
    }
}
