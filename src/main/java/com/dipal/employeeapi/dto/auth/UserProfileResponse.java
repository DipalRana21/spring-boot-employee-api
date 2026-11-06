package com.dipal.employeeapi.dto.auth;

public record UserProfileResponse(
        Long id,
        String email,
        String role
) {}