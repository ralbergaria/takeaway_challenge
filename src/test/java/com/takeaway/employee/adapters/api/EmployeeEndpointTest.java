package com.takeaway.employee.adapters.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.ports.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

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

    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setup() throws Exception {
        EmployeeCreateRequest employeeCreateRequest = EmployeeCreateRequest.builder()
                .email("testall@testall.com")
                .fullName("test")
                .birthday("2023-03-01")
                .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();

        MvcResult response = mockMvc.perform(post("/api/v1/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeCreateRequest))).andReturn();
        employeeResponse = objectMapper.readValue(response.getResponse().getContentAsString(), EmployeeResponse.class);
    }

    @AfterEach
    void cleanUp() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/" + employeeResponse.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTheEmployeeById() throws Exception {
        // given
        mockMvc.perform(get("/api/v1/employees/" + employeeResponse.getId())
                        .contentType("application/json"))
        // Then
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employeeResponse)));
    }

    @Test
    void shouldReturnAllEmployee() throws Exception {
        // given
        mockMvc.perform(get("/api/v1/employees")
                        .contentType("application/json"))
        // Then
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(employeeResponse))));
    }

    @Test
    void shouldSaveTheEmployee() throws Exception {
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
    void shouldTestInvalidBirthdayDate() throws Exception {
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
}