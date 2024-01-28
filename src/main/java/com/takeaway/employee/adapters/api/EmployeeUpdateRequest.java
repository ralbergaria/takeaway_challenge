package com.takeaway.employee.adapters.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.model.Hobby;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class EmployeeUpdateRequest extends EmployeeCreateRequest{
    @NotNull
    @NotEmpty
    private String id;

    static Employee toDomain(EmployeeUpdateRequest employeeUpdateRequest) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();
        return Employee.builder()
                .id(employeeUpdateRequest.getId())
                .email(employeeUpdateRequest.getEmail())
                .fullName(employeeUpdateRequest.getFullName())
                .birthday(LocalDate.parse(employeeUpdateRequest.getBirthday(), formatter))
                .hobbies(employeeUpdateRequest.getHobbiesIds().stream().map(id -> Hobby.builder().id(id).build()).collect(Collectors.toSet()))
                .build();
    }
}