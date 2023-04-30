package com.example.jumoparking.service;



import com.example.domain.dto.ParkingDetailDto;
import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;
import com.example.domain.dto.ShareSaveDto;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShareLotService {
    Long saveShareLot(ShareSaveDto shareSaveDto, @RequestPart List<MultipartFile> files) throws IOException;

    void deleteShareLot(Long sha_id);

    List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto);

    ParkingDetailDto getDetail(Long parkId);

    boolean checkFavorite(Long userId, Long lotId);

}
