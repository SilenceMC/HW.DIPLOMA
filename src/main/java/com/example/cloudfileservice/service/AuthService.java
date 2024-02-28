package com.example.cloudfileservice.service;

import com.example.cloudfileservice.domain.dto.request.AuthRequestDto;
import com.example.cloudfileservice.domain.dto.response.AuthResponseDto;
import com.example.cloudfileservice.domain.entity.User;
import com.example.cloudfileservice.exception.AuthException;
import com.example.cloudfileservice.repository.AuthRepository;
import com.example.cloudfileservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    public AuthResponseDto loginUser(AuthRequestDto authRequestDto) {
        Optional<User> user = userRepository.getUserByLogin(authRequestDto.login());
        return new AuthResponseDto(authRepository.createToken(user.get().getLogin()));
    }

    public String getLoginByToken(String authToken) {
        if (authToken.startsWith("Bearer "))
            authToken = authToken.substring(7);

        Optional<String> login = authRepository.getLoginByToken(authToken);

        if (login.isEmpty()) {
            log.error("Для переданного значения auth-token {} не найден пользователь в БД", authToken);
            throw new AuthException("Неавторизованный запрос!");
        }

        return login.get();
    }

    public void logoutUser(String authToken) {
        authRepository.deleteToken(authToken);
    }
}
