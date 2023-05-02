package com.example.domain.dto.point.response;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class TicketCreateResponseDto {
    private Long ticketId;
    private String buyer;
    private String carNumber;
    private Long shaId;
    private String parkingRegion;
    private String address;
    private int type;
    private int inTiming;
    private int outTiming;
    private int cost;
}
