
package com.dipal.employeeapi.dto.auth;

public record AuthResponse(
    String accessToken,
    String tokenType,
    long expiresIn
) {}
