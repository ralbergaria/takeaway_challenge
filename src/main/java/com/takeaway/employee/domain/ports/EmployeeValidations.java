package com.takeaway.employee.domain.ports;

import com.takeaway.employee.domain.exceptions.EntityNotFoundException;
import com.takeaway.employee.domain.exceptions.UniqueEmailException;
import com.takeaway.employee.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
class EmployeeValidations {
    private final EmployeeRepository employeeRepository;

    Employee validateEmployeeExists(String id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + id + "not found."));
    }
    void validateEmailAlreadyExists(String email) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
        if(employeeOptional.isPresent()) {
            throw new UniqueEmailException("Email " + email + " already exists.");
        }
    }

}
