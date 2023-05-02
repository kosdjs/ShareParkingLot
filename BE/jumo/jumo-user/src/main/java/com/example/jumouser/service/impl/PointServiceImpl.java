package com.example.jumouser.service.impl;

import com.example.domain.dto.point.request.PointConfirmRequestDto;
import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.request.TicketSellerRequestDto;
import com.example.domain.dto.point.response.*;
import com.example.domain.entity.*;
import com.example.domain.etc.OutTiming;
import com.example.domain.repo.ShareLotRepo;
import com.example.domain.repo.TicketRepo;
import com.example.domain.repo.TransactionRepo;
import com.example.domain.repo.UserRepo;
import com.example.error.exception.SaveException;
import com.example.jumouser.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;
    private final ShareLotRepo shareLotRepo;
    private final TicketRepo ticketRepo;
    private final OutTiming outTiming;

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
            int outTime = outTiming.OutTimingMethod(ticketCreateRequestDto.getInTiming(), ticketCreateRequestDto.getType());

            // 주차권 생성 및 저장
            Ticket ticket = Ticket.builder(shareLot.get(), user.get(), ticketCreateRequestDto).build();
            ticketRepo.save(ticket);
            return new TicketCreateResponseDto(
                    ticket.getTicket_id(),
                    ticket.getBuyer().getName(),
                    ticket.getCar_number(),
                    ticket.getShareLot().getSha_id(),
                    ticket.getParkingRegion(),
                    ticket.getAddress(),
                    ticket.getType(),
                    ticket.getIn_timing(),
                    outTime,
                    ticket.getCost()
            );
        }
        throw new IllegalStateException();

    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketListResponseDto> getBoughtTicketList(Long userId) {
        Optional<User> buyer = userRepo.findById(userId);
        if (buyer.isPresent()) {
            List<Ticket> tickets = ticketRepo.findAllByBuyer(buyer.get());
            return tickets.stream().map(t -> new TicketListResponseDto(outTiming, t)).collect(Collectors.toList());
        }
        throw new IllegalStateException();
    }

    @Override
    public List<TicketListResponseDto> getSoldTicketList(Long shaId) {
        Optional<ShareLot> shareLot = shareLotRepo.findById(shaId);

        if (shareLot.isPresent()) {
            List<Ticket> tickets = ticketRepo.findAllByShareLot(shareLot.get());
            return tickets.stream().map(t -> new TicketListResponseDto(outTiming, t)).collect(Collectors.toList());
        }
        throw new IllegalStateException();
    }

    @Override
    @Transactional(readOnly = true)
    public TicketDetailResponseDto getTicket(Long ticketId) {
        Optional<Ticket> currTicket = ticketRepo.findById(ticketId);

        if (currTicket.isPresent()) {
            Optional<ShareLot> shareLot = shareLotRepo.findById(currTicket.get().getShareLot().getSha_id());

            int outTime = outTiming.OutTimingMethod(currTicket.get().getIn_timing(), currTicket.get().getType());
            List<Image> images = shareLot.get().getImages();

            return new TicketDetailResponseDto(
                    currTicket.get().getTicket_id(),
                    currTicket.get().getParkingRegion(),
                    currTicket.get().getBuyer().getName(),
                    currTicket.get().getCar_number(),
                    currTicket.get().getParking_date(),
                    currTicket.get().getType(),
                    currTicket.get().getIn_timing(),
                    outTime,
                    currTicket.get().getCost(),
                    images,
                    currTicket.get().isSell_confirm(),
                    currTicket.get().isBuy_confirm()
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
                    currTicket.get().getParkingRegion(),
                    currTicket.get().getParking_date(),
                    currTicket.get().isSell_confirm()
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
        int outTime = outTiming.OutTimingMethod(inTiming, type);

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
            Optional<Ticket> buyerTicket = ticketRepo.findById(pointConfirmRequestDto.getTicketId());
            if (buyerTicket.isPresent()) {
                buyerTicket.get().setBuy_confirm(true);
                ticketRepo.save(buyerTicket.get());
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
                    outTime
                    );
        }
        throw new SaveException("Unable to Save");
    }
}
