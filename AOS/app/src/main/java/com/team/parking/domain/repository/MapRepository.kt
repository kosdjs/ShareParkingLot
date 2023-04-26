package com.team.parking.domain.repository

import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.ParkingLot
import retrofit2.Response

interface MapRepository {

    suspend fun getParkingLots(mapRequest: MapRequest) : Response<List<ParkingLot>>
}