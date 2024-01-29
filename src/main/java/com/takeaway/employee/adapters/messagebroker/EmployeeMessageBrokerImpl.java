package com.takeaway.employee.adapters.messagebroker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.ports.EmployeeMessageBroker;
import com.takeaway.employee.domain.ports.StatusMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
class EmployeeMessageBrokerImpl implements EmployeeMessageBroker {
    private final RabbitPublisher rabbitPublisher;
    private final ObjectMapper objectMapper;
    @Override
    public void sendMessage(Employee employee, StatusMessage statusMessage) {
        try {
            rabbitPublisher.sendMessage(objectMapper.writeValueAsString(EventMessage.fromDomain(employee, statusMessage)));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(),e);
        }
    }
}