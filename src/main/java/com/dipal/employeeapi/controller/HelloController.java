package com.dipal.employeeapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dipal.employeeapi.service.HelloService;
import com.dipal.employeeapi.model.Employee;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/api/hello")
    public String hello() {
        return helloService.getMessage();
    }

    // @GetMapping("/api/employee")
    // public Employee getEmployee() {
    //     return helloService.getEmployee();
    // }

    // @PostMapping ("/api/employees")
    // public ResponseEntity<EmployeeResponse> createEmployee(
    //         @Valid @RequestBody CreateEmployeeRequest request) {

    //     EmployeeResponse response = employeeService.createEmployee(request);

    //     return ResponseEntity
    //             .status(HttpStatus.CREATED)
    //             .body(response);
    // }
}
