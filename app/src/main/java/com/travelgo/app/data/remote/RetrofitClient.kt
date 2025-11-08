package com.travelgo.app.data.remote

import android.content.Context
import com.travelgo.app.data.local.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    fun create(context: Context): Retrofit {

        // 1️⃣ SessionManager para manejar el token
        val sessionManager = SessionManager(context)

        // 2️⃣ AuthInterceptor para inyectar el token automáticamente
        val authInterceptor = AuthInterceptor(sessionManager)

        // 3️⃣ HttpLoggingInterceptor para debugging
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY  // ⚠️ Cambiar a NONE en producción
        }

        // 4️⃣ OkHttpClient con AMBOS interceptores
        val okHttpClient = OkHttpClient.Builder()
            // ⚠️ ORDEN IMPORTANTE: AuthInterceptor primero, luego Logging
            .addInterceptor(authInterceptor)    // Añade el token
            .addInterceptor(loggingInterceptor)  // Muestra en Logcat (con token)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        // 5️⃣ Retrofit con el cliente configurado
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}
