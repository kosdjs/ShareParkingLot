package com.team.parking.data.repository.dataSourceImpl

import com.team.parking.data.api.ShareLotAPIService
import com.team.parking.data.model.day.DayRequest
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.model.parkinglot.ShareLotRequest
import com.team.parking.data.model.parkinglot.ShareLotResponse
import com.team.parking.data.repository.dataSource.ShareLotRemoteDatasource
import okhttp3.MultipartBody
import retrofit2.Response

class ShareLotRemoteDataSourceImpl(private val shareLotAPIService: ShareLotAPIService) : ShareLotRemoteDatasource {
    override suspend fun deleteShareLot(parkId: Long): Response<Unit> {
        return shareLotAPIService.deleteShareLot(parkId)
    }

    override suspend fun getShareLotDetail(parkId: Long): Response<ParkingLotResponse> {
        return shareLotAPIService.getShareLotDetail(parkId)
    }

    override suspend fun getShareLotList(userId: Long): Response<List<ShareLotResponse>> {
        return shareLotAPIService.getShareLotList(userId)
    }

    override suspend fun postShareLot(
        saveDto: ShareLotRequest,
        files: List<MultipartBody.Part>
    ) : Response<Long> {
        return shareLotAPIService.postShareLot(saveDto, files)
    }

    override suspend fun postSaveDay(daySaveDtos: DayRequest, parkId: Long) : Response<Unit> {
        return shareLotAPIService.postSaveDay(daySaveDtos, parkId)
    }

    override suspend fun postUpdateDay(daySaveDtos: DayRequest, parkId: Long) : Response<Unit> {
        return shareLotAPIService.postUpdateDay(daySaveDtos, parkId)
    }
}