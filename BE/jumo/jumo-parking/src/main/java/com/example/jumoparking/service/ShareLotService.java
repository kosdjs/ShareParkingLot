package com.example.jumoparking.service;

import com.example.jumoparking.dto.ParkingInDto;
import com.example.jumoparking.dto.ParkingListDto;

import java.util.List;

public interface ShareLotService {
    List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto);

}
