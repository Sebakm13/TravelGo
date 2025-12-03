package com.travelgo.app.data.model

// Modelo de usuario usado en toda la app
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String? = null
)
