package com.liviasantos.lab.rabbitmq.cashback.gateway.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCreatedListener {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void listener(final String message){
        log.info("mensagem recebida {}", message);
    }
}
