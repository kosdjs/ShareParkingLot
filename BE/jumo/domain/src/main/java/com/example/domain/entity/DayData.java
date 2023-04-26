package com.example.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long day_id;

    @ManyToOne
    @JoinColumn(name = "sha_id")
    private ShareLot shareLot;

    private String day_str;

    private int day_start;

    private int day_end;

}
