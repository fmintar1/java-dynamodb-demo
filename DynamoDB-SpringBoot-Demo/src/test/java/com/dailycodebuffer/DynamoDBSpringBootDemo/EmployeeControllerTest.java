package com.dailycodebuffer.DynamoDBSpringBootDemo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dailycodebuffer.DynamoDBSpringBootDemo.controller.EmployeeController;
import com.dailycodebuffer.DynamoDBSpringBootDemo.entity.Department;
import com.dailycodebuffer.DynamoDBSpringBootDemo.entity.Employee;
import com.dailycodebuffer.DynamoDBSpringBootDemo.repository.EmployeeRepository;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetEmployee() throws Exception {
        Employee employee = new Employee("1", "John", "Doe", "john@example.com", new Department("IT", "IT001"));
        
        employeeRepository.save(employee);

        when(employeeRepository.getEmployeeByID("1")).thenReturn(new Employee("1", "John", "Doe", "john@example.com", new Department("IT", "IT001")));

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.employeeID").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testPostEmployee() {
        Employee savedEmployee = employeeRepository.save(new Employee("1", "John", "Doe", "john@example.com", new Department("IT", "IT001")));
        Employee employeeToSave = employeeRepository.save(new Employee("1", "John", "Doe", "john@example.com", new Department("IT", "IT001")));

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
        
        
    }
    
}
