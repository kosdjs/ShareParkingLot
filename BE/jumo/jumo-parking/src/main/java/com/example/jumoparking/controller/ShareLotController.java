package com.example.jumoparking.controller;

import com.example.domain.dto.ShareSaveDto;
import com.example.domain.entity.ShareLot;
import com.example.jumoparking.service.ShareLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shareLot")
@RequiredArgsConstructor
public class ShareLotController {

    private final ShareLotService shareLotService;
    @PostMapping("/save")
    public boolean createShareLot(@Validated @RequestBody ShareSaveDto saveDto){
        return shareLotService.saveShareLot(saveDto);
    }


}
