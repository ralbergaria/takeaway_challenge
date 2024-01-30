package com.takeaway.employee.domain.ports;

import com.takeaway.employee.domain.exceptions.EntityNotFoundException;
import com.takeaway.employee.domain.exceptions.UniqueEmailException;
import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.model.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMessagePublisher employeeMessagePublisher;
    public Employee create(Employee employee) {
        validateEmailAlreadyExists(employee.getEmail());
        Employee employeeReturn = employeeRepository.save(employee);
        employeeMessagePublisher.sendMessage(employee, StatusMessage.CREATE);
        return employeeReturn;
    }
    public Employee update(Employee employee) {
        Employee existsEmployee = validateEmployeeExists(employee.getId());
        if(!existsEmployee.getEmail().equals(employee.getEmail())) {
            validateEmailAlreadyExists(employee.getEmail());
        }
        Employee employeeReturn = employeeRepository.save(employee);
        employeeMessagePublisher.sendMessage(employee, StatusMessage.UPDATE);
        return employeeReturn;
    }

    public void delete(String id) {
        Employee employee = validateEmployeeExists(id);
        employeeRepository.delete(employee);
        employeeMessagePublisher.sendMessage(employee, StatusMessage.DELETE);
    }

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Employee getById(String id) {
        return validateEmployeeExists(id);
    }

    protected Employee validateEmployeeExists(String id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + id + " not found."));
    }
    protected void validateEmailAlreadyExists(String email) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
        if(employeeOptional.isPresent()) {
            throw new UniqueEmailException("Email " + email + " already exists.");
        }
    }
}
