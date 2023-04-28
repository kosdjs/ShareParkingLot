package com.example.domain.dto.point.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointChargeResponseDto {
    private Long userId;
    private String nickname;
    private int ptCharge;
    private int ptHas;
}
