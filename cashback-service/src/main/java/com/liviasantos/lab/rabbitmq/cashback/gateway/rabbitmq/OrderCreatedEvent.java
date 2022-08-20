package com.liviasantos.lab.rabbitmq.cashback.gateway.rabbitmq;

import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private String code;
    private String store;
    private Long totalValueInCents;
}
