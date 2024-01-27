package com.takeaway.employee.adapters.employeedb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EmployeeJPARepositoryTest {
    @Autowired
    private EmployeeJPARepository employeeRepository;

    @Test
    void shouldTestFindAll() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        assertEquals(1, employeeEntities.size());
        assertTrue(employeeEntities.stream().allMatch(it -> it.getEMail().contains("r.albergaria85@gmail.com")));
    }

    @Test
    void shouldTestFindById() {
        Optional<EmployeeEntity> employee = employeeRepository.findById(UUID.fromString("60a9b885-b256-434d-a5ba-08c18f9db4f0"));
        assertTrue(employee.isPresent());
    }

    @Test
    void shouldTestUpdateEmployee() {
        EmployeeEntity employee = employeeRepository.findById(UUID.fromString("60a9b885-b256-434d-a5ba-08c18f9db4f0")).orElseThrow();
        employee.setHobbies(employee.getHobbies().stream().filter(hobby -> hobby.getDescription().equals("soccer"))
                .collect(Collectors.toSet()));
        employeeRepository.save(employee);
        EmployeeEntity updatedEmployee = employeeRepository.findById(UUID.fromString("60a9b885-b256-434d-a5ba-08c18f9db4f0")).orElseThrow();
        assertTrue(updatedEmployee.getHobbies().stream().allMatch(hobby -> hobby.getDescription().equals("soccer")));
    }

    @Test
    void shouldTestDeleteEmployee() {
        EmployeeEntity employee = employeeRepository.findById(UUID.fromString("60a9b885-b256-434d-a5ba-08c18f9db4f0")).orElseThrow();
        employeeRepository.delete(employee);
        Optional<EmployeeEntity> updatedEmployee = employeeRepository.findById(UUID.fromString("60a9b885-b256-434d-a5ba-08c18f9db4f0"));
        assertFalse(updatedEmployee.isPresent());
    }

    @Test
    void shouldCreateNewEmployee() {
        EmployeeEntity employee = EmployeeEntity.builder()
                .eMail("test@test.com")
                .fullName("Test")
                .birthday(LocalDate.of(2000, 3, 17))
                .hobbies(Set.of(HobbyEntity.builder().id(UUID.fromString("f8c4dd44-3190-4082-978b-525f6c3f8d3b"))
                        .build())).build();
        employee = employeeRepository.save(employee);
        Optional<EmployeeEntity> employeeCreated = employeeRepository.findById(employee.getId());
        assertTrue(employeeCreated.isPresent());
        assertEquals(employee.getId(), employeeCreated.get().getId());
        assertEquals(employee.getEMail(), employeeCreated.get().getEMail());
        assertEquals(employee.getFullName(), employeeCreated.get().getFullName());
        assertEquals(employee.getBirthday(), employeeCreated.get().getBirthday());
        assertTrue(employee.getHobbies().stream().allMatch(hobby -> hobby.getId().equals(UUID.fromString("f8c4dd44-3190-4082-978b-525f6c3f8d3b"))));
    }

    @Test
    void shouldThrowExceptionEmailIsUnique() {
        EmployeeEntity employee = EmployeeEntity.builder().eMail("r.albergaria85@gmail.com").build();
        employeeRepository.save(employee);
    }
}