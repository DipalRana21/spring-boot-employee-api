package com.dipal.employeeapi.repository;

import com.dipal.employeeapi.model.Employee;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository
                extends JpaRepository<Employee, Long>,
                        JpaSpecificationExecutor<Employee> {

        Optional<Employee> findByEmail(String email);

        List<Employee> findByRole(String role);

        List<Employee> findBySalaryGreaterThan(Double salary);

        List<Employee> findBySalaryLessThan(Double salary);

        List<Employee> findByNameContaining(String name);

        List<Employee> findByNameContainingIgnoreCase(String name);

        List<Employee> findByRoleAndSalaryGreaterThan(
                        String role,
                        Double salary);

        List<Employee> findByDepartmentId(Long departmentId);

        @Query ("""
                        SELECT e
                        FROM Employee e
                        WHERE e.salary > :salary
                        ORDER BY e.salary DESC
                        """)
        List<Employee> findHighSalaryEmployees(
                        @Param ("salary") Double salary);
        
        Page<Employee> findAll(Pageable pageable);
}