package com.example.jumoparking.service;



import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;
import com.example.domain.dto.ShareSaveDto;

import java.util.List;

public interface ShareLotService {
    boolean saveShareLot(ShareSaveDto shareSaveDto);

    void deleteShareLot(Long sha_id);

    List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto);

}
