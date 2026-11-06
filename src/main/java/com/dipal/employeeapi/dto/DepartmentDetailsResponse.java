package com.dipal.employeeapi.dto;

import java.util.List;

public class DepartmentDetailsResponse {

    private Long id;
    private String name;
    private List<EmployeeResponse> employees;

    public DepartmentDetailsResponse(
            Long id,
            String name,
            List<EmployeeResponse> employees) {

        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<EmployeeResponse> getEmployees() {
        return employees;
    }
}