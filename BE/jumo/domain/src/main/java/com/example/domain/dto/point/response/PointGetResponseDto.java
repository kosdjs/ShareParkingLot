package com.example.domain.dto.point.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointGetResponseDto {
    private Long creditId;
    private Long shaId;
    private String lotName;
    private String buyDay;
    private int type;
    private int inTiming;
    private int outTiming;
}
