package com.example.jumouser.service;


import com.example.domain.dto.point.request.PointBuyRequestDto;
import com.example.domain.dto.point.response.PointBuyResponseDto;
import com.example.domain.dto.point.response.PointChargeResponseDto;

public interface PointService {
    PointChargeResponseDto chargePoint(Long userId, int ptCharge);

    PointBuyResponseDto consumePoint(Long userId, PointBuyRequestDto pointBuyRequestDto);
}
