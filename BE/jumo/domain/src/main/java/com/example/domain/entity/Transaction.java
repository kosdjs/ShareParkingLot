package com.example.domain.entity;

import javax.persistence.*;

import com.example.domain.dto.point.request.PointBuyRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Builder(builderMethodName = "TransactionBuilder")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Long credit_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "buy_date")
    private String buy_date;

    @Column(name = "lot_name")
    private String lot_name;

    @Column(name = "pt_get")
    private int pt_get;

    @Column(name = "pt_lose")
    private int pt_lose;

    @Builder
    public Transaction(User user, String name, int get, int lose)
    {
        this.user = user;
        this.buy_date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        this.lot_name = name;
        this.pt_get = get;
        this.pt_lose = lose;
    }

}
