package com.example.jumouser.service.impl;

import com.example.domain.dto.point.request.TicketCreateRequestDto;
import com.example.domain.dto.point.response.*;
import com.example.domain.entity.ShareLot;
import com.example.domain.entity.Ticket;
import com.example.domain.entity.Transaction;
import com.example.domain.entity.User;
import com.example.domain.etc.OutTiming;
import com.example.domain.repo.ShareLotRepo;
import com.example.domain.repo.TicketRepo;
import com.example.domain.repo.TransactionRepo;
import com.example.domain.repo.UserRepo;
import com.example.error.exception.InputException;
import com.example.error.exception.SaveException;
import com.example.jumouser.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertFalse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;
    private final ShareLotRepo shareLotRepo;
    private final TicketRepo ticketRepo;
    private final OutTiming outTiming;

    @Override
    @Transactional(readOnly = true)
    public TypeResponseDto getTypeAvailability(Long shaId, int time) {
        Optional<ShareLot> currShareLot = shareLotRepo.findById(shaId);
        if (currShareLot.isPresent()) {
            String date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // 해당하는 공유 주차장의 모든 당일 티켓 가져오기
            List<Ticket> existingTickets = ticketRepo.findAllByShareLotAndParkingDate(currShareLot.get(), date);

            // 먼저 발권한 티켓이 없다면 모든 시간 가능
            if (existingTickets.isEmpty()) {
                return new TypeResponseDto(true, true, true, true);
            } else {
                // 선행 티켓 존재 시 종일권 불가능
                boolean allDay = false;

                // 예약된 시간 배열 생성
                boolean[] occupied = new boolean[26];
                Arrays.fill(occupied, true);

                // 모든 선행 티켓에서 예약된 시간 false 만들기
                for (Ticket ticket : existingTickets) {
                    int inTiming = ticket.getIn_timing();
                    int outTime = outTiming.OutTimingMethod(inTiming, ticket.getType());

                    for (int i = inTiming; i < outTime; i++) {
                        occupied[i] = false;
                    }
                }
                // 예약된 시간과 겹치면 무조건 불가능
                if (!occupied[time]) {
                    return new TypeResponseDto(false, false, false, false);
                } else {
                    // 다른 경우 1시간 권 무조건 가능
                    boolean oneHour = true;
                    boolean threeHours = true;
                    boolean fiveHours = true;
                    for (int j = 1; j < 6; j++) {
                        // 3시간 이내 주차불가 시 3시간 5시간 권 사용 불가
                        if (!occupied[time + j]) {
                            if (j < 4) {
                                threeHours = false;
                                fiveHours = false;
                                break;
                            }
                            // 5시간 이내 주차불가 시 5시간 권 사용 불가
                            else {
                                fiveHours = false;
                            }
                        }
                    }
                    return new TypeResponseDto(oneHour, threeHours, fiveHours, allDay);
                }
            }
        }
        return null;
    }

    @Override
    public TicketCreateResponseDto ticketCreate(Long userId, TicketCreateRequestDto ticketCreateRequestDto) {

        Optional<User> user = userRepo.findById(userId);
        Optional<ShareLot> shareLot = shareLotRepo.findById(ticketCreateRequestDto.getShaId());

        if (user.isPresent() && shareLot.isPresent()) {
            int[] typeToHour = new int[]{2, 6, 10, 48};
            int cost = typeToHour[ticketCreateRequestDto.getType()]*shareLot.get().getSha_fee();
            
            // 보유 포인트가 결제 금액 이상일 때 구매
            if(user.get().getPtHas() >= cost) {
                int outTime = outTiming.OutTimingMethod(ticketCreateRequestDto.getInTiming(), ticketCreateRequestDto.getType());

                // 주차권 생성 및 저장
                Ticket ticket = Ticket.builder(shareLot.get(), user.get(), ticketCreateRequestDto).build();
                ticketRepo.save(ticket);
                return new TicketCreateResponseDto(outTiming, ticket);
            } else throw new SaveException("Point is NOT enough to buy ticket");
        }
        throw new IllegalStateException();

    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketBuyerResponseDto> getBoughtTicketList(Long userId) {
        Optional<User> buyer = userRepo.findById(userId);
        String date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (buyer.isPresent()) {
            List<Ticket> tickets = ticketRepo.findAllByBuyerAndParkingDate(buyer.get(), date);
            return tickets.stream().map(t -> new TicketBuyerResponseDto(outTiming, t)).collect(Collectors.toList());
        }
        throw new IllegalStateException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketSellerResponseDto> getSoldTicketList(Long userId, Long shaId) {
        Optional<ShareLot> shareLot = shareLotRepo.findById(shaId);
        Optional<User> seller = userRepo.findById(userId);
        String date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (shareLot.isPresent() && seller.isPresent()) {
            if (shareLot.get().getUser() == seller.get()) {
                List<Ticket> tickets = ticketRepo.findAllByShareLotAndSellerIdAndParkingDate(shareLot.get(), userId, date);
                return tickets.stream().map(t -> new TicketSellerResponseDto(outTiming, t)).collect(Collectors.toList());
            } else throw new InputException("this User is NOT seller of this ParkingLot");
        }
        throw new IllegalStateException();
    }

    @Override
    @Transactional(readOnly = true)
    public TicketDetailResponseDto getTicket(Long ticketId) {
        Optional<Ticket> currTicket = ticketRepo.findById(ticketId);

        if (currTicket.isPresent()) {
            return new TicketDetailResponseDto(outTiming, currTicket.get());
        }
        throw new IllegalStateException();
    }

    @Override
    public TicketSellConfirmResponseDto ticketSellConfirm(Long userId, Long ticketId) {
        Optional<User> seller = userRepo.findById(userId);
        Optional<Ticket> currTicket = ticketRepo.findById(ticketId);

        if (seller.isPresent() && currTicket.isPresent()) {
            if(seller.get().getUserId() == currTicket.get().getSellerId()) {
                currTicket.get().setSell_confirm(true);
                ticketRepo.save(currTicket.get());
                return new TicketSellConfirmResponseDto(currTicket.get());
            } else throw new InputException("this user is not seller");


        } throw new IllegalStateException();

    }

    @Override
    public TicketBuyConfirmResponseDto ticketBuyConfirm(Long userId, Long ticketId ) {
        Optional<User> buyer = userRepo.findById(userId);
        Optional<Ticket> ticket = ticketRepo.findById(ticketId);

        Optional<ShareLot> shareLot = shareLotRepo.findById(ticket.get().getShareLot().getShaId());
        Optional<User> seller = userRepo.findById(shareLot.get().getUser().getUserId());

        int ptLose = ticket.get().getCost();
        int inTiming = ticket.get().getIn_timing();
        int type = ticket.get().getType();
        int outTime = outTiming.OutTimingMethod(inTiming, type);

        if (buyer.isPresent() && shareLot.isPresent() && seller.isPresent()) {
            // 요청 userId가 구매자라면
            if (buyer.get() == ticket.get().getBuyer()) {
                /*
            구매자 측면
            */
                // 구매자 거래 내역 저장
                Transaction buy_transaction = new Transaction(
                        buyer.get(), shareLot.get().getSha_name(), 0, ptLose, shareLot.get().getShaId()
                );
                transactionRepo.save(buy_transaction);

                // 구매자 포인트 차감 User Entity 반영
                User buyerUser = buyer.get();
                buyerUser.subtractPoint(ptLose);
                userRepo.save(buyerUser);

                // 구매 확정 처리
                Optional<Ticket> buyerTicket = ticketRepo.findById(ticketId);
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
                        seller.get(), shareLot.get().getSha_name(), ptLose, 0, shareLot.get().getShaId()
                );
                transactionRepo.save(sell_transaction);
                /*-----------판매자 끝-----------*/

                // DTO return
                return new TicketBuyConfirmResponseDto(
                        buy_transaction.getCredit_id(),
                        ptLose,
                        buyerUser.getPtHas(),
                        shareLot.get().getSha_name(),
                        shareLot.get().getShaId(),
                        buy_transaction.getTransactionDate(),
                        type,
                        inTiming,
                        outTime
                );
            }
        }
        throw new SaveException("Unable to Save");
    }
}
