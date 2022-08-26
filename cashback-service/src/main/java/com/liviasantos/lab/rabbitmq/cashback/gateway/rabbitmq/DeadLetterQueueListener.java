package com.liviasantos.lab.rabbitmq.cashback.gateway.rabbitmq;

import com.liviasantos.lab.rabbitmq.cashback.config.rabbit.RabbitMQProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeadLetterQueueListener {

    private final RabbitTemplate rabbitTemplate;
    private static final String RETRY_HEADER = "x-dlq-retry";
    private final RabbitMQProperties rabbitMQProperties;

    @RabbitListener(queues = "${rabbitmq.queue-cashback-dlq}")
    public void reprocess(final OrderCreatedEvent event, @Headers Map<String, Object> headers){
        Integer retryHeader = (Integer) headers.get(RETRY_HEADER);

        if(retryHeader == null) {
            retryHeader = 0;
        }

        log.info("reprocessando venda {}", event.getCode());

        if(retryHeader < 3){
            int tryCount = retryHeader + 1;

            Map<String, Object> updatedHeaders = new HashMap<>();
            updatedHeaders.put(RETRY_HEADER, tryCount);

            final MessagePostProcessor messagePostProcessor = message -> {
                MessageProperties messageProperties = message.getMessageProperties();
                updatedHeaders.forEach(messageProperties::setHeader);
                return message;
            };

            log.info("reenviando venda {} para a dlq", event.getCode());
            rabbitTemplate.convertAndSend(rabbitMQProperties.getQueueCashbackDlq(), event, messagePostProcessor);
        } else {
            //salvar em banco ou mandar para uma fila parking-lot
            log.info("reprocessamento falhou, enviando venda {} para parking lot", event.getCode());
            rabbitTemplate.convertAndSend(rabbitMQProperties.getQueueCashbackDlqParkingLot(), event);
        }

    }
}
