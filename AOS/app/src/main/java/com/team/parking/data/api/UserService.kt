package com.team.parking.data.api

import com.team.parking.data.model.user.LoginRequest
import com.team.parking.data.model.user.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface UserService {
    @GET("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}