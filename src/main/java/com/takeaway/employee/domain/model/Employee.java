package com.takeaway.employee.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
public class Employee {
    private String id;
    private String eMail;
    private String fullName;
    private LocalDate birthday;
    private Set<Hobby> hobbies;
}