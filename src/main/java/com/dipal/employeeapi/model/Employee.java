package com.dipal.employeeapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_name", nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String role;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Version
    private Long version;

    public Employee() {
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee(String name, String role, String email, Double salary) {
        this.name = name;
        this.role = role;
        this.salary = salary;
        this.email = email;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}