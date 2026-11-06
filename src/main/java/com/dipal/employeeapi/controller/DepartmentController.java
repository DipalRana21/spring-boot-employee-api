package com.dipal.employeeapi.controller;

import com.dipal.employeeapi.dto.CreateDepartmentRequest;
import com.dipal.employeeapi.dto.DepartmentDetailsResponse;
import com.dipal.employeeapi.dto.DepartmentResponse;
import com.dipal.employeeapi.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(
            DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid @RequestBody CreateDepartmentRequest request) {

        DepartmentResponse response = departmentService.createDepartment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {

        return ResponseEntity.ok(
                departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {

        return ResponseEntity.ok(
                departmentService.getDepartmentById(id));
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<DepartmentDetailsResponse> getDepartmentWithEmployees(@PathVariable Long id) {

        return ResponseEntity.ok(
                departmentService.getDepartmentWithEmployees(id));
    }
}