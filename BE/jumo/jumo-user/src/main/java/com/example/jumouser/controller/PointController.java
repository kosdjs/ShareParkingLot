package com.example.jumouser.controller;

import com.example.domain.dto.point.request.PointBuyRequestDto;
import com.example.domain.dto.point.response.PointBuyResponseDto;
import com.example.domain.dto.point.response.PointChargeResponseDto;
import com.example.domain.dto.point.response.PointHasResponseDto;
import com.example.jumouser.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/{userId}")

    public PointHasResponseDto getUserPoint(@PathVariable Long userId) {
        return null;
    }

    @PutMapping("/charge/{userId}/{ptCharge}")
    public PointChargeResponseDto chargePoint(@PathVariable Long userId, @PathVariable int ptCharge) {
        return pointService.chargePoint(userId, ptCharge);
    }

    @PutMapping("/buy/{userId}")
    public PointBuyResponseDto consumePoint(@PathVariable Long userId, @RequestBody PointBuyRequestDto pointBuyRequestDto) {
        return pointService.consumePoint(userId, pointBuyRequestDto);
    }
}
