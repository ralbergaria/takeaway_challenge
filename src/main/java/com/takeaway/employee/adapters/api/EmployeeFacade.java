package com.takeaway.employee.adapters.api;

import com.takeaway.employee.domain.ports.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
class EmployeeFacade {
    private final EmployeeService employeeService;
    List<EmployeeResponse> getAll(){
        return employeeService.getAll().stream().map(EmployeeResponse::fromDomain).collect(Collectors.toList());
    }

    EmployeeResponse getById(String employeeId) {
        return EmployeeResponse.fromDomain(employeeService.getById(employeeId));
    }

    EmployeeResponse create(EmployeeCreateRequest employeeCreateRequest) {
        return EmployeeResponse.fromDomain(employeeService.create(employeeCreateRequest.toDomain()));
    }

    EmployeeResponse update(EmployeeUpdateRequest employeeUpdateRequest) {
        return EmployeeResponse.fromDomain(employeeService.update(EmployeeUpdateRequest.toDomain(employeeUpdateRequest)));
    }

    void delete (String id) {
        employeeService.delete(id);
    }
}
