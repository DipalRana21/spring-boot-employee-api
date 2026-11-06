
package com.dipal.employeeapi.specification;

import com.dipal.employeeapi.model.Employee;
import org.springframework.data.jpa.domain.Specification;

public final class EmployeeSpecification {

    private EmployeeSpecification() {
        // Utility class: no instances needed.
    }

    public static Specification<Employee> nameContains(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<Employee> roleContains(String role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("role")),
                        "%" + role.toLowerCase() + "%"
                );
    }

    public static Specification<Employee> salaryAtLeast(Double minSalary) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("salary"),
                        minSalary
                );
    }

    public static Specification<Employee> salaryAtMost(Double maxSalary) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(
                        root.get("salary"),
                        maxSalary
                );
    }

    public static Specification<Employee> belongsToDepartment(
            Long departmentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("department").get("id"),
                        departmentId
                );
    }
}
