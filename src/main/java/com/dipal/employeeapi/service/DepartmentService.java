package com.dipal.employeeapi.service;

import com.dipal.employeeapi.dto.CreateDepartmentRequest;
import com.dipal.employeeapi.dto.DepartmentDetailsResponse;
import com.dipal.employeeapi.dto.DepartmentResponse;
import com.dipal.employeeapi.exception.DepartmentNotFoundException;
import com.dipal.employeeapi.model.Department;
import com.dipal.employeeapi.repository.DepartmentRepository;
import com.dipal.employeeapi.dto.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

        private final DepartmentRepository departmentRepository;

        public DepartmentService(
                        DepartmentRepository departmentRepository) {

                this.departmentRepository = departmentRepository;
        }

        public DepartmentResponse createDepartment(
                        CreateDepartmentRequest request) {

                departmentRepository.findByName(request.getName())
                                .ifPresent(department -> {
                                        throw new IllegalArgumentException(
                                                        "Department already exists");
                                });

                Department department = new Department(request.getName());

                Department savedDepartment = departmentRepository.save(department);

                return convertToResponse(savedDepartment);
        }

        public List<DepartmentResponse> getAllDepartments() {

                return departmentRepository.findAll()
                                .stream()
                                .map(this::convertToResponse)
                                .toList();
        }

        public DepartmentResponse getDepartmentById(Long id) {

                Department department = findDepartmentById(id);

                return convertToResponse(department);
        }

        public Department findDepartmentById(Long id) {

                return departmentRepository.findById(id)
                                .orElseThrow(() -> new DepartmentNotFoundException(
                                                "Department not found with id: " + id));
        }

        private DepartmentResponse convertToResponse(
                        Department department) {

                return new DepartmentResponse(
                                department.getId(),
                                department.getName());
        }

        public DepartmentDetailsResponse getDepartmentWithEmployees(Long id) {

                Department department = findDepartmentById(id);

                List<EmployeeResponse> employees = department.getEmployees()
                                .stream()
                                .map(employee -> new EmployeeResponse(
                                                employee.getId(),
                                                employee.getName(),
                                                employee.getEmail(),
                                                employee.getRole(),
                                                employee.getSalary(),
                                                department.getId(),
                                                department.getName()))
                                .toList();

                return new DepartmentDetailsResponse(
                                department.getId(),
                                department.getName(),
                                employees);
        }
}