package com.example.jumoparking.controller;

import com.example.domain.dto.ParkingBottomListDto;
import com.example.domain.dto.ParkingDetailDto;
import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;
import com.example.jumoparking.service.ParkingLotService;
import com.example.jumoparking.service.ShareLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parkingLot")
@RequiredArgsConstructor
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    private final ShareLotService shareLotService;

    @PostMapping("/list")
    public List<ParkingListDto> getParkingList(@Validated @RequestBody ParkingInDto parkingInDto){
        return parkingLotService.getListOfPoint(parkingInDto);
    }

    //바텀 리스트

    @PostMapping("/list/bottom")
    public List<ParkingBottomListDto> getParkingBottomList(@Validated @RequestBody ParkingInDto parkingInDto){
        return parkingLotService.getBottomListOfPoint(parkingInDto);
    }
    @GetMapping("/detail")
    public ParkingDetailDto shareLotDetail(@RequestParam Long lotId){ return parkingLotService.getDetail(lotId);}

    @GetMapping("/favorite")
    public boolean checkFavorite(@RequestParam Long lotId, @RequestParam Long userId, @RequestParam int parkType){
        if(parkType == 1){
            return shareLotService.checkFavorite(userId, lotId);
        }
        else{
            return parkingLotService.checkFavorite(userId, lotId);
        }
    }

    @GetMapping("/favorite/list")
    public List<ParkingBottomListDto> getFavoriteList(@RequestParam Long userId){
        return parkingLotService.getFavoriteList(userId);
    }



}
