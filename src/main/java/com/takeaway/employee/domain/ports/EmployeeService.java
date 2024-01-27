package com.takeaway.employee.domain.ports;

import com.takeaway.employee.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeValidations employeeValidations;
    public Employee create(Employee employee) {
        employeeValidations.validateEmailAlreadyExists(employee.getEmail());
        return employeeRepository.save(employee);
    }
    public Employee update(Employee employee) {
        Employee existsEmployee = employeeValidations.validateEmployeeExists(employee.getId());
        if(!existsEmployee.getEmail().equals(employee.getEmail())) {
            employeeValidations.validateEmailAlreadyExists(employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    public void delete(Employee employee) {
        employeeValidations.validateEmployeeExists(employee.getId());
        employeeRepository.delete(employee);
    }

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Employee getById(String id) {
        return employeeValidations.validateEmployeeExists(id);
    }
}
