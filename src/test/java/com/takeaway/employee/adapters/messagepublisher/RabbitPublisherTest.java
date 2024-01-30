package com.takeaway.employee.adapters.messagepublisher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitPublisherTest {

    @Value("spring.rabbitmq.exchanges.employeeExchange")
    private String exchangeName;

    @Value("spring.rabbitmq.bindings.routing-key")
    private String routingKey;

    private RabbitPublisher rabbitPublisher;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    void testSendMessage() {
        // Given
        rabbitPublisher = new RabbitPublisher(rabbitTemplate);
        String message = "Test Message";

        // When
        rabbitPublisher.sendMessage(message);

        // Then
        verify(rabbitTemplate).convertAndSend(exchangeName, routingKey, message);
    }
}
