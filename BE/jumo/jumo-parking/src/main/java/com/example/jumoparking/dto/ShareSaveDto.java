package com.example.jumoparking.dto;


import com.example.domainparking.entity.ParkingLot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareSaveDto {

    private int shaType;
    private String shaName;
    private String jibun;
    private String road;
    private int shaField;
    private int shaFee;
    private String shaProp;
    private float latitude;
    private float longitude;

    @Builder
    public ShareSaveDto(ParkingLot parkingLot){
        this.lat = parkingLot.getLatitude();
        this.lng = parkingLot.getLongitude();
        this.parkType = 0;
        this.feeBasic = parkingLot.getPer_basic() * 6;
        this.clusteringCnt = 0;
    }
}
