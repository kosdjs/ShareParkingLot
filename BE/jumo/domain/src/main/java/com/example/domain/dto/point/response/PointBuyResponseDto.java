package com.example.domain.dto.point.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointBuyResponseDto {
    private Long creditId;
    private int ptLose;
    private int ptHas;
    private String parkingRegion;
    private String buyDay;
    private int type;
    private int inTiming;
    private int outTiming;
}
