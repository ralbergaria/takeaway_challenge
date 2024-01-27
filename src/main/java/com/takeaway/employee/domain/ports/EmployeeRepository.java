package com.takeaway.employee.domain.ports;

import com.takeaway.employee.domain.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Employee save(Employee employee);

    void delete(Employee employee);

    List<Employee> findAll();

    Optional<Employee> findById(String uuid);
    Optional<Employee> findByEmail(String uuid);
}