package com.takeaway.employee.adapters.messagepublisher.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitPublisherConfig {
    @Value("${message-broker.notification-queue.employee-notification-exchange}")
    private String employeeExchange;

    @Value("${message-broker.notification-queue.employee-notification-routing-key}")
    private String employeeRoutingKey;
    @Bean
    public DirectExchange employeeExchange() {
        return new DirectExchange(employeeExchange);
    }

    @Bean
    public Queue employeeQueueNotification() {
        return QueueBuilder.durable("only-to-test-notifications").build();
    }

    @Bean
    public Binding bindingPublisher() {
        return BindingBuilder.bind(employeeQueueNotification()).to(employeeExchange()).with(employeeRoutingKey);
    }
}
