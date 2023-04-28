package com.example.domain.dto.point.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointBuyRequestDto {
    private Long sha_id;
    private int pt_lose;
    private int in_timing;
    private int type;
}
