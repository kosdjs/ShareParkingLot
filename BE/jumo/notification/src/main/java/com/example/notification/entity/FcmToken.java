package com.example.notification.entity;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@RedisHash()
public class FcmToken {

    @Id
    private Long token_id;
    private String user_id;
    private String token;
}
