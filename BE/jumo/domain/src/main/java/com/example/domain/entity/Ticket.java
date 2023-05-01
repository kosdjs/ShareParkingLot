package com.example.domain.entity;

import javax.persistence.*;

import com.example.domain.dto.point.request.TicketCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(builderMethodName = "TicketBuilder")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticket_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sha_id")
    private ShareLot shareLot;

    @Column(name = "seller_id")
    private Long seller_id;

    @Column(name = "parking_region")
    private String parking_region;

    private String address;

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

    public static TicketBuilder builder(ShareLot shareLot, User buyer, TicketCreateRequestDto ticketCreateRequestDto){
        int[] typeToHour = new int[]{2, 6, 10, 48};
        int cost = typeToHour[ticketCreateRequestDto.getType()]*shareLot.getSha_fee();

        return TicketBuilder()
                .shareLot(shareLot)
                .buyer(buyer)
                .seller_id(shareLot.getUser().getUser_id())
                .parking_region(shareLot.getSha_name())
                .address(shareLot.getSha_road())
                .type(ticketCreateRequestDto.getType())
                .cost(cost)
                .in_timing(ticketCreateRequestDto.getInTiming());
    }

    public void setBuy_confirm(boolean buyConfirm) {
        this.buy_confirm = buyConfirm;
    }

    public void setSell_confirm(boolean sellConfirm) {
        this.sell_confirm = sellConfirm;
    }

}
