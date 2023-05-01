package com.example.domain.dto.point.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class TicketSellerResponseDto {
    private Long ticketId;
    private Long shaId;
    private int ptToEarn;
    private String parkingRegion;
    private String parkingDate;

}
