package com.takeaway.employee.adapters.employeedb;

import com.takeaway.employee.domain.model.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@Entity
@AllArgsConstructor
@Table(name = "employees")
class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(name = "e_mail")
    private String email;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employees_to_hobbies",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id", referencedColumnName = "id"))
    private Set<HobbyEntity> hobbies;

    public EmployeeEntity() {}

    static EmployeeEntity fromDomain(Employee employee) {
        return EmployeeEntity.builder()
                .id(employee.getId() != null ? UUID.fromString(employee.getId()) : null)
                .email(employee.getEmail())
                .fullName(employee.getFullName())
                .birthday(employee.getBirthday())
                .hobbies(employee.getHobbies().stream().map(HobbyEntity::fromDomain).collect(Collectors.toSet()))
                .build();
    }

    static Employee toDomain(EmployeeEntity employeeEntity) {
        return Employee.builder()
                .id(employeeEntity.getId().toString())
                .email(employeeEntity.getEmail())
                .fullName(employeeEntity.getFullName())
                .birthday(employeeEntity.getBirthday())
                .hobbies(employeeEntity.getHobbies().stream().map(HobbyEntity::toDomain).collect(Collectors.toSet()))
                .build();
    }
}
