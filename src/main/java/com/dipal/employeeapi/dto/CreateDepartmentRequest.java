package com.dipal.employeeapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateDepartmentRequest {

    @NotBlank(message = "Department name is required")
    @Size(
        min = 2,
        max = 100,
        message = "Department name must be between 2 and 100 characters"
    )
    private String name;

    public CreateDepartmentRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}