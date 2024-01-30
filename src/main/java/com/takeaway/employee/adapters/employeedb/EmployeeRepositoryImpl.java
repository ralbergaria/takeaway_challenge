package com.takeaway.employee.adapters.employeedb;

import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.ports.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
class EmployeeRepositoryImpl implements EmployeeRepository {
    private final EmployeeJPARepository employeeJPARepository;
    @Override
    public Employee save(Employee employee) {
        EmployeeEntity employeeEntity = EmployeeEntity.fromDomain(employee);
        return employeeJPARepository.save(employeeEntity).toDomain();
    }

    @Override
    public void delete(Employee employee) {
        EmployeeEntity employeeEntity = EmployeeEntity.fromDomain(employee);
        employeeJPARepository.delete(employeeEntity);
    }

    @Override
    public List<Employee> findAll() {
        return employeeJPARepository.findAll().stream().map(EmployeeEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> findById(String uuid) {
        Optional<EmployeeEntity> optionalEmployee = employeeJPARepository.findById(UUID.fromString(uuid));
        return optionalEmployee.map(EmployeeEntity::toDomain);
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        Optional<EmployeeEntity> optionalEmployee = employeeJPARepository.findByEmail(email);
        return optionalEmployee.map(EmployeeEntity::toDomain);
    }
}
