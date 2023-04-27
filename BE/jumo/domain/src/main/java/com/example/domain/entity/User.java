package com.example.domain.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String social_id;
    private String phone;
    private String email;
    private String nickname;
    private String fcm_token;
    private String profile_img;
    private int pt_has;

    @OneToMany(mappedBy = "car_id", cascade = CascadeType.ALL)
    private List<CarInfo> carInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "ticket_id", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "credit_id")
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "favorite_id", cascade = CascadeType.ALL)
    private List<Favorite> favoriteList = new ArrayList<>();
}
