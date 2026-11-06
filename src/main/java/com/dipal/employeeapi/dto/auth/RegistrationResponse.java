
package com.dipal.employeeapi.dto.auth;

public record RegistrationResponse(
    Long id,
    String email,
    String role
) {}
