package com.team.parking.data.repository.dataSource

import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.ParkingLot
import retrofit2.Response

interface MapRemoteDatasource {

    suspend fun getParkingLots(mapRequest: MapRequest) : Response<List<ParkingLot>>
}