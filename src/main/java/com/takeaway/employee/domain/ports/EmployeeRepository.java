package com.takeaway.employee.domain.ports;

import com.takeaway.employee.domain.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee create(Employee employee);

    Employee update(Employee employee);

    Employee delete(Employee employee);

    List<Employee> getAll();

    Employee get(String uuid);
}