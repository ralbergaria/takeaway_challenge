package com.takeaway.employee.adapters.api;

import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.model.Hobby;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
@Data
@NoArgsConstructor
class EmployeeCreateRequest {
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    private String fullName;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format. Use yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthday;
    private List<String> hobbiesIds;

    static Employee toDomain(EmployeeCreateRequest employeeCreateRequest) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();
        return Employee.builder()
                .email(employeeCreateRequest.getEmail())
                .fullName(employeeCreateRequest.getFullName())
                .birthday(LocalDate.parse(employeeCreateRequest.getBirthday(), formatter))
                .hobbies(employeeCreateRequest.getHobbiesIds().stream().map(id -> Hobby.builder().id(id).build()).collect(Collectors.toSet()))
                .build();
    }
}
