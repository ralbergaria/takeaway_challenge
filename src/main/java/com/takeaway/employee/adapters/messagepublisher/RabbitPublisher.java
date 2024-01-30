package com.takeaway.employee.adapters.messagepublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RabbitPublisher {
    @Value("${message-broker.notification-queue.employee-notification-exchange}")
    private String exchangeName;

    @Value("${message-broker.notification-queue.employee-notification-routing-key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}