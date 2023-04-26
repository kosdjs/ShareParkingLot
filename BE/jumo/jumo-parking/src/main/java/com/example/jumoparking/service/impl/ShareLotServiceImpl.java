package com.example.jumoparking.service.impl;

import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;
import com.example.domain.dto.ShareSaveDto;
import com.example.domain.entity.ParkingLot;
import com.example.domain.entity.ShareLot;
import com.example.domain.repo.ParkingLotRepo;
import com.example.domain.repo.ShareLotRepo;
import com.example.jumoparking.service.ShareLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShareLotServiceImpl implements ShareLotService {

    private final ShareLotRepo shareLotRepo;
    @Override
    public boolean saveShareLot(ShareSaveDto shareSaveDto) {

        ShareLot shareLot = ShareLot.builder(shareSaveDto).build();

        shareLot = shareLotRepo.save(shareLot);

        if (shareLot == null){

            return false;
        }
        return true;
    }

    @Override
    public void deleteShareLot(Long sha_id) {
        Optional<ShareLot> shareLot = shareLotRepo.findById(sha_id);
        if (shareLot.isPresent()){
            shareLotRepo.delete(shareLot.get());
        }
    }

    @Override
    public List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto) {
        List<ShareLot> shareLots = shareLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());

        return shareLots.stream().map(shareLot -> new ParkingListDto(shareLot)).collect(Collectors.toList());
    }


}
