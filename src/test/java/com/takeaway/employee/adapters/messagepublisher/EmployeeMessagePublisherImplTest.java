package com.takeaway.employee.adapters.messagepublisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.model.Hobby;
import com.takeaway.employee.domain.model.StatusMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeMessagePublisherImplTest {
    @Mock
    private  RabbitPublisher rabbitPublisher;

    private EmployeeMessagePublisherImpl employeeMessagePublisher;
    @Test
    void testSendMessage() throws Exception {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        employeeMessagePublisher = new EmployeeMessagePublisherImpl(rabbitPublisher, objectMapper);
        Employee employee = Employee
                .builder()
                .id("test")
                .fullName("testName")
                .email("testEmail")
                .birthday(LocalDate.of(1985,3,17))
                .hobbies(Set.of(Hobby.builder().id("hobbyId").description("hobbyDescription").build()))
                .build();
        EventMessage eventMessage = EventMessage.builder()
                .status(StatusMessage.CREATE.toString())
                .birthday("1985-03-17")
                .id(employee.getId())
                .email(employee.getEmail())
                .fullName(employee.getFullName())
                .hobbyIds(employee.getHobbies().stream().map(Hobby::getId).collect(Collectors.toSet()))
                .build();
        String message = objectMapper.writeValueAsString(eventMessage);

        // When
        employeeMessagePublisher.sendMessage(employee, StatusMessage.CREATE);

        // Then
        verify(rabbitPublisher).sendMessage(message);
    }
}
