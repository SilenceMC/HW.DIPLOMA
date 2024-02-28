package com.example.cloudfileservice.controller;

import com.example.cloudfileservice.domain.dto.request.AuthRequestDto;
import com.example.cloudfileservice.domain.dto.response.AuthResponseDto;
import com.example.cloudfileservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authUser(@RequestBody @Validated AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.loginUser(authRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("auth-token") String authToken) {
        authService.logoutUser(authToken);
        return ResponseEntity.ok().build();
    }
}
