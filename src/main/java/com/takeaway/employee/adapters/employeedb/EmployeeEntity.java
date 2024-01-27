package com.takeaway.employee.adapters.employeedb;

import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.model.Hobby;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@Entity
@Table(name = "employees")
class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(name = "e_mail")
    private String eMail;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employees_to_hobbies",
            joinColumns = @JoinColumn(name = "hobby_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Set<HobbyEntity> hobbies = new HashSet<>();

    EmployeeEntity fromDomain(Employee employee) {
        return EmployeeEntity.builder()
                .id(UUID.fromString(employee.getId()))
                .eMail(employee.getEMail())
                .fullName(employee.getFullName())
                .birthday(employee.getBirthday())
                .hobbies(employee.getHobbies().stream().map(HobbyEntity::fromDomain).collect(Collectors.toSet()))
                .build();
    }

    Employee toDomain(EmployeeEntity employeeEntity) {
        return Employee.builder()
                .id(employeeEntity.getId().toString())
                .eMail(employeeEntity.getEMail())
                .fullName(employeeEntity.getFullName())
                .birthday(employeeEntity.getBirthday())
                .hobbies(employeeEntity.getHobbies().stream().map(HobbyEntity::toDomain).collect(Collectors.toSet()))
                .build();
    }
}
