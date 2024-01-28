package com.takeaway.employee.adapters.employeedb;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
class EmployeeJPARepositoryTest {
    @Autowired
    private EmployeeJPARepository employeeRepository;

    private EmployeeEntity employeeEntity;

    @BeforeEach
    void setup() {
        employeeEntity = EmployeeEntity.builder()
                .email("r.albergaria85@gmail.com")
                .fullName("Rafael Albergaria Carmo")
                .birthday(LocalDate.of(1985, 3, 17))
                .hobbies(Set.of(HobbyEntity.builder().id(UUID.fromString("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).description("soccer").build()))
                .build();
        employeeRepository.save(employeeEntity);
    }

    @AfterEach
    void cleanUp() {
        employeeRepository.deleteAll();
    }

    @Test
    void shouldTestFindAll() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        assertEquals(1, employeeEntities.size());
        assertTrue(employeeEntities.stream().allMatch(it -> it.getEmail().contains("r.albergaria85@gmail.com")));
    }

    @Test
    void shouldTestFindById() {
        Optional<EmployeeEntity> employee = employeeRepository.findById(employeeEntity.getId());
        assertTrue(employee.isPresent());
    }

    @Test
    void shouldTestFindByEmail() {
        Optional<EmployeeEntity> employee = employeeRepository.findByEmail("r.albergaria85@gmail.com");
        assertTrue(employee.isPresent());
    }

    @Test
    void shouldTestUpdateEmployee() {
        EmployeeEntity employee = employeeRepository.findById(employeeEntity.getId()).orElseThrow();
        employee.setHobbies(employee.getHobbies().stream().filter(hobby -> hobby.getDescription().equals("soccer"))
                .collect(Collectors.toSet()));
        employeeRepository.save(employee);
        EmployeeEntity updatedEmployee = employeeRepository.findById(employeeEntity.getId()).orElseThrow();
        assertTrue(updatedEmployee.getHobbies().stream().allMatch(hobby -> hobby.getDescription().equals("soccer")));
    }

    @Test
    void shouldTestDeleteEmployee() {
        EmployeeEntity employee = employeeRepository.findById(employeeEntity.getId()).orElseThrow();
        employeeRepository.delete(employee);
        Optional<EmployeeEntity> updatedEmployee = employeeRepository.findById(employeeEntity.getId());
        assertFalse(updatedEmployee.isPresent());
    }

    @Test
    void shouldCreateNewEmployee() {
        EmployeeEntity employee = EmployeeEntity.builder()
                .email("test@test.com")
                .fullName("Test")
                .birthday(LocalDate.of(2000, 3, 17))
                .hobbies(Set.of(HobbyEntity.builder().id(UUID.fromString("f8c4dd44-3190-4082-978b-525f6c3f8d3b"))
                        .build())).build();
        employee = employeeRepository.save(employee);
        Optional<EmployeeEntity> employeeCreated = employeeRepository.findById(employee.getId());
        assertTrue(employeeCreated.isPresent());
        assertEquals(employee.getId(), employeeCreated.get().getId());
        assertEquals(employee.getEmail(), employeeCreated.get().getEmail());
        assertEquals(employee.getFullName(), employeeCreated.get().getFullName());
        assertEquals(employee.getBirthday(), employeeCreated.get().getBirthday());
        assertTrue(employee.getHobbies().stream().allMatch(hobby -> hobby.getId().equals(UUID.fromString("f8c4dd44-3190-4082-978b-525f6c3f8d3b"))));
    }
}