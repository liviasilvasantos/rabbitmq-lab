package com.liviasantos.lab.rabbitmq.cashback.config.rabbit;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rabbitmq")
@Getter
@Setter
public class RabbitMQProperties {

    private String routingKey;
    private String fanoutExchange;
    private String fanoutExchangeDlx;
    private String queueCashback;
    private String queueCashbackDlq;
    private String queueCashbackDlqParkingLot;

}
