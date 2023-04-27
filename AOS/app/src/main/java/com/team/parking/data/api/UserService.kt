package com.team.parking.data.api

import com.team.parking.data.model.user.LoginRequest
import com.team.parking.data.model.user.LoginResponse
import com.team.parking.data.model.user.SignUpRequest
import com.team.parking.data.model.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("user/login")
    suspend fun login(@Query("loginRequestDto") loginRequest: LoginRequest): Response<LoginResponse>

    @GET("user/email")
    suspend fun checkEmail(@Query("email")email: String) : Response<Boolean>

    @POST("user/sign")
    suspend fun signUp(@Body request: SignUpRequest) : Response<User>
}