package com.example.jumouser.controller;

import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.response.*;
import com.example.jumouser.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    // 구매 가능한 주차권
    @GetMapping("/{shaId}/{time}")
    public TypeResponseDto getTypeAvailability(@PathVariable Long shaId, @PathVariable int time) {
        return ticketService.getTypeAvailability(shaId, time);
    }

    // 주차권 구매 (완료)
    @PostMapping("/{userId}")
    public TicketCreateResponseDto buyTicket(@PathVariable Long userId, @RequestBody TicketCreateRequestDto ticketCreateRequestDto) {
        return ticketService.ticketCreate(userId, ticketCreateRequestDto);
    }

    // 주차권 당일 구매 목록 조회 (완료)
    @GetMapping("/bought/{userId}")
    public List<TicketBuyerResponseDto> getBoughtTicket(@PathVariable Long userId) {
        return ticketService.getBoughtTicketList(userId);
    }

    // 주차권 당일 판매 목록 조회 (완료)
    @GetMapping("/sold/{userId}/{shaId}")
    public List<TicketSellerResponseDto> getSoldTicket(@PathVariable Long userId , @PathVariable Long shaId) {
        return ticketService.getSoldTicketList(userId, shaId);
    }

    // 주차권 상세 조회 (완료)
    @GetMapping("/{ticketId}")
    public TicketDetailResponseDto getTicket(@PathVariable Long ticketId) {
        return ticketService.getTicket(ticketId);
    }

    // 판매확정 (완료)
    @PutMapping("/sell/{userId}/{ticketId}")
    public TicketSellConfirmResponseDto sellerConfirm(@PathVariable Long userId, @PathVariable Long ticketId) {
        return ticketService.ticketSellConfirm(userId, ticketId);
    }

    // 구매확정 (완료)
    @PutMapping("/buy/{userId}/{ticketId}")
    public TicketBuyConfirmResponseDto consumePoint(@PathVariable Long userId, @PathVariable Long ticketId) {
        return ticketService.ticketBuyConfirm(userId, ticketId);
    }
}
