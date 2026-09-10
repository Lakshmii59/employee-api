package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.exception.EmployeeAlreadyExistsException;
import com.example.employee.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public Employee createEmployee(Employee employee){
        if(employeeRepo.existsByEmail(employee.getEmail())){
            throw new EmployeeAlreadyExistsException("Employee already exists");
        }
        return employeeRepo.save(employee);
    }
}
