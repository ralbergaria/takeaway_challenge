package com.takeaway.employee.adapters.messageconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.employee.domain.ports.EmployeeEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
class EmployeeConsumer {
    private final ObjectMapper objectMapper;
    private final EmployeeEventService employeeEventService;
    public void handleMessage(String message) {
        try {
            EmployeeEvent employeeEvent = objectMapper.readValue(message, EmployeeEvent.class);
            employeeEventService.applyDeleteEvent(employeeEvent.toDomain());
        } catch (JsonProcessingException e) {
            log.error("Error to convert EmployeeEvent message " + message, e);
        }
    }
}
