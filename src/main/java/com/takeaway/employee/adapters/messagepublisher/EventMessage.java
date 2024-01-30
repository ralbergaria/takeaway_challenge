package com.takeaway.employee.adapters.messagepublisher;

import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.model.Hobby;
import com.takeaway.employee.domain.model.StatusMessage;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
class EventMessage {
    private String status;
    private String id;
    private String email;
    private String fullName;
    private String birthday;
    private Set<String> hobbyIds;

    static EventMessage fromDomain(Employee employee, StatusMessage statusMessage) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();
        return EventMessage.builder()
                .status(statusMessage.toString())
                .birthday(employee.getBirthday().format(formatter))
                .id(employee.getId())
                .email(employee.getEmail())
                .fullName(employee.getFullName())
                .hobbyIds(employee.getHobbies().stream().map(Hobby::getId).collect(Collectors.toSet()))
                .build();
    }
}
