package com.takeaway.employee.adapters.employeedb;

import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.ports.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public Employee create(Employee employee) {
        return null;
    }

    @Override
    public Employee update(Employee employee) {
        return null;
    }

    @Override
    public Employee delete(Employee employee) {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public Employee get(String uuid) {
        return null;
    }
}
