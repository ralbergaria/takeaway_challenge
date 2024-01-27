package com.takeaway.employee.adapters.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeEndpointTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnTheEmployeeById() throws Exception {
        var expectJson = "{\"id\":\"60a9b885-b256-434d-a5ba-08c18f9db4f0\",\"eMail\":\"r.albergaria85@gmail.com\",\"fullName\":\"Rafael Albergaria Carmo\",\"birthday\":\"1985-03-17\"}";
        mockMvc.perform(get("/api/v1/employees/60a9b885-b256-434d-a5ba-08c18f9db4f0")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectJson));
    }

    @Test
    void shouldReturnAllEmployee() throws Exception {
        var expectJson = "[{\"id\":\"60a9b885-b256-434d-a5ba-08c18f9db4f0\",\"eMail\":\"r.albergaria85@gmail.com\"},{\"fullName\":\"Rafael Albergaria Carmo\",\"birthday\":\"1985-03-17\"}]";
        mockMvc.perform(get("/api/v1/employees")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectJson));
    }

    @Test
    void shouldSaveTheEmployee() throws Exception {
        var expectJson = "[{\"id\":\"60a9b885-b256-434d-a5ba-08c18f9db4f0\",\"eMail\":\"test@test.com\"},{\"fullName\":\"test\",\"birthday\":\"2023-03-01\"}]";
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .eMail("test@test.com")
                .fullName("test")
                .birthday(LocalDate.of(2023,03,01))
                .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();

        mockMvc.perform(post("/api/v1/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectJson));
    }

    @Test
    void TheEmployeesShouldNotHaveTheSameEmail() throws Exception {
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .eMail("r.albergaria85@gmail.com")
                .fullName("test")
                .birthday(LocalDate.of(2023,03,01))
                .hobbiesIds(List.of("f8c4dd44-3190-4082-978b-525f6c3f8d3b")).build();

        mockMvc.perform(post("/api/v1/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteTheEmployee() throws Exception {
        var expectJson = "{}";
        mockMvc.perform(delete("/api/v1/employees/60a9b885-b256-434d-a5ba-08c18f9db4f0")
                        .contentType("application/json"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/employees/60a9b885-b256-434d-a5ba-08c18f9db4f0")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectJson));
    }
}
