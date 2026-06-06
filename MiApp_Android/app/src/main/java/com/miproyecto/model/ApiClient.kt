package com.miproyecto.model

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

object ApiClient {
    private const val BASE_URL = "https://api.example.com/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val service: ApiService = retrofit.create(ApiService::class.java)
}

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body payload: Map<String, String>): AuthTokens

    @POST("auth/signup")
    suspend fun signup(@Body payload: Map<String, String>): AuthTokens

    @GET("dashboard")
    suspend fun dashboard(@Header("Authorization") bearer: String): List<String>
}
