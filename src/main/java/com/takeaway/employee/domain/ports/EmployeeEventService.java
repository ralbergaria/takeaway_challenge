package com.takeaway.employee.domain.ports;

import com.takeaway.employee.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeEventService {
    private final EmployeeRepository employeeRepository;

    @Transactional
    public void applyDeleteEvent(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        employeeOptional.ifPresentOrElse(
                employeeRepository::delete,
                () -> log.warn("Employee " + employee.getId() + " to be deleted not found")
        );
    }
}
