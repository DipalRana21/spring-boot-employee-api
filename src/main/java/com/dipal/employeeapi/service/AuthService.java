
package com.dipal.employeeapi.service;

import com.dipal.employeeapi.dto.auth.*;
import com.dipal.employeeapi.model.AppUser;
import com.dipal.employeeapi.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public RegistrationResponse register(RegisterRequest request) {
        String email = request.email().trim();

        if (appUserRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "An account with this email already exists");
        }

        AppUser user = new AppUser(
                email,
                passwordEncoder.encode(request.password()),
                "USER");

        AppUser saved = appUserRepository.save(user);

        return new RegistrationResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email().trim(),
                            request.password()));

            JwtService.AuthToken token = jwtService.generateToken(authentication);

            return new AuthResponse(
                    token.value(),
                    "Bearer",
                    token.expiresIn());

        } catch (AuthenticationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password");
        }
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentUser(Authentication authentication) {
        String email = authentication.getName();

        AppUser user = appUserRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getRole());
    }
}
