package com.liviasantos.lab.rabbitmq.order.gateway.http.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class OrderJson {

    private String code;
    private String store;
    private String sku;
    private Long totalValueInCents;

}
