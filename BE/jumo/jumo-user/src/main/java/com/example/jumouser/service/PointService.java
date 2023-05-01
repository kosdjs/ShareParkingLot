package com.example.jumouser.service;


import com.example.domain.dto.point.request.PointConfirmRequestDto;
import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.request.TicketSellerRequestDto;
import com.example.domain.dto.point.response.PointConfirmResponseDto;
import com.example.domain.dto.point.response.PointChargeResponseDto;
import com.example.domain.dto.point.response.TicketCreateResponseDto;
import com.example.domain.dto.point.response.TicketSellerResponseDto;

public interface PointService {
    
    // 포인트 충전
    PointChargeResponseDto chargePoint(Long userId, int ptCharge);

    // 주차권 구매
    TicketCreateResponseDto ticketCreate(Long userId, TicketCreateRequestDto ticketCreateRequestDto);

    // 판매 확정
    TicketSellerResponseDto ticketSellConfirm(Long userId, TicketSellerRequestDto ticketSellerRequestDto);

    // 구매 확정
    PointConfirmResponseDto ticketBuyConfirm(Long userId, PointConfirmRequestDto pointConfirmRequestDto);
}
