package com.takeaway.employee.adapters.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@ToString
@AllArgsConstructor
class EmployeeRequest {
    private String eMail;
    private String fullName;
    private LocalDate birthday;
    private List<String> hobbiesIds;
}
