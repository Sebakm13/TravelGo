package com.travelgo.app.viewmodel

import android.net.Uri
import com.travelgo.app.data.model.User   // ✅ tu propia clase

data class ProfileUiState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val error: String? = null,
    val formattedCreatedAt: String = "",
    val avatarUri: Uri? = null
)