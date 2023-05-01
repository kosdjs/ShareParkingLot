package com.example.domain.entity;


import javax.persistence.*;
import com.example.domain.dto.user.SignUpRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String name;
    private String type;
    private String phone;
    private String email;
    private String password;
    private String fcm_token;
    private String profile_img;
    @Column(columnDefinition = "int default 0")
    private int pt_has;

    public User(SignUpRequestDto requestDto){
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.type = requestDto.getType();
        this.phone = requestDto.getPhone();
        this.profile_img = requestDto.getProfile_image();
        this.password=requestDto.getPassword();

    }

    public void addPoint(int point){
        this.pt_has += point;
    }

    public void subtractPoint(int point) {
        this.pt_has -= point;
    }

    @OneToMany(mappedBy = "car_id", cascade = CascadeType.ALL)
    private List<CarInfo> carInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "ticket_id", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "credit_id")
    private List<Transaction> transactions = new ArrayList<>();
}
