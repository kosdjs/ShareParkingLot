package com.example.jumouser.service.impl;

import com.example.domain.dto.point.request.PointConfirmRequestDto;
import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.request.TicketSellerRequestDto;
import com.example.domain.dto.point.response.PointConfirmResponseDto;
import com.example.domain.dto.point.response.PointChargeResponseDto;
import com.example.domain.dto.point.response.TicketCreateResponseDto;
import com.example.domain.dto.point.response.TicketSellerResponseDto;
import com.example.domain.entity.ShareLot;
import com.example.domain.entity.Ticket;
import com.example.domain.entity.Transaction;
import com.example.domain.entity.User;
import com.example.domain.repo.ShareLotRepo;
import com.example.domain.repo.TicketRepo;
import com.example.domain.repo.TransactionRepo;
import com.example.domain.repo.UserRepo;
import com.example.error.exception.InputException;
import com.example.error.exception.SaveException;
import com.example.jumouser.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;
    private final ShareLotRepo shareLotRepo;
    private final TicketRepo ticketRepo;

    @Override
    public PointChargeResponseDto chargePoint(Long userId, int ptCharge) {
        Optional<User> user = userRepo.findById(userId);

        // 포인트 증가 User Entity 반영
        if(user.isPresent()){
            User currUser = user.get();
            currUser.addPoint(ptCharge);
            userRepo.save(currUser);

            return new PointChargeResponseDto(
                    currUser.getUser_id(), currUser.getName(), ptCharge, currUser.getPt_has()
            );

        }
        return null;
    }

    @Override
    public TicketCreateResponseDto ticketCreate(Long userId, TicketCreateRequestDto ticketCreateRequestDto) {

        Optional<User> user = userRepo.findById(userId);
        Optional<ShareLot> shareLot = shareLotRepo.findById(ticketCreateRequestDto.getShaId());

        if (user.isPresent() && shareLot.isPresent()) {

            // 출차시간 계산
            int[] plusHour = new int[]{1, 3, 5, 24};
            int outTiming = ticketCreateRequestDto.getInTiming() + plusHour[ticketCreateRequestDto.getType()];
            outTiming = Math.min(outTiming, 24);

            // 주차권 생성 및 저장
            Ticket ticket = Ticket.builder(shareLot.get(), user.get(), ticketCreateRequestDto).build();
            ticketRepo.save(ticket);
            return new TicketCreateResponseDto(
                    ticket.getTicket_id(),
                    ticket.getBuyer().getUser_id(),
                    ticket.getShareLot().getSha_id(),
                    ticket.getParking_region(),
                    ticket.getAddress(),
                    ticket.getType(),
                    ticket.getIn_timing(),
                    outTiming,
                    ticket.getCost()
            );
        }
        throw new IllegalStateException();

    }

    @Override
    public TicketSellerResponseDto ticketSellConfirm(Long userId, TicketSellerRequestDto ticketSellerRequestDto) {
        Optional<User> seller = userRepo.findById(userId);
        Optional<Ticket> currTicket = ticketRepo.findById(ticketSellerRequestDto.getTicketId());

        if (seller.isPresent() && currTicket.isPresent()) {
            currTicket.get().setSell_confirm(true);
            ticketRepo.save(currTicket.get());

            return new TicketSellerResponseDto(
                    currTicket.get().getTicket_id(),
                    ticketSellerRequestDto.getShaId(),
                    currTicket.get().getCost(),
                    currTicket.get().getParking_region(),
                    currTicket.get().getParking_date()
            );
        } throw new IllegalStateException();

    }

    @Override
    public PointConfirmResponseDto ticketBuyConfirm(Long userId, PointConfirmRequestDto pointConfirmRequestDto ) {
        Optional<User> buyer = userRepo.findById(userId);
        Optional<ShareLot> shareLot = shareLotRepo.findById(pointConfirmRequestDto.getShaId());
        Optional<User> seller = userRepo.findById(shareLot.get().getUser().getUser_id());
        Optional<Ticket> ticket = ticketRepo.findById(pointConfirmRequestDto.getTicketId());
        int ptLose = ticket.get().getCost();
        int inTiming = ticket.get().getIn_timing();
        int type = ticket.get().getType();

        // 출차시간 계산
        int[] typeToHour = new int[]{1, 3, 5, 24};
        int outTiming = inTiming + typeToHour[type];
        outTiming = Math.min(outTiming, 24);

        if (buyer.isPresent() && shareLot.isPresent() && seller.isPresent()) {

            /*
            구매자 측면
            */
            // 구매자 거래 내역 저장
            Transaction buy_transaction = new Transaction(
                    buyer.get(), shareLot.get().getSha_name(), 0, ptLose, shareLot.get().getSha_id()
                    );
            transactionRepo.save(buy_transaction);

            // 구매자 포인트 차감 User Entity 반영
            User buyerUser = buyer.get();
            buyerUser.subtractPoint(ptLose);
            userRepo.save(buyerUser);

            // 구매 확정 처리
            Optional<Ticket> userTicket = ticketRepo.findById(pointConfirmRequestDto.getTicketId());
            if (userTicket.isPresent()) {
                userTicket.get().setBuy_confirm(true);
                ticketRepo.save(userTicket.get());
            }
            /*------------구매자 끝--------------*/


             /*
            판매자 측면
            */
            // 판매자 포인트 증가 User Entity 반영
            User sellerUser = seller.get();
            sellerUser.addPoint(ptLose);
            userRepo.save(sellerUser);

            // 판매자 거래 내역 저장
            Transaction sell_transaction = new Transaction(
                    seller.get(), shareLot.get().getSha_name(), ptLose, 0, shareLot.get().getSha_id()
            );
            transactionRepo.save(sell_transaction);
            /*-----------판매자 끝-----------*/

            // DTO return
            return new PointConfirmResponseDto(
                    buy_transaction.getCredit_id(),
                    ptLose,
                    buyerUser.getPt_has(),
                    shareLot.get().getSha_name(),
                    shareLot.get().getSha_id(),
                    buy_transaction.getTransaction_date(),
                    type,
                    inTiming,
                    outTiming
                    );
        }
        throw new SaveException("Unable to Save");
    }
}
