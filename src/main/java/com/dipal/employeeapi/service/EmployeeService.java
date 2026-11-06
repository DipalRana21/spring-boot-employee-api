// package com.dipal.employeeapi.service;

// import com.dipal.employeeapi.dto.CreateEmployeeRequest;
// import com.dipal.employeeapi.dto.EmployeeResponse;
// import com.dipal.employeeapi.dto.UpdateEmployeeRequest;
// import com.dipal.employeeapi.exception.EmployeeNotFoundException;
// import com.dipal.employeeapi.model.Employee;
// import org.springframework.stereotype.Service;

// import java.util.ArrayList;
// import java.util.List;

// @Service
// public class EmployeeService {

//     private final List<Employee> employees = new ArrayList<>();

//     private Long nextId = 1L;

//     public EmployeeService() {

//         employees.add(
//                 new Employee(
//                         nextId++,
//                         "Dipal",
//                         "Backend Developer"
//                 )
//         );

//         employees.add(
//                 new Employee(
//                         nextId++,
//                         "Rahul",
//                         "Frontend Developer"
//                 )
//         );
//     }

//     public List<EmployeeResponse> getAllEmployees() {

//         return employees.stream()
//                 .map(this::convertToResponse)
//                 .toList();
//     }

//     public EmployeeResponse getEmployeeById(Long id) {

//         Employee employee = findEmployeeById(id);

//         return convertToResponse(employee);
//     }

//     public EmployeeResponse createEmployee(
//             CreateEmployeeRequest request) {

//         Employee employee = new Employee(
//                 nextId++,
//                 request.getName(),
//                 request.getRole()
//         );

//         employees.add(employee);

//         return convertToResponse(employee);
//     }

//     public EmployeeResponse updateEmployee(
//             Long id,
//             UpdateEmployeeRequest request) {

//         Employee employee = findEmployeeById(id);

//         employee.setName(request.getName());
//         employee.setRole(request.getRole());

//         return convertToResponse(employee);
//     }

//     public void deleteEmployee(Long id) {

//         Employee employee = findEmployeeById(id);

//         employees.remove(employee);
//     }

//     private Employee findEmployeeById(Long id) {

//         return employees.stream()
//                 .filter(employee -> employee.getId().equals(id))
//                 .findFirst()
//                 .orElseThrow(() ->
//                         new EmployeeNotFoundException(
//                                 "Employee not found with id: " + id
//                         )
//                 );
//     }

//     private EmployeeResponse convertToResponse(Employee employee) {

//         return new EmployeeResponse(
//                 employee.getId(),
//                 employee.getName(),
//                 employee.getRole()
//         );
//     }
// }

package com.dipal.employeeapi.service;

import com.dipal.employeeapi.dto.CreateEmployeeRequest;
import com.dipal.employeeapi.dto.EmployeeResponse;
import com.dipal.employeeapi.dto.UpdateEmployeeRequest;
import com.dipal.employeeapi.exception.EmployeeNotFoundException;
import com.dipal.employeeapi.model.Department;
import com.dipal.employeeapi.model.Employee;
import com.dipal.employeeapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.dipal.employeeapi.specification.EmployeeSpecification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeService {

        private final EmployeeRepository employeeRepository;
        private final DepartmentService departmentService;

        private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

        public EmployeeService(EmployeeRepository employeeRepository, DepartmentService departmentService) {
                this.employeeRepository = employeeRepository;
                this.departmentService = departmentService;
        }

        public List<EmployeeResponse> getAllEmployees() {

                return employeeRepository.findAll()
                                .stream()
                                .map(this::convertToResponse)
                                .toList();
        }

        public EmployeeResponse getEmployeeById(Long id) {

                Employee employee = findEmployeeById(id);

                return convertToResponse(employee);
        }

        @Transactional
        public EmployeeResponse assignDepartment(
                        Long employeeId,
                        Long departmentId) {

                Employee employee = findEmployeeById(employeeId);

                Department department = departmentService.findDepartmentById(departmentId);

                employee.setDepartment(department);

                Employee updatedEmployee = employeeRepository.save(employee);

                return convertToResponse(updatedEmployee);
        }

        public EmployeeResponse createEmployee(
                        CreateEmployeeRequest request) {

                log.info("Creating employee");

                Employee employee = new Employee(
                                request.getName(),
                                request.getRole(),
                                request.getEmail(),
                                request.getSalary());

                Employee savedEmployee = employeeRepository.save(employee);

                log.info("Employee created successfully with id={}", savedEmployee.getId());

                return convertToResponse(savedEmployee);
        }

        public EmployeeResponse updateEmployee(
                        Long id,
                        UpdateEmployeeRequest request) {

                Employee employee = findEmployeeById(id);

                employee.setName(request.getName());
                employee.setRole(request.getRole());
                employee.setEmail(request.getEmail());
                employee.setSalary(request.getSalary());

                Employee updatedEmployee = employeeRepository.save(employee);

                return convertToResponse(updatedEmployee);
        }

        @PreAuthorize("hasRole('ADMIN')")
        public void deleteEmployee(Long id) {

                Employee employee = findEmployeeById(id);

                employeeRepository.delete(employee);
        }

        private Employee findEmployeeById(Long id) {

                return employeeRepository.findById(id)
                                .orElseThrow(() -> new EmployeeNotFoundException(
                                                "Employee not found with id: " + id));
        }

        private EmployeeResponse convertToResponse(
                        Employee employee) {

                Long departmentId = null;
                String departmentName = null;

                if (employee.getDepartment() != null) {
                        departmentId = employee.getDepartment().getId();
                        departmentName = employee.getDepartment().getName();
                }

                return new EmployeeResponse(
                                employee.getId(),
                                employee.getName(),
                                employee.getRole(),
                                employee.getEmail(),
                                employee.getSalary(),
                                departmentId,
                                departmentName);
        }

        public List<EmployeeResponse> getEmployeesByRole(
                        String role) {

                return employeeRepository.findByRole(role)
                                .stream()
                                .map(this::convertToResponse)
                                .toList();
        }

        public List<EmployeeResponse> searchEmployees(
                        String name) {

                return employeeRepository
                                .findByNameContainingIgnoreCase(name)
                                .stream()
                                .map(this::convertToResponse)
                                .toList();
        }

        public List<EmployeeResponse> getEmployeesByDepartment(
                        Long departmentId) {

                return employeeRepository
                                .findByDepartmentId(departmentId)
                                .stream()
                                .map(this::convertToResponse)
                                .toList();
        }

        public List<EmployeeResponse> getHighSalaryEmployees(
                        Double salary) {

                return employeeRepository
                                .findHighSalaryEmployees(salary)
                                .stream()
                                .map(this::convertToResponse)
                                .toList();
        }

        public Page<EmployeeResponse> getEmployees(
                        Pageable pageable) {

                return employeeRepository
                                .findAll(pageable)
                                .map(this::convertToResponse);
        }

        @Transactional
        public EmployeeResponse promoteEmployee(Long id) {

                Employee employee = findEmployeeById(id);

                employee.setRole("Senior " + employee.getRole());

                return convertToResponse(employee);
        }

        @Transactional(readOnly = true)
        public Page<EmployeeResponse> searchEmployees(
                        String name,
                        String role,
                        Double minSalary,
                        Double maxSalary,
                        Long departmentId,
                        Pageable pageable) {

                Specification<Employee> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

                if (name != null && !name.isBlank()) {
                        spec = spec.and(EmployeeSpecification.nameContains(name.trim()));
                }

                if (role != null && !role.isBlank()) {
                        spec = spec.and(EmployeeSpecification.roleContains(role.trim()));
                }

                if (minSalary != null) {
                        spec = spec.and(EmployeeSpecification.salaryAtLeast(minSalary));
                }

                if (maxSalary != null) {
                        spec = spec.and(EmployeeSpecification.salaryAtMost(maxSalary));
                }

                if (departmentId != null) {
                        spec = spec.and(EmployeeSpecification.belongsToDepartment(departmentId));
                }

                return employeeRepository.findAll(spec, pageable)
                                .map(this::convertToResponse);
        }
}
