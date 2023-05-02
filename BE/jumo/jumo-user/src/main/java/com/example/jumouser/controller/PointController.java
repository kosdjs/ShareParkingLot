package com.example.jumouser.controller;

import com.example.domain.dto.point.request.PointConfirmRequestDto;
import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.request.TicketSellerRequestDto;
import com.example.domain.dto.point.response.*;
import com.example.domain.entity.Ticket;
import com.example.domain.entity.User;
import com.example.domain.repo.UserRepo;
import com.example.jumouser.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;
    private final UserRepo userRepo;

    // 현재 포인트 조회
    @GetMapping("/point/{userId}")
    public int getUserPoint(@PathVariable Long userId) {
        Optional<User> currUser = userRepo.findById(userId);
        if (currUser.isPresent()) {
            return currUser.get().getPt_has();
        } else throw new IllegalStateException();
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

    // 주차권 구매 목록 조회
    @GetMapping("/ticket/bought/{userId}")
    public List<TicketListResponseDto> getBoughtTicket(@PathVariable Long userId) {
        return pointService.getBoughtTicketList(userId);
    }

    // 주차권 판매 목록 조회
    @GetMapping("/ticket/sold/{shaId}")
    public List<TicketListResponseDto> getSoldTicket(@PathVariable Long shaId) {
        return pointService.getSoldTicketList(shaId);
    }

    // 주차권 상세 조회
    @GetMapping("/ticket/{ticketId}")
    public TicketDetailResponseDto getTicket(@PathVariable Long ticketId) {
        return pointService.getTicket(ticketId);
    }

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
