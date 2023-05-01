package com.example.domain.dto.user;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;


@Builder
@Data
public class LoginResponseDto {
    private Long user_id;
    private String name;
    private String type;
    private String social_id;
    private String phone;
    private String email;

    private String profile_img;
    private int ptHas;
}
