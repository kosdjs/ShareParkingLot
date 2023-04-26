package com.team.parking.data.repository.dataSourceImpl

import com.team.parking.data.api.MapService
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.ParkingLot
import com.team.parking.data.repository.dataSource.MapRemoteDatasource
import retrofit2.Response

class MapRemoteDataSourceImpl(
    private val mapService: MapService
) : MapRemoteDatasource{
    override suspend fun getParkingLots(mapRequest: MapRequest): Response<List<ParkingLot>> {
        return mapService.getMapsDataFrom(mapRequest)
    }
}