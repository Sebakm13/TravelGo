package com.travelgo.app.viewmodel

import com.travelgo.app.data.Repository.UserRepository
import com.travelgo.app.data.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserViewModelTest {

    private lateinit var viewModel: UserViewModel
    private val repository = mockk<UserRepository>()

    @Before
    fun setup() {
        viewModel = UserViewModel(repository)
    }

    @Test
    fun `fetchUser should update user StateFlow`() = runBlocking {
        // GIVEN: tu modelo User usa id STRING
        val fakeUser = User(
            id = "1",
            name = "Alice",
            email = "alice@mail.com",
            createdAt = 123456L,
            avatarUrl = null
        )

        coEvery { repository.fetchUser(1) } returns Result.success(fakeUser)

        // WHEN
        viewModel.fetchUser(1)

        // THEN
        assertEquals(fakeUser, viewModel.user.value)
    }
}
