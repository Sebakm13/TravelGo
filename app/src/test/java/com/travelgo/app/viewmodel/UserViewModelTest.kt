package com.travelgo.app.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

data class User(val username: String, val email: String)

interface UserRepository {
    suspend fun getUser(id: String): User
}

class FakeUserRepository : UserRepository {
    override suspend fun getUser(id: String): User {
        return User(username = "testUser", email = "test@example.com")
    }
}

class UserViewModel(private val repository: UserRepository, private val id: String) {

    private val _userState = MutableStateFlow(User("", ""))
    val userState: StateFlow<User> = _userState

    suspend fun fetchUser() {
        val user = repository.getUser(id)
        _userState.value = user
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: UserRepository
    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = FakeUserRepository()
        viewModel = UserViewModel(repository, "123")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchUserUpdatesStateFlow() = runTest {
        // Llamamos a la función que actualiza el StateFlow
        viewModel.fetchUser()

        // Avanzamos el dispatcher para procesar todas las coroutines
        testScheduler.advanceUntilIdle()

        // Verificamos que el StateFlow se actualizó correctamente
        val state = viewModel.userState.first()
        assertEquals("testUser", state.username)
        assertEquals("test@example.com", state.email)
    }
}

