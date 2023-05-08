package com.team.parking.data.repository

import com.team.parking.data.model.day.DayRequest
import com.team.parking.data.model.map.SearchKeyWordResponse
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.model.parkinglot.ShareLotRequest
import com.team.parking.data.model.parkinglot.ShareLotResponse
import com.team.parking.data.repository.dataSource.ShareLotRemoteDatasource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.ShareLotRepository
import okhttp3.MultipartBody
import retrofit2.Response

class ShareLotRepositoryImpl(private val shareLotRemoteDatasource: ShareLotRemoteDatasource) : ShareLotRepository {
    override suspend fun deleteShareLot(parkId: Long): Resource<Unit> {
        return responseToUnit(shareLotRemoteDatasource.deleteShareLot(parkId))
    }

    override suspend fun getShareLotDetail(parkId: Long): Resource<ParkingLotResponse> {
        return responseToParkingLotResponse(shareLotRemoteDatasource.getShareLotDetail(parkId))
    }

    override suspend fun getShareLotList(userId: Long): Resource<List<ShareLotResponse>> {
        return responseToList(shareLotRemoteDatasource.getShareLotList(userId))
    }

    override suspend fun postShareLot(
        saveDto: ShareLotRequest,
        files: List<MultipartBody.Part>
    ): Resource<Long> {
        return responseToLong(shareLotRemoteDatasource.postShareLot(saveDto, files))
    }

    override suspend fun putSaveDay(daySaveDtos: List<DayRequest>, parkId: Long): Resource<Unit> {
        return responseToUnit(shareLotRemoteDatasource.putSaveDay(daySaveDtos, parkId))
    }

    override suspend fun getShareLotDay(parkId: Long): Resource<List<DayRequest>> {
        return responseToDayList(shareLotRemoteDatasource.getShareLotDay(parkId))
    }

    private fun responseToUnit(response: Response<Unit>): Resource<Unit> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToLong(response: Response<Long>): Resource<Long> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToParkingLotResponse(response: Response<ParkingLotResponse>): Resource<ParkingLotResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToList(response: Response<List<ShareLotResponse>>): Resource<List<ShareLotResponse>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToDayList(response: Response<List<DayRequest>>): Resource<List<DayRequest>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}