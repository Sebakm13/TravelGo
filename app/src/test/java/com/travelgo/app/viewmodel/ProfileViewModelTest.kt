package com.travelgo.app.viewmodel

import android.app.Application
import com.travelgo.app.data.Repository.IUserRepository
import com.travelgo.app.data.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import junit.framework.TestCase.assertEquals
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.Result.Companion.failure

@ExperimentalCoroutinesApi
class ProfileViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ProfileViewModel

    private val application: Application = mock<Application>()
    private val userRepository: IUserRepository = mock()


    // Ajustado a tu modelo real
    private val fakeUser = User(
        id = "1",
        name = "Juan Perez",
        email = "juan@test.com",
        avatarUrl = null
    )

    @Before
    fun setup() {
        viewModel = ProfileViewModel(application, userRepository)
    }

    @Test
    fun `cuando loadUser es llamado, el estado cambia con datos del usuario`() = runTest {
        whenever(userRepository.getUserById(1)).thenReturn(Result.success(fakeUser))

        viewModel.loadUser()
        advanceUntilIdle()

        val expectedState = ProfileUiState(
            isLoading = false,
            userName = "Juan Perez",
            userEmail = "juan@test.com",
            userImage = null,
            error = null
        )

        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `cuando fetchUser devuelve failure, uiState muestra error`() = runTest {
        whenever(userRepository.getUserById(1)).thenReturn(failure(Exception("fail")))

        viewModel.loadUser()
        advanceUntilIdle()

        val expectedState = ProfileUiState(
            isLoading = false,
            userName = "",
            userEmail = "",
            userImage = null,
            error = "fail"
        )

        assertEquals(expectedState, viewModel.uiState.value)
    }
}
