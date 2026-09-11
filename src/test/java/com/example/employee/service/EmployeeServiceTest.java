package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.exception.EmployeeAlreadyExistsException;
import com.example.employee.repository.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldCreateEmployeeSuccessfully() {

        Employee employee = new Employee(
                null,
                "John Doe",
                "john.doe@company.com",
                "Engineering"
        );

        when(employeeRepo.existsByEmail(employee.getEmail()))
                .thenReturn(false);

        when(employeeRepo.save(employee))
                .thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        assertEquals(employee, result);

        verify(employeeRepo)
                .existsByEmail(employee.getEmail());

        verify(employeeRepo)
                .save(employee);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        Employee employee = new Employee(
                null,
                "John Doe",
                "john.doe@company.com",
                "Engineering"
        );

        when(employeeRepo.existsByEmail(employee.getEmail()))
                .thenReturn(true);

        assertThrows(
                EmployeeAlreadyExistsException.class,
                () -> employeeService.createEmployee(employee)
        );

        verify(employeeRepo)
                .existsByEmail(employee.getEmail());

        verify(employeeRepo, never())
                .save(employee);
    }
}
