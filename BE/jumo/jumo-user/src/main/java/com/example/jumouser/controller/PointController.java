package com.example.jumouser.controller;

import com.example.domain.dto.point.request.PointConfirmRequestDto;
import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.request.TicketSellerRequestDto;
import com.example.domain.dto.point.response.*;
import com.example.domain.repo.UserRepo;
import com.example.jumouser.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;
    private final UserRepo userRepo;

    // 현재 포인트 조회 (완료)
    @GetMapping("/point/{userId}")
    public int getUserPoint(@PathVariable Long userId) {
        return pointService.getUserPoint(userId);
    }

    // 월별 포인트 획득 내역 조회 (완료)
    @GetMapping("/point/earned/{userId}/{year}/{month}")
    public List<PointEarnedResponseDto> getEarnedPointList(@PathVariable Long userId, @PathVariable String year, @PathVariable String month) {
        return pointService.getEarnedPointList(userId, year, month);
    }

    // 월별 포인트 사용 내역 조회 (완료)
    @GetMapping("/point/spent/{userId}/{year}/{month}")
    public List<PointSpentResponseDto> getSpentPointList(@PathVariable Long userId, @PathVariable String year, @PathVariable String month) {
        return pointService.getSpentPointList(userId, year, month);
    }

    // 포인트 충전 (완료)
    @PutMapping("/point/charge/{userId}/{ptCharge}")
    public PointChargeResponseDto chargePoint(@PathVariable Long userId, @PathVariable int ptCharge) {
        return pointService.chargePoint(userId, ptCharge);
    }

    // 주차권 구매 (완료)
    @PostMapping("/ticket/{userId}")
    public TicketCreateResponseDto buyTicket(@PathVariable Long userId, @RequestBody TicketCreateRequestDto ticketCreateRequestDto) {
        return pointService.ticketCreate(userId, ticketCreateRequestDto);
    }

    // 주차권 당일 구매 목록 조회 (완료)
    @GetMapping("/ticket/bought/{userId}")
    public List<TicketBuyerResponseDto> getBoughtTicket(@PathVariable Long userId) {
        return pointService.getBoughtTicketList(userId);
    }

    // 주차권 당일 판매 목록 조회 (완료)
    @GetMapping("/ticket/sold/{userId}/{shaId}")
    public List<TicketSellerResponseDto> getSoldTicket(@PathVariable Long userId , @PathVariable Long shaId) {
        return pointService.getSoldTicketList(userId, shaId);
    }

    // 주차권 상세 조회 (완료)
    @GetMapping("/ticket/{ticketId}")
    public TicketDetailResponseDto getTicket(@PathVariable Long ticketId) {
        return pointService.getTicket(ticketId);
    }

    // 판매확정 (완료)
    @PutMapping("/ticket/sell/{userId}/{ticketId}")
    public TicketSellConfirmResponseDto sellerConfirm(@PathVariable Long userId, @PathVariable Long ticketId) {
        return pointService.ticketSellConfirm(userId, ticketId);
    }

    // 구매확정 (완료)
    @PutMapping("/ticket/buy/{userId}/{ticketId}")
    public TicketBuyConfirmResponseDto consumePoint(@PathVariable Long userId, @PathVariable Long ticketId) {
        return pointService.ticketBuyConfirm(userId, ticketId);
    }
}
