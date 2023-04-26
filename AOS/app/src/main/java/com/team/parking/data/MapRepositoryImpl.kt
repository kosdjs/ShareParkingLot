package com.team.parking.data

import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.ParkingLot
import com.team.parking.data.repository.dataSource.MapRemoteDatasource
import com.team.parking.domain.repository.MapRepository
import retrofit2.Response

class MapRepositoryImpl(
    private val mapRemoteDatasource: MapRemoteDatasource
) : MapRepository{
    override suspend fun getParkingLots(mapRequest: MapRequest): Response<List<ParkingLot>> {
        return mapRemoteDatasource.getParkingLots(mapRequest)
    }
}