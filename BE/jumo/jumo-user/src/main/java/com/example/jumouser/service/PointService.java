package com.example.jumouser.service;


import com.example.domain.dto.point.request.PointConfirmRequestDto;
import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.request.TicketSellerRequestDto;
import com.example.domain.dto.point.response.*;

import java.util.List;

public interface PointService {

    // 현재 포인트 조회
    int getUserPoint(Long userId);

    // 월별 포인트 획득 내역 조회
    List<PointEarnedResponseDto> getEarnedPointList(Long userId, String year, String month);

    // 월별 포인트 사용 내역 조회
    List<PointSpentResponseDto> getSpentPointList(Long userId, String year, String month);


    // 포인트 충전
    PointChargeResponseDto chargePoint(Long userId, int ptCharge);

    // 주차권 구매
    TicketCreateResponseDto ticketCreate(Long userId, TicketCreateRequestDto ticketCreateRequestDto);

    // 구매 주차권 List 조회
    List<TicketBuyerResponseDto> getBoughtTicketList(Long userId);

    // 판매 주차권 List 조회
    List<TicketSellerResponseDto> getSoldTicketList(Long userId, Long shaId);

    // 주차권 상세 조회
    TicketDetailResponseDto getTicket(Long ticketId);

    // 판매 확정
    TicketSellConfirmResponseDto ticketSellConfirm(Long userId, Long ticketId);

    // 구매 확정
    TicketBuyConfirmResponseDto ticketBuyConfirm(Long userId, Long ticketId);
}
