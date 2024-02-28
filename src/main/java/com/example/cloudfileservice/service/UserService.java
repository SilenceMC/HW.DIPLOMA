package com.example.cloudfileservice.service;

import com.example.cloudfileservice.domain.entity.User;
import com.example.cloudfileservice.exception.AuthException;
import com.example.cloudfileservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User getUserByLogin(String login) {
        Optional<User> userEntity = userRepository.getUserByLogin(login);
        if (userEntity.isEmpty()) {
            log.error("Для переданного значения login {} не найден пользователь в БД", login);
            throw new AuthException("Пользователя с таким login не существует");
        }
        return userEntity.get();
    }
}
