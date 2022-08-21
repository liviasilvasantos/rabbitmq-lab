package com.liviasantos.lab.rabbitmq.notification.gateway.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCreatedListener {

    @RabbitListener(queues = "${rabbitmq.queue-notification}")
    public void listener(final OrderCreatedEvent event){
        log.info("evento recebido {}", event);
    }
}
