package com.dailycodebuffer.DynamoDBSpringBootDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodebuffer.DynamoDBSpringBootDemo.entity.Employee;
import com.dailycodebuffer.DynamoDBSpringBootDemo.repository.EmployeeRepository;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employee")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") String employeeID) {
        return employeeRepository.getEmployeeByID(employeeID);
    }
    
    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable("id") String employeeID) {
        return employeeRepository.delete(employeeID);
    }

    @PutMapping("/employee/{id}")
    public String updateEmployee(@PathVariable("id") String employeeID, @RequestBody Employee employee) {
        return employeeRepository.update(employeeID, employee);
    }
}
