package com.liviasantos.lab.rabbitmq.order.gateway.http;

import com.liviasantos.lab.rabbitmq.order.config.rabbit.RabbitMQProperties;
import com.liviasantos.lab.rabbitmq.order.gateway.http.json.OrderJson;
import com.liviasantos.lab.rabbitmq.order.gateway.rabbit.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    @PostMapping
    public void createOrder(@RequestBody final OrderJson orderJson) {
        var orderCreatedEvent = buildOrderCreatedEvent(orderJson);
        log.info("creating order {}", orderCreatedEvent);

        rabbitTemplate.convertAndSend(rabbitMQProperties.getQueue(), orderCreatedEvent);
    }

    private static OrderCreatedEvent buildOrderCreatedEvent(OrderJson orderJson) {
        return OrderCreatedEvent.builder()
                .code(orderJson.getCode())
                .store(orderJson.getStore())
                .totalValueInCents(orderJson.getTotalValueInCents())
                .build();
    }
}
