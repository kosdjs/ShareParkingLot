package com.example.domain.entity;


import com.example.domain.dto.user.SignUpRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
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
    private int pt_has;


    public User(SignUpRequestDto requestDto){
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.type = requestDto.getType();
        this.phone = requestDto.getPhone();
        this.profile_img = requestDto.getProfile_image();
        this.password=requestDto.getPassword();

    }
}
