package com.example.cloudfileservice;

import com.example.cloudfileservice.domain.dto.request.AuthRequestDto;
import com.example.cloudfileservice.domain.dto.response.AuthResponseDto;
import com.example.cloudfileservice.domain.entity.User;
import com.example.cloudfileservice.exception.AuthException;
import com.example.cloudfileservice.repository.AuthRepository;
import com.example.cloudfileservice.repository.UserRepository;
import com.example.cloudfileservice.service.AuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthService authService;
    private static AuthRequestDto authRequestDto;
    private static User userEntity;
    private static String authToken;

    @BeforeAll
    static void init() {
        authRequestDto = new AuthRequestDto(
                "user",
                "password"
        );

        userEntity = new User(
                "user",
                "password"
        );

        authToken = String.valueOf(UUID.randomUUID());
    }

    @Test
    void loginUser() {
        when(userRepository.getUserByLogin(authRequestDto.login()))
                .thenReturn(Optional.ofNullable(userEntity));
        when(authRepository.createToken(userEntity.getLogin()))
                .thenReturn(UUID.randomUUID());

        assertNotNull(authService.loginUser(authRequestDto));
        verify(authRepository, times(1)).createToken(userEntity.getLogin());
    }

    @Test
    void getLoginByToken_error() {
        when(authRepository.getLoginByToken(authToken))
                .thenReturn(Optional.empty());

        assertThrows(AuthException.class,
                () -> authService.getLoginByToken(authToken));
        verify(authRepository, times(1)).getLoginByToken(authToken);
    }

    @Test
    void getLoginByToken_success() {
        when(authRepository.getLoginByToken(authToken))
                .thenReturn(Optional.of("Random String"));

        assertNotNull(authService.getLoginByToken(authToken));
        verify(authRepository, times(1)).getLoginByToken(authToken);
    }

    @Test
    void logoutUser() {
        authRepository.deleteToken(authToken);
        verify(authRepository, times(1)).deleteToken(authToken);
    }
}
