package com.dipal.employeeapi.dto;

public class EmployeeResponse {

    private Long id;
    private String name;
    private String role;
    private String email;
    private Double salary;

    private Long departmentId;
    private String departmentName;

    public EmployeeResponse(Long id, String name, String role, String email, Double salary, Long departmentId, String departmentName) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.salary = salary;
        this.departmentId = departmentId;
        this.departmentName = departmentName;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

     public String getEmail() {
        return email;
    }

    public Double getSalary() {
        return salary;
    }

     public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}