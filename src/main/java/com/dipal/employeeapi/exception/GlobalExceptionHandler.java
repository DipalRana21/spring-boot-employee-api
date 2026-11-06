package com.dipal.employeeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(EmployeeNotFoundException.class)
        public ErrorResponse handleEmployeeNotFound(
                        EmployeeNotFoundException exception) {

                return new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                exception.getMessage());
        }

        @ExceptionHandler(DepartmentNotFoundException.class)
        public ErrorResponse handleDepartmentNotFound(
                        DepartmentNotFoundException exception) {

                return new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                exception.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String, Object> handleValidationException(
                        MethodArgumentNotValidException exception) {

                Map<String, String> errors = new HashMap<>();

                exception.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> errors.put(
                                                error.getField(),
                                                error.getDefaultMessage()));

                Map<String, Object> response = new HashMap<>();

                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("message", "Validation failed");
                response.put("errors", errors);

                return response;
        }
}