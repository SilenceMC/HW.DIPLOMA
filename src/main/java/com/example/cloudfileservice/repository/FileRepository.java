package com.example.cloudfileservice.repository;

import com.example.cloudfileservice.domain.entity.File;
import com.example.cloudfileservice.domain.entity.User;
import jakarta.transaction.Transactional;
import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, String> {

    @Transactional
    List<File> findAllByUser(User userEntity);

    @Modifying
    @Transactional
    @Query("update File f set f.fileName = :newFileName where f.user = : userEntity and  f.fileName = :fileName")
    void renameFile(String fileName, String newFileName, User userEntity);

    @Transactional
    void deleteByFileNameAndUser(String fileName, User userEntity);

    @Transactional
    Optional<File> findByFileNameAndUser(String fileName, User userEntity);
}
