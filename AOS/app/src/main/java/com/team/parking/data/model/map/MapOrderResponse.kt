package com.team.parking.data.model.map


import com.google.gson.annotations.SerializedName

data class MapOrderResponse(
    @SerializedName("feeBasic")
    val feeBasic: Int,
    @SerializedName("lat")
    val lat: Int,
    @SerializedName("lng")
    val lng: Int,
    @SerializedName("meter")
    val meter: Int,
    @SerializedName("parkId")
    val parkId: Int,
    @SerializedName("parkingField")
    val parkingField: Int,
    @SerializedName("parkingName")
    val parkingName: String,
    @SerializedName("payType")
    val payType: String
)