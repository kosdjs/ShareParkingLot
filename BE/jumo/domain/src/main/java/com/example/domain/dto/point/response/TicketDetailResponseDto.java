package com.example.domain.dto.point.response;

import com.example.domain.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class TicketDetailResponseDto {
    private Long ticketId;
    private String parkingRegion;
    private String nickname;
    private String carNumber;
    private String parkingDate;
    private int type;
    private int inTiming;
    private int outTiming;
    private int cost;
    private List<Image> images;
    private boolean sellConfirm;
    private boolean buyConfirm;
}
