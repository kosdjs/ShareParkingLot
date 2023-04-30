package com.example.jumoparking.controller;

import com.example.domain.dto.DaySaveDto;
import com.example.domain.dto.ParkingDetailDto;
import com.example.domain.dto.ShareSaveDto;
import com.example.domain.entity.ShareLot;
import com.example.jumoparking.service.DayDataService;
import com.example.jumoparking.service.ShareLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/shareLot")
@RequiredArgsConstructor
public class ShareLotController {

    private final ShareLotService shareLotService;
    private final DayDataService dayDataService;
    @PostMapping("/save")
    public Long createShareLot(@Validated ShareSaveDto saveDto, @RequestPart List<MultipartFile> files) throws  Exception{
        return shareLotService.saveShareLot(saveDto, files);
    }

    @PostMapping("/saveDay")
    public boolean saveDay(@RequestParam Long lotId, @RequestBody List<DaySaveDto> daySaveDtos){
        for (DaySaveDto daySaveDto : daySaveDtos){
            dayDataService.saveDayData(daySaveDto, lotId);
        }
        return true;
    }

    @PostMapping("/updateDay")
    public void updateDay(@RequestParam Long lotId, @RequestBody List<DaySaveDto> daySaveDtos){
        dayDataService.deleteDayData(lotId);
        for(DaySaveDto daySaveDto : daySaveDtos){
            dayDataService.saveDayData(daySaveDto, lotId);
        }
    }

    @DeleteMapping("/delete")
    public void deleteShareLot(@RequestParam Long lotId){
        shareLotService.deleteShareLot(lotId);
    }

    @GetMapping("/detail")
    public ParkingDetailDto shareLotDetail(@RequestParam Long lotId){ return shareLotService.getDetail(lotId);}

}
