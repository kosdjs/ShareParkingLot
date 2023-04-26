package com.team.parking.data.api

import com.team.parking.data.model.MapResponse
import retrofit2.Response
import retrofit2.http.GET

interface MapService {

    @GET
    suspend fun getMapsDataFrom() : Response<Response<MapResponse>>
}