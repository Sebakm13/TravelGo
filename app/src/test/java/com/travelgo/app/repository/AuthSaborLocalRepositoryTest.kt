package com.travelgo.app.repository

import android.content.Context
import android.content.SharedPreferences
import android.graphics.RuntimeShader
import com.travelgo.app.data.remote.SaborLocalApiService
import com.travelgo.app.data.remote.dto.*
import com.travelgo.app.data.model.User
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue
class AuthSaborLocalRepositoryTest (

    private lateinit var mockContext: Context
    private lateinit var mockApiService: SaborLocalApiService
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor

    private lateinit var repository: AuthSaborLocalRepository

    @before
    fun setup() {
        mockContext = mockk(relaxed = true)
        mockApiservice = mockk()
        mockSharedPreferences = mockk(relaxed = true)
        mockEditor = mockk(relaxed = true)

        every { mockContext.getSharedPreferences(any(), any()) } returns mockSharedPreferences
        every { mockSharedPreferences.edit() } returns mockEditor
        every { mockEditor.putString(any(), any()) } returnsmockEditor
        every { mockEditor.apply() }  just Runs
        every { mockEditor.clear() } returns mockEditor

        mockkObject(com.travelgo.app.data.remote.RetrofitClient)
        every { com.travelgo.app.data.remore.RetrofitClient.saborLocalApiService } returns mockApp
}
)
)