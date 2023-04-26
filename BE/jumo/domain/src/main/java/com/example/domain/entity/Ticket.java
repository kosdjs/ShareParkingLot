package com.example.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticket_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sha_id")
    private ShareLot shareLot;

    private Long user_id2;

    private String parking_region;

    private String address;

    private int open_timing;

    private int close_timing;

    private int type;

    private int cost;

    private int in_timing;

    private boolean validation;

    private String parking_date;

    private boolean buy_confirm;

    private boolean sell_confirm;
}
