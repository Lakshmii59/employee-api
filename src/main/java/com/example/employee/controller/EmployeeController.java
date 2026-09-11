package com.example.employee.controller;

import com.example.employee.dto.EmployeeRequest;
import com.example.employee.dto.EmployeeResponse;
import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request){
        Employee employee = new Employee(
                null,
                request.name(),
                request.email(),
                request.department()
        );
        Employee savedEmployee = employeeService.createEmployee(employee);
        EmployeeResponse response = new EmployeeResponse("Employee created successfully", savedEmployee.getEmployeeId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee>getEmployeeById(@PathVariable Long employeeId){
        Employee employee = employeeService.getEmployeeById(employeeId);
        return  ResponseEntity.ok(employee);
    }
}
