package com.takeaway.employee.adapters.messagebroker;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitPublisher {
    @Value("spring.rabbitmq.exchanges.employeeExchange")
    private String exchangeName;

    @Value("spring.rabbitmq.bindings.routing-key")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
