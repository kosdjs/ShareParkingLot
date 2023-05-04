package com.team.parking.domain.repository

import com.team.parking.data.model.day.DayRequest
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.model.parkinglot.ShareLotRequest
import com.team.parking.data.model.parkinglot.ShareLotResponse
import com.team.parking.data.util.Resource
import okhttp3.MultipartBody

interface ShareLotRepository {
    suspend fun deleteShareLot(parkId: Long) : Resource<Unit>
    suspend fun getShareLotDetail(parkId: Long) : Resource<ParkingLotResponse>
    suspend fun getShareLotList(userId: Long) : Resource<List<ShareLotResponse>>
    suspend fun postShareLot(
        saveDto: ShareLotRequest,
        files: List<MultipartBody.Part>
    ) : Resource<Long>
    suspend fun postSaveDay(
        daySaveDtos: DayRequest,
        parkId: Long
    ) : Resource<Unit>
    suspend fun postUpdateDay(
        daySaveDtos: DayRequest,
        parkId: Long
    ) : Resource<Unit>
}