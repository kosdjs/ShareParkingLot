package com.team.parking.data.repository

import com.team.parking.data.model.map.SearchKeyWordResponse
import com.team.parking.data.model.user.*
import com.team.parking.data.repository.dataSource.UserRemoteDataSource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.UserRepository
import retrofit2.Response

class UserRepositoryImpl (
    private val userRemoteDataSource : UserRemoteDataSource
) : UserRepository{
    override suspend fun login(
        type: String,
        accessToken: String?,
        email: String?,
        password: String?,
        social_id: String?,
        name: String?,
        profile_img: String?,
        fcm_token: String?
    ): Resource<LoginResponse> {
        return responseToResource(userRemoteDataSource.login(type,accessToken,email,password,social_id,name,profile_img,fcm_token))
    }

    override suspend fun checkEmail(email: String): Resource<Boolean> {
        return responseToResource(userRemoteDataSource.checkEmail(email))
    }

    override suspend fun signUp(request: SignUpRequest): Resource<User> {
        return responseToResource(userRemoteDataSource.signUp(request))
    }

    override suspend fun updateFcmToken(updateFcmTokenRequest: UpdateFcmTokenRequest): Resource<Boolean> {
        return responseToResource(userRemoteDataSource.updateFcmToken(updateFcmTokenRequest))
    }

    override suspend fun sendAuthMessage(phone: PhoneRequest): Resource<Boolean> {
        return responseToResource(userRemoteDataSource.sendAuthMessage(phone))
    }

    override suspend fun certificatePhone(phone: String, code: String): Resource<Boolean> {
        return responseToResource(userRemoteDataSource.certificatePhone(phone, code))
    }

    private fun <T> responseToResource(response: Response<out T>): Resource<T> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}