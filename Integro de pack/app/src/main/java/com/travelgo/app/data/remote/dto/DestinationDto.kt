
package com.travelgo.app.data.remote.dto

data class DestinationDto(
    val id: Long,
    val name: String,
    val city: String,
    val country: String,
    val category: String,
    val pricePerNight: Double,
    val imageUrl: String?,
    val weatherSummary: String?
)

data class DestinationCreateRequest(
    val name: String,
    val city: String,
    val country: String,
    val category: String,
    val pricePerNight: Double,
    val imageUrl: String? = null,
    val weatherSummary: String? = null
)
