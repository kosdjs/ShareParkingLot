package com.team.parking.data.api

import com.team.parking.data.model.day.DayRequest
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.model.parkinglot.ShareLotRequest
import com.team.parking.data.model.parkinglot.ShareLotResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ShareLotAPIService {
    @DELETE("shareLot/delete")
    suspend fun deleteShareLot(@Query("parkId") parkId: Long) : Response<String>

    @GET("shareLot/detail")
    suspend fun getShareLotDetail(@Query("parkId") parkId: Long) : Response<ParkingLotResponse>

    @GET("shareLot/myList")
    suspend fun getShareLotList(@Query("userId") userId: Long) : Response<List<ShareLotResponse>>

    @POST("shareLot/save")
    suspend fun postShareLot(
        @Part("files") files: List<MultipartBody.Part>,
        @Part("saveDto") saveDto: ShareLotRequest,
        @Part("userId") userId: Long
    )

    @POST("shareLot/saveDay")
    suspend fun postSaveDay(
        @Body daySaveDtos: DayRequest,
        @Body parkId: Long
    )

    @POST("shareLot/updateDay")
    suspend fun postUpdateDay(
        @Body daySaveDtos: DayRequest,
        @Body parkId: Long
    )
}