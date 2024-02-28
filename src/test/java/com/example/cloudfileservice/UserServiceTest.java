package com.example.cloudfileservice;

import com.example.cloudfileservice.domain.entity.User;
import com.example.cloudfileservice.exception.AuthException;
import com.example.cloudfileservice.repository.UserRepository;
import com.example.cloudfileservice.service.AuthService;
import com.example.cloudfileservice.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static User userEntity;

    private static String login;

    @BeforeAll
    static void init() {
        userEntity = new User(
                "user",
                "password"
        );

        login = "Random String";
    }

    @Test
    void getUserByLogin_error() {
        when(userRepository.getUserByLogin(login))
                .thenReturn(Optional.empty());

        assertThrows(AuthException.class,
                () -> userService.getUserByLogin(login));
        verify(userRepository, times(1)).getUserByLogin(login);
    }

    @Test
    void getUserByLogin_success() {
        when(userRepository.getUserByLogin(login))
                .thenReturn(Optional.of(userEntity));

        assertEquals(userEntity, userService.getUserByLogin(login));
        verify(userRepository, times(1)).getUserByLogin(login);
    }
}
