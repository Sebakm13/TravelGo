package com.travelgo.app.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val avatarUrl: String? = null
)