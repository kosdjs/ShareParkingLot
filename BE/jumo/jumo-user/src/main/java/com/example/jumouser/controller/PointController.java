package com.example.jumouser.controller;

import com.example.domain.dto.point.request.PointConfirmRequestDto;
import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.request.TicketSellerRequestDto;
import com.example.domain.dto.point.response.*;
import com.example.domain.entity.Ticket;
import com.example.jumouser.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    // 현재 포인트 조회
    @GetMapping("/point/{userId}")
    public PointHasResponseDto getUserPoint(@PathVariable Long userId) {
        return null;
    }

    // 포인트 충전
    @PutMapping("/point/charge/{userId}/{ptCharge}")
    public PointChargeResponseDto chargePoint(@PathVariable Long userId, @PathVariable int ptCharge) {
        return pointService.chargePoint(userId, ptCharge);
    }

    // 주차권 구매
    @PostMapping("/ticket/{userId}")
    public TicketCreateResponseDto buyTicket(@PathVariable Long userId, @RequestBody TicketCreateRequestDto ticketCreateRequestDto) {
        return pointService.ticketCreate(userId, ticketCreateRequestDto);
    }

    // 주차권 조회



    // 판매확정
    @PutMapping("/ticket/sell/{userId}")
    public TicketSellerResponseDto sellerConfirm(@PathVariable Long userId, @RequestBody TicketSellerRequestDto ticketSellerRequestDto) {
        return pointService.ticketSellConfirm(userId, ticketSellerRequestDto);
    }

    // 구매확정
    @PutMapping("/ticket/buy/{userId}")
    public PointConfirmResponseDto consumePoint(@PathVariable Long userId, @RequestBody PointConfirmRequestDto pointConfirmRequestDto) {
        return pointService.ticketBuyConfirm(userId, pointConfirmRequestDto);
    }
}
