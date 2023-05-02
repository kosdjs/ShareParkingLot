package com.example.domain.dto.point.response;

import com.example.domain.entity.Ticket;
import com.example.domain.etc.OutTiming;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@RequiredArgsConstructor

public class TicketListResponseDto {
    @JsonIgnore
    private final OutTiming outTiming;

    private Long ticketId;
    private String parkingRegion;
    private String parkingDate;
    private int inTiming;
    private int outTime;

    @Builder
    public TicketListResponseDto(OutTiming outTiming, Ticket ticket) {
        this.outTiming = outTiming;
        this.ticketId = ticket.getTicket_id();
        this.parkingRegion = ticket.getParkingRegion();
        this.parkingDate = ticket.getParking_date();
        this.inTiming = ticket.getIn_timing();
        this.outTime = this.outTiming.OutTimingMethod(ticket.getIn_timing(), ticket.getType());
    }

}
