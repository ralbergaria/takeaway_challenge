package com.takeaway.employee.domain.ports;

import com.takeaway.employee.domain.model.Employee;

public interface EmployeeMessageBroker {
    void sendMessage(Employee employee, StatusMessage statusMessage);
}
