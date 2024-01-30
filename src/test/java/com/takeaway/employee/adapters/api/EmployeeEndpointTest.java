package com.takeaway.employee.adapters.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.model.Hobby;
import com.takeaway.employee.domain.ports.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeEndpointTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeResponse employeeResponse;
    private Employee employee;

    @BeforeEach
    void setup() {
        employee = Employee.builder()
                .email("testall@testall.com")
                .fullName("test")
                .birthday(LocalDate.of(2023, 3, 1))
                .hobbies(Set.of(Hobby.builder().id("f8c4dd44-3190-4082-978b-525f6c3f8d3b").description("soccer").build()))
                .build();

        employee = employeeRepository.save(employee);

        employeeResponse = EmployeeResponse.fromDomain(employee);
    }

    @AfterEach
    void cleanUp()  {
        employeeRepository.delete(employee);
    }

    @Test
    void shouldReturnTheEmployeeById() throws Exception {
        // given
        mockMvc.perform(get("/api/v1/employees/public/" + employeeResponse.getId())
                        .contentType("application/json"))
        // Then
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employeeResponse)));
    }

    @Test
    void shouldReturnAllEmployee() throws Exception {
        // given
        mockMvc.perform(get("/api/v1/employees/public")
                        .contentType("application/json"))
        // Then
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(employeeResponse))));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldCreateTheEmployee() throws Exception {
        // given
        EmployeeCreateRequest employeeCreateRequest = EmployeeCreateRequest.builder()
                .email("test@test.com")
                .fullName("test")
                .birthday("2023-03-01")
                .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();
        // When
        MvcResult response = mockMvc.perform(post("/api/v1/employees")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employeeCreateRequest)))
                .andExpect(status().isOk())
                .andReturn();
        EmployeeResponse employeeResponse = objectMapper.readValue(response.getResponse().getContentAsString(), EmployeeResponse.class);
        // Then
        assertEquals("test@test.com", employeeResponse.getEmail());
        assertEquals("test", employeeResponse.getFullName());
        assertEquals("2023-03-01", employeeResponse.getBirthday());
        assertTrue(employeeResponse.getHobbies().stream().allMatch(h -> h.getId().equals("f8c4dd44-3190-4082-978b-525f6c3f8d3b")));
        // Clean
        mockMvc.perform(delete("/api/v1/employees/" + employeeResponse.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldUpdateTheEmployee() throws Exception {
        // given
        EmployeeUpdateRequest employeeUpdateRequest = EmployeeUpdateRequest.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .fullName("test updated")
                .birthday("2023-03-01")
                .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();
        // When
        MvcResult response = mockMvc.perform(put("/api/v1/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeUpdateRequest)))
                .andExpect(status().isOk())
                .andReturn();
        EmployeeResponse employeeResponse = objectMapper.readValue(response.getResponse().getContentAsString(), EmployeeResponse.class);
        // Then
        assertEquals("testall@testall.com", employeeResponse.getEmail());
        assertEquals("test updated", employeeResponse.getFullName());
        assertEquals("2023-03-01", employeeResponse.getBirthday());
        assertTrue(employeeResponse.getHobbies().stream().allMatch(h -> h.getId().equals("f8c4dd44-3190-4082-978b-525f6c3f8d3b")));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void employeesShouldNotHaveTheSameEmail() throws Exception {
        // Given
        EmployeeCreateRequest employeeCreateRequest = EmployeeCreateRequest.builder()
                .email("testall@testall.com")
                .fullName("test")
                .birthday("2023-03-01")
                .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();
        // When
        mockMvc.perform(post("/api/v1/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeCreateRequest)))
        // then
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldDeleteTheEmployee() throws Exception {
        // Given
        EmployeeCreateRequest employeeCreateRequest = EmployeeCreateRequest.builder()
                .email("testDelete@test.com")
                .fullName("test")
                .birthday("2023-03-01")
                .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();
        // When
        MvcResult response = mockMvc.perform(post("/api/v1/employees")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employeeCreateRequest)))
                        .andExpect(status().isOk()).andReturn();
        EmployeeResponse response2 = objectMapper.readValue(response.getResponse().getContentAsString(), EmployeeResponse.class);
        mockMvc.perform(delete("/api/v1/employees/" + response2.getId())
                        .contentType("application/json"))
        // Then
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/employees" + response2.getId())
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldTestInvalidEmail() throws Exception {
        // Given
        EmployeeCreateRequest employeeCreateRequest = EmployeeCreateRequest.builder()
                .email("testall")
                .fullName("test")
                .birthday("2023-03-01")
                .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();
        // When
        mockMvc.perform(post("/api/v1/employees")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employeeCreateRequest)))
        // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldTestInvalidBirthdayFormatDate() throws Exception {
        // Given
        EmployeeCreateRequest employeeCreateRequest = EmployeeCreateRequest.builder()
            .email("testall@test.com")
            .fullName("test")
            .birthday("01-03-2021")
            .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();
        // When
        mockMvc.perform(post("/api/v1/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeCreateRequest)))
        // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldTestDeleteForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/" + employee.getId())
                .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldTestCreateForbidden() throws Exception {
        // Given
        EmployeeCreateRequest employeeCreateRequest = EmployeeCreateRequest.builder()
                .email("test_create_forbidden@test.com")
                .fullName("test")
                .birthday("2021-03-01")
                .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();
        // When
        mockMvc.perform(post("/api/v1/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeCreateRequest)))
                // Then
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldTestUpdateForbidden() throws Exception {
        EmployeeUpdateRequest employeeUpdateRequest = EmployeeUpdateRequest.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .fullName("test updated")
                .birthday("2023-03-01")
                .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();
        mockMvc.perform(put("/api/v1/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeUpdateRequest)))
                .andExpect(status().isForbidden());
    }
}