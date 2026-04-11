package com.kuraiji.blog.controllers;

import com.kuraiji.blog.common.constants.ValidationConstants;
import com.kuraiji.blog.domain.request.LoginRequest;
import com.kuraiji.blog.domain.response.AuthResponse;
import com.kuraiji.blog.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @Value("${spring.profiles.active}")
    private String profile;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = authenticationService.authenticate(
                loginRequest.getEmail(), loginRequest.getPassword());
        String tokenValue = authenticationService.generateToken(userDetails);
        ResponseCookie cookie = ResponseCookie.from(ValidationConstants.COOKIE_NAME, tokenValue)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(86400)
                .sameSite(Objects.equals(profile, "dev") ? "Lax" : "Strict")
                .partitioned(true)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged in successfully");
    }
}
