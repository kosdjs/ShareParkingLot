package com.team.parking.domain.usecase

import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.ParkingLot
import com.team.parking.domain.repository.MapRepository
import retrofit2.Response

class GetMapDataUseCase(private val mapRepository: MapRepository) {

    suspend fun execute(request: MapRequest) : Response<List<ParkingLot>>{
        return mapRepository.getParkingLots(request)
    }
}