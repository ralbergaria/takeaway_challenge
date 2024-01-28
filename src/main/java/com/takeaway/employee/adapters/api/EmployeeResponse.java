package com.takeaway.employee.adapters.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.takeaway.employee.domain.model.Employee;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
class EmployeeResponse {
    private String id;
    private String email;
    private String fullName;
    private String birthday;
    private Set<HobbyResponse> hobbies;

    static EmployeeResponse fromDomain(Employee employee) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();
        return EmployeeResponse.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .fullName(employee.getFullName())
                .birthday(employee.getBirthday().format(formatter))
                .hobbies(employee.getHobbies().stream().map(HobbyResponse::fromDomain).collect(Collectors.toSet()))
                .build();
    }
}