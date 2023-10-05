package com.dailycodebuffer.DynamoDBSpringBootDemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.dailycodebuffer.DynamoDBSpringBootDemo.entity.Department;
import com.dailycodebuffer.DynamoDBSpringBootDemo.entity.Employee;
import com.dailycodebuffer.DynamoDBSpringBootDemo.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeRepositoryTest {
    
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @InjectMocks
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee("1", "John", "Doe", "john@gmail.com", new Department("IT", "IT001"));
        doNothing().when(dynamoDBMapper).save(any());

        Employee savedEmployee = employeeRepository.save(employee);

        assertNotNull(savedEmployee);
        assertEquals("1", savedEmployee.getEmployeeID());
        assertEquals("John", savedEmployee.getFirstName());
        assertEquals("Doe", savedEmployee.getLastName());
    }

    @Test
    public void testGetEmployeeByID() {
        Employee employee = new Employee("1", "John", "Doe", "john@example.com", new Department("IT", "IT001"));
        when(dynamoDBMapper.load(Employee.class, "1")).thenReturn(employee);

        Employee retrievedEmployee = employeeRepository.getEmployeeByID("1");

        assertNotNull(retrievedEmployee);
        assertEquals("1", retrievedEmployee.getEmployeeID());
        assertEquals("john@example.com", retrievedEmployee.getEmail());
        assertEquals("IT", retrievedEmployee.getDepartment().getDepartmentName());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee("1", "John", "Doe", "john@example.com", new Department("IT", "IT001"));
        when(dynamoDBMapper.load(Employee.class, "1")).thenReturn(employee);

        String result = employeeRepository.delete("1");

        assertEquals("Employee " + employee + " deleted!", result);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee("1", "John", "Doe", "john@example.com", new Department("IT", "IT001"));

        Employee savedEmployee = employeeRepository.save(employee);

        assertNotNull(savedEmployee);
        assertEquals("1", savedEmployee.getEmployeeID());
        assertEquals("John", savedEmployee.getFirstName());
        assertEquals("john@example.com", savedEmployee.getEmail());

        Employee employee2 = new Employee("1", "Jane", "Dawn", "jane@example.com", new Department("SWE", "SWE001"));

        savedEmployee = employeeRepository.update("1", employee2);

        assertNotNull(savedEmployee);
        assertEquals("1", savedEmployee.getEmployeeID());
        assertEquals("Jane", savedEmployee.getFirstName());
        assertEquals("Dawn", savedEmployee.getLastName());
        assertEquals("SWE", savedEmployee.getDepartment().getDepartmentName());


    }
}
