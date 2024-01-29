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
    private final EmployeeMessageBroker employeeMessageBroker;
    public Employee create(Employee employee) {
        employeeValidations.validateEmailAlreadyExists(employee.getEmail());
        Employee employeeReturn = employeeRepository.save(employee);
        employeeMessageBroker.sendMessage(employee, StatusMessage.CREATE);
        return employeeReturn;
    }
    public Employee update(Employee employee) {
        Employee existsEmployee = employeeValidations.validateEmployeeExists(employee.getId());
        if(!existsEmployee.getEmail().equals(employee.getEmail())) {
            employeeValidations.validateEmailAlreadyExists(employee.getEmail());
        }
        Employee employeeReturn = employeeRepository.save(employee);
        employeeMessageBroker.sendMessage(employee, StatusMessage.UPDATE);
        return employeeReturn;
    }

    public void delete(String id) {
        Employee employee = employeeValidations.validateEmployeeExists(id);
        employeeRepository.delete(employee);
        employeeMessageBroker.sendMessage(employee, StatusMessage.DELETE);
    }

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Employee getById(String id) {
        return employeeValidations.validateEmployeeExists(id);
    }
}
