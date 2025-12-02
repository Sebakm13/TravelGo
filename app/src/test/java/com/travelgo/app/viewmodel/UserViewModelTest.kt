package com.travelgo.app.viewmodel

import com.travelgo.app.data.Repository.IUserRepository
import com.travelgo.app.data.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private lateinit var repository: IUserRepository
    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mockk()

        coEvery { repository.getUserById(1) } returns Result.success(
            User(
                id = "1",
                name = "John Doe",
                email = "john@example.com",
                avatarUrl = null
            )
        )

        // Corregido aquí, ahora se usa UserViewModel
        viewModel = UserViewModel(repository)
    }

    @Test
    fun `fetchUser obtiene datos usando MockK`() = runTest {
        viewModel.fetchUser(1)
        testScheduler.advanceUntilIdle()

        val user = viewModel.user.first()
        assertEquals("John Doe", user?.name)
    }
}
