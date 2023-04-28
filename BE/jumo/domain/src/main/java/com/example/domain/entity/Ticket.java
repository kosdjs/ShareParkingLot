package com.example.domain.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticket_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sha_id")
    private ShareLot shareLot;

    @Column(name = "user_id2")
    private Long user_id2;

    @Column(name = "parking_region")
    private String parking_region;

    private String address;

    @Column(name = "open_timing")
    private int open_timing;

    @Column(name = "close_timing")
    private int close_timing;

    private int type;

    private int cost;

    @Column(name = "in_timing")
    private int in_timing;

    @Column(columnDefinition = "boolean default true")
    private boolean validation;

    @Column(name = "parking_date")
    private String parking_date;

    @Column(name = "buy_confirm", columnDefinition = "boolean default false")
    private boolean buy_confirm;

    @Column(name="sell_confirm", columnDefinition = "boolean default false")
    private boolean sell_confirm;
}
