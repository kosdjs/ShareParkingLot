package com.example.jumoparking.controller;

import com.example.domain.dto.CarInfoDto;
import com.example.domain.dto.CarSaveDto;
import com.example.jumoparking.service.CarInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carInfo")
@RequiredArgsConstructor
public class CarInfoController {

    private final CarInfoService carInfoService;
    @PostMapping("/save")
    public void saveCar(@RequestBody CarSaveDto carSaveDto, @RequestParam Long userId){
        carInfoService.saveCarInfo(carSaveDto, userId);
    }

    @GetMapping("/list")
    public List<CarInfoDto> listCar(@RequestParam Long userId){
        return carInfoService.getListCarInfo(userId);
    }

    @GetMapping("/checkRep")
    public boolean checkRep(@RequestParam Long carId, @RequestParam Long userId){
        return carInfoService.checkRep(carId, userId);
    }

}
