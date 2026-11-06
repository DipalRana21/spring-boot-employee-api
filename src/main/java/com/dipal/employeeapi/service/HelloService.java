package com.dipal.employeeapi.service;

import org.springframework.stereotype.Service;

import com.dipal.employeeapi.EmployeeApiApplication;
import com.dipal.employeeapi.model.Employee;

@Service
public class HelloService {
    public String getMessage() {
        return "Hello from Spring Boot!";
    }

    // public Employee getEmployee() {
    //     return new Employee(
    //         1,
    //         "Dipal",
    //         "Java Backend Developer"
    //     );
    // }

//     public Employee getEmployeeById(int id) {

//     return employees.stream()
//             .filter(employee -> employee.getId() == id)
//             .findFirst()
//             .orElseThrow(() ->
//                     new EmployeeNotFoundException(
//                             "Employee not found with id: " + id
//                     )
//             );
// }
}

