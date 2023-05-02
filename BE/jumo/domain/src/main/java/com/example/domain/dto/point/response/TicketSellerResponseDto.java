package com.example.domain.dto.point.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TicketSellerResponseDto {
    private Long ticketId;
    private Long shaId;
    private int ptToEarn;
    private String parkingRegion;
    private String parkingDate;
    private boolean sellConfirm;

}
