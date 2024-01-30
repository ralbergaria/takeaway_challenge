package com.takeaway.employee.adapters.messageconsumer.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConsumerConfig {
    @Value("${message-broker.event-queue.employee-event-queue}")
    private String queueName;

    @Value("${message-broker.event-queue.employee-event-dead-letter-exchange}")
    private String deadLetterExchangeName;

    @Value("${message-broker.event-queue.employee-event-dead-letter-queue}")
    private String deadLetterQueue;
    @Bean
    public Queue employeeQueueEvent() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchangeName);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(employeeQueueEvent()).to(deadLetterExchange()).with(deadLetterQueue);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
