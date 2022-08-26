package com.liviasantos.lab.rabbitmq.cashback.config.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties rabbitMQProperties;

    @Bean
    public Queue queueCashback(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", rabbitMQProperties.getFanoutExchangeDlx());
//        args.put("x-dead-letter-routing-key", rabbitMQProperties.getQueueCashbackDlq());

        return new Queue(rabbitMQProperties.getQueueCashback(), true, false, false, args);
    }

    @Bean
    public Queue queueCashbackDlq(){
        return new Queue(rabbitMQProperties.getQueueCashbackDlq());
    }

    @Bean
    public Queue queueCashbackDlqParkingLot(){
        return new Queue(rabbitMQProperties.getQueueCashbackDlqParkingLot());
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(rabbitMQProperties.getFanoutExchange());
    }

    public FanoutExchange fanoutExchangeDlx(){
        return new FanoutExchange(rabbitMQProperties.getFanoutExchangeDlx());
    }

    @Bean
    public Binding binding(final Queue queueCashback, final FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queueCashback).to(fanoutExchange);
    }

    @Bean
    public Binding bindingDlq(final Queue queueCashbackDlq, final FanoutExchange fanoutExchangeDlx){
        return BindingBuilder.bind(queueCashbackDlq).to(fanoutExchangeDlx);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(
            RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory, final Jackson2JsonMessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}
