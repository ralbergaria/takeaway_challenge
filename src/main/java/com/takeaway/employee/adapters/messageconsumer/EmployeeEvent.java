package com.takeaway.employee.adapters.messageconsumer;

import com.takeaway.employee.domain.model.Employee;
import lombok.Data;

import java.time.LocalDateTime;

@Data
class EmployeeEvent {
    private String eventId;
    private String employeeId;
    private LocalDateTime terminationRequestedAt;
    private LocalDateTime lastDayAtWork;
    private String reason;

    Employee toDomain() {
        return Employee.builder().id(this.getEmployeeId()).build();
    }
}
