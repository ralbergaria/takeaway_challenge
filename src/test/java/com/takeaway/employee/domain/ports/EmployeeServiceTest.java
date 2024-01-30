package com.takeaway.employee.domain.ports;

import com.takeaway.employee.domain.exceptions.EntityNotFoundException;
import com.takeaway.employee.domain.exceptions.UniqueEmailException;
import com.takeaway.employee.domain.model.Employee;
import com.takeaway.employee.domain.model.StatusMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMessagePublisher employeeMessagePublisher;
    private EmployeeService employeeService;
    @BeforeEach
    void setup() {
        employeeService = spy(new EmployeeService(employeeRepository, employeeMessagePublisher));
    }

    @Test
    void testCreate() {
        // Given
        when(employeeRepository.findByEmail(any())).thenReturn(Optional.empty());
        Employee employee = Employee.builder().id("test").email("testEmail").build();
        // When
        employeeService.create(employee);
        // Then
        verify(employeeService).validateEmailAlreadyExists("testEmail");
        verify(employeeRepository).save(employee);
        verify(employeeMessagePublisher).sendMessage(employee, StatusMessage.CREATE);
    }

    @Test
    void testUpdate() {
        // Given
        Employee employee = Employee.builder().id("test").email("testEmail").build();
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));

        // When
        employeeService.update(employee);
        // Then
        verify(employeeService).validateEmployeeExists("test");
        verify(employeeRepository).save(employee);
        verify(employeeMessagePublisher).sendMessage(employee, StatusMessage.UPDATE);
    }

    @Test
    void testDelete() {
        // Given
        Employee employee = Employee.builder().id("test").email("test").build();
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));

        // When
        employeeService.delete("test");
        // Then
        verify(employeeService).validateEmployeeExists("test");
        verify(employeeRepository).delete(employee);
        verify(employeeMessagePublisher).sendMessage(employee, StatusMessage.DELETE);
    }

    @Test
    void testGetById() {
        // Given
        Employee employee = Employee.builder().id("test").email("test").build();
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));

        // When
        employeeService.getById("test");
        // Then
        verify(employeeService).validateEmployeeExists("test");
    }

    @Test
    void shouldValidateEmailAlreadyExists_NotThrow() {
        // Given
        when(employeeRepository.findByEmail(any())).thenReturn(Optional.empty());
        // When
        employeeService.validateEmailAlreadyExists("test");
        // No throws exception
    }

    @Test
    void shouldValidateEmailAlreadyExists_Throw() {
        // Given
        Employee employee = Employee.builder().id("test").email("test").build();
        when(employeeRepository.findByEmail(any())).thenReturn(Optional.of(employee));
        // When
        assertThrows(UniqueEmailException.class, () -> employeeService.validateEmailAlreadyExists("test"));
        // Throws exception
    }

    @Test
    void shouldValidateEmployeeExists_NotThrow() {
        // Given
        Employee employee = Employee.builder().id("test").email("test").build();
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        // When
        employeeService.validateEmployeeExists("test");
        // No throws exception
    }

    @Test
    void shouldValidateEmployeeExists_Throw() {
        // Given
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        // When
        assertThrows(EntityNotFoundException.class, () -> employeeService.validateEmployeeExists("test"));
        // No throws exception
    }
}
