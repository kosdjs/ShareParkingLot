package com.example.jumouser.service.impl;

import com.example.domain.dto.point.request.PointBuyRequestDto;
import com.example.domain.dto.point.response.PointBuyResponseDto;
import com.example.domain.dto.point.response.PointChargeResponseDto;
import com.example.domain.entity.ShareLot;
import com.example.domain.entity.Transaction;
import com.example.domain.entity.User;
import com.example.domain.repo.ShareLotRepo;
import com.example.domain.repo.TransactionRepo;
import com.example.domain.repo.UserRepo;
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

    @Override
    public PointChargeResponseDto chargePoint(Long userId, int ptCharge) {
        Optional<User> user = userRepo.findById(userId);

        if(user.isPresent()){
            User currUser = user.get();
            currUser.addPoint(ptCharge);
            userRepo.save(currUser);

            return new PointChargeResponseDto(
                    user.get().getUser_id(), user.get().getName(), ptCharge, user.get().getPt_has()
            );

        }
        return null;
    }

    @Override
    public PointBuyResponseDto consumePoint(Long userId, PointBuyRequestDto pointBuyRequestDto ) {
        Optional<User> user = userRepo.findById(userId);
        Optional<ShareLot> shareLot = shareLotRepo.findById(pointBuyRequestDto.getSha_id());

        if (user.isPresent() && shareLot.isPresent()) {
            Transaction buy_transaction = new Transaction(user.get(), shareLot.get().getSha_name(), 0, pointBuyRequestDto.getPt_lose());

            transactionRepo.save(buy_transaction);
        }
        return null;
    }
}
