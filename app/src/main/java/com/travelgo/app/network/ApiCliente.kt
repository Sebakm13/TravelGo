package com.travelgo.app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ApiClient {
    // base URL para tus microservicios durante dev
    private const val BASE_URL = "http://10.0.2.2:8082/" // emulator -> host machine
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userApi: UserApi = retrofit.create(UserApi::class.java)
    val tripsApi: TripsApi = retrofit.create(TripsApi::class.java)

    // Example for external APIs (OpenWeather)
    val weatherApi: WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)
}

data class ApiUserDto(
    val id: Int,
    val name: String?,
    val email: String?,
    val createdAt: Long?,
    val avatarUrl: String?
)

interface UserApi {
    @GET("api/users/{id}")
    suspend fun getUser(@Path("id") id: Int): ApiUserDto
}

data class TripDto(
    val id: Long?,
    val title: String,
    val description: String?,
    val origin: String?,
    val destination: String?,
    val price: Double?,
    val capacity: Int?
)

interface TripsApi {
    @GET("api/trips")
    suspend fun getTrips(): List<TripDto>

    @POST("api/trips")
    suspend fun createTrip(@Body trip: TripDto): TripDto
}

// OpenWeather example
data class WeatherResponse(val weather: List<Map<String,Any>>, val main: Map<String,Any>)

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(@Query("q") city: String, @Query("appid") key: String): WeatherResponse
}
