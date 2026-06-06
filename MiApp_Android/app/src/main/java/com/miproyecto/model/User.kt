package com.miproyecto.model

data class User(
    val id: String,
    val name: String,
    val email: String
)

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String
)
