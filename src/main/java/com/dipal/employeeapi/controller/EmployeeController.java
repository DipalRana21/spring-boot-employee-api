package com.dipal.employeeapi.controller;

import com.dipal.employeeapi.dto.CreateEmployeeRequest;
import com.dipal.employeeapi.dto.EmployeeResponse;
import com.dipal.employeeapi.dto.UpdateEmployeeRequest;
import com.dipal.employeeapi.service.EmployeeService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

        private final EmployeeService employeeService;

        public EmployeeController(EmployeeService employeeService) {
                this.employeeService = employeeService;
        }

        @GetMapping
        public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(Pageable pageable) {

                return ResponseEntity.ok(
                                employeeService.getEmployees(pageable));
        }

        @GetMapping("/{id}")
        public ResponseEntity<EmployeeResponse> getEmployeeById(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                employeeService.getEmployeeById(id));
        }

        @PostMapping
        public ResponseEntity<EmployeeResponse> createEmployee(
                        @Valid @RequestBody CreateEmployeeRequest request) {

                EmployeeResponse response = employeeService.createEmployee(request);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);
        }

        @PutMapping("/{id}")
        public ResponseEntity<EmployeeResponse> updateEmployee(
                        @PathVariable Long id,
                        @Valid @RequestBody UpdateEmployeeRequest request) {

                return ResponseEntity.ok(
                                employeeService.updateEmployee(id, request));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteEmployee(
                        @PathVariable Long id) {

                employeeService.deleteEmployee(id);

                return ResponseEntity.noContent().build();
        }

        @PutMapping("/{employeeId}/department/{departmentId}")
        public ResponseEntity<EmployeeResponse> assignDepartment(
                        @PathVariable Long employeeId,
                        @PathVariable Long departmentId) {

                return ResponseEntity.ok(
                                employeeService.assignDepartment(
                                                employeeId,
                                                departmentId));
        }

        @GetMapping("/role/{role}")
        public ResponseEntity<List<EmployeeResponse>> getEmployeesByRole(
                        @PathVariable String role) {

                return ResponseEntity.ok(
                                employeeService.getEmployeesByRole(role));
        }

        // @GetMapping("/search")
        // public ResponseEntity<List<EmployeeResponse>> searchEmployees(
        //                 @RequestParam String name) {

        //         return ResponseEntity.ok(
        //                         employeeService.searchEmployees(name));
        // }

        @GetMapping("/department/{departmentId}")
        public ResponseEntity<List<EmployeeResponse>> getEmployeesByDepartment(
                        @PathVariable Long departmentId) {

                return ResponseEntity.ok(
                                employeeService.getEmployeesByDepartment(
                                                departmentId));
        }

        @GetMapping("/high-salary")
        public ResponseEntity<List<EmployeeResponse>> getHighSalaryEmployees(
                        @RequestParam Double salary) {

                return ResponseEntity.ok(
                                employeeService.getHighSalaryEmployees(salary));
        }

        @PutMapping("/{id}/promote")
        public ResponseEntity<EmployeeResponse> promoteEmployee(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                employeeService.promoteEmployee(id));
        }

        @GetMapping("/search")
        public ResponseEntity<Page<EmployeeResponse>> searchEmployees(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false) String role,
                        @RequestParam(required = false) Double minSalary,
                        @RequestParam(required = false) Double maxSalary,
                        @RequestParam(required = false) Long departmentId,
                        Pageable pageable) {

                return ResponseEntity.ok(
                                employeeService.searchEmployees(
                                                name,
                                                role,
                                                minSalary,
                                                maxSalary,
                                                departmentId,
                                                pageable));
        }
}