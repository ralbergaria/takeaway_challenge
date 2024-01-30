package com.takeaway.employee.domain.ports;

import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.model.StatusMessage;

public interface EmployeeMessagePublisher {
    void sendMessage(Employee employee, StatusMessage statusMessage);
}
