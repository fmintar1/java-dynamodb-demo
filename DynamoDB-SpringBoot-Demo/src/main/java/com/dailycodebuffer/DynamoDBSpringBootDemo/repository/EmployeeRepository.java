package com.dailycodebuffer.DynamoDBSpringBootDemo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.dailycodebuffer.DynamoDBSpringBootDemo.entity.Employee;

@Repository
public class EmployeeRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Employee save(Employee employee) {
        dynamoDBMapper.save(employee);
        return employee;
    }

    public Employee getEmployeeByID(String employeeID) {
        return dynamoDBMapper.load(Employee.class, employeeID);
    }

    public String delete(String employeeID) {
        Employee emp = dynamoDBMapper.load(Employee.class, employeeID);
        dynamoDBMapper.delete(emp);
        return "Employee " + emp + " deleted!";
    }

    public Employee update(String employeeID, Employee employee) {
        try{
        dynamoDBMapper.save(employee,
            new DynamoDBSaveExpression()
            .withExpectedEntry("employeeID",
            new ExpectedAttributeValue(
                new AttributeValue().withS(employeeID)
            )));
        }
        catch(AmazonDynamoDBException | DynamoDBMappingException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return employee;
    }
    
}
