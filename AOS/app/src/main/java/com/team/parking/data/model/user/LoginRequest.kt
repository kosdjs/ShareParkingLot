package com.team.parking.data.model.user


data class LoginRequest(
    val type: String,
    val accessToken : String
)