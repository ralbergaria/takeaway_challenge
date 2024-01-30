package com.takeaway.employee.adapters.messageconsumer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class RabbitConsumer {
    private final EmployeeConsumer employeeConsumer;
    @RabbitListener(queues = "${message-broker.event-queue.employee-event-queue}")
    public void handleMessage(String message) {
        employeeConsumer.handleMessage(message);
    }
}
