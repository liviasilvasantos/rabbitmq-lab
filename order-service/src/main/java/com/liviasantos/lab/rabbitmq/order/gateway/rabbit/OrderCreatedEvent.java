package com.liviasantos.lab.rabbitmq.order.gateway.rabbit;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@ToString
public class OrderCreatedEvent implements Serializable {
    private String code;
    private String store;
    private Long totalValueInCents;
}
