package com.team.parking.data.model.ticket

data class TicketSellConfirmResponse(
    val parkingDate: String,
    val parkingRegion: String,
    val ptToEarn: Int,
    val sellConfirm: Boolean,
    val shaId: Int,
    val ticketId: Int
)