package com.example.cloudfileservice.repository;


import com.example.cloudfileservice.domain.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> getUserByLogin(String login);

}
