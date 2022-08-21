package com.liviasantos.lab.rabbitmq.notification.gateway.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private String code;
    private String store;
    private Long totalValueInCents;
}
