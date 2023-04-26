package com.example.domainuser.dto;

import com.example.domainparking.entity.ParkingLot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "게시물 리스트 응답DTO")
public class ParkingListDto {
    private float lat;
    private float lng;
    private int parkType;
    private int feeBasic;

    private int clusteringCnt;

    @Builder
    public ParkingListDto(ParkingLot parkingLot){
        this.lat = parkingLot.getLatitude();
        this.lng = parkingLot.getLongitude();
        this.parkType = 0;
        this.feeBasic = parkingLot.getPer_basic() * 6;
        this.clusteringCnt = 0;
    }
}
