package com.travelgo.app.viewmodel

import android.app.Application
import com.travelgo.app.data.Repository.UserRepository
import com.travelgo.app.data.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel
    private val repository = mockk<UserRepository>()
    private val application = mockk<Application>(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProfileViewModel(application, repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadUserSuccess() = runTest {
        val fakeUser = User(
            id = "1",
            name = "Ana Torres",
            email = "ana@example.com",
            avatarUrl = "image.png"
        )

        coEvery { repository.fetchUser(1) } returns Result.success(fakeUser)

        viewModel.loadUser(1)
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertEquals("Ana Torres", state.userName)
        assertEquals("ana@example.com", state.userEmail)
        assertEquals("image.png", state.userImage)
        assertNull(state.error)
    }

    @Test
    fun loadUserError() = runTest {

        coEvery { repository.fetchUser(1) } returns Result.failure(Exception("Error de red"))

        viewModel.loadUser(1)
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertEquals("Error de red", state.error)
    }
}
