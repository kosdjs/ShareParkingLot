package com.example.jumouser.service;


import com.example.domain.dto.point.request.PointConfirmRequestDto;
import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.request.TicketSellerRequestDto;
import com.example.domain.dto.point.response.*;

import java.util.List;

public interface PointService {
    
    // 포인트 충전
    PointChargeResponseDto chargePoint(Long userId, int ptCharge);

    // 주차권 구매
    TicketCreateResponseDto ticketCreate(Long userId, TicketCreateRequestDto ticketCreateRequestDto);

    // 구매 주차권 List 조회
    List<TicketListResponseDto> getBoughtTicketList(Long userId);

    // 판매 주차권 List 조회
    List<TicketListResponseDto> getSoldTicketList(Long shaId);

    // 주차권 상세 조회
    TicketDetailResponseDto getTicket(Long ticketId);

    // 판매 확정
    TicketSellerResponseDto ticketSellConfirm(Long userId, TicketSellerRequestDto ticketSellerRequestDto);

    // 구매 확정
    PointConfirmResponseDto ticketBuyConfirm(Long userId, PointConfirmRequestDto pointConfirmRequestDto);
}
