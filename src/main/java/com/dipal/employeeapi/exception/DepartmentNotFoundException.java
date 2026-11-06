package com.dipal.employeeapi.exception;

public class DepartmentNotFoundException
        extends RuntimeException {

    public DepartmentNotFoundException(String message) {
        super(message);
    }
}