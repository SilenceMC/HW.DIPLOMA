package com.example.cloudfileservice.service;

import com.example.cloudfileservice.domain.dto.response.FileDto;
import com.example.cloudfileservice.domain.entity.File;

import com.example.cloudfileservice.domain.entity.User;
import com.example.cloudfileservice.exception.InputDataException;
import com.example.cloudfileservice.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final UserService userService;
    private final FileRepository fileRepository;

    public void uploadFile(String login, String fileName, long size, byte[] fileContent) throws IOException {
        User userEntity = userService.getUserByLogin(login);
        File fileEntity = new File(fileName, size, fileContent, userEntity);
        fileRepository.save(fileEntity);
    }


    public byte[] downloadFile(String login, String fileName) {
        User userEntity = userService.getUserByLogin(login);
        return fileRepository.findByFileNameAndUser(fileName, userEntity).get().getFileContent();
    }

    public void renameFile(String login, String fileName, String newFileName) {
        User userEntity = userService.getUserByLogin(login);
        checkUniqueFileName(fileName, userEntity);
        fileRepository.renameFile(fileName, newFileName, userEntity);
    }

    public void deleteFile(String login, String fileName) {
        User userEntity = userService.getUserByLogin(login);
        fileRepository.deleteByFileNameAndUser(fileName, userEntity);
    }

    public List<FileDto> getFileList(String login, int limit) {
        User userEntity = userService.getUserByLogin(login);
        return fileRepository.findAllByUser(userEntity)
                .stream()
                .limit(limit)
                .map(file -> new FileDto(file.getFileName(), file.getSize()))
                .collect(Collectors.toList());
    }

    public void checkUniqueFileName(String fileName, User userEntity) {
        if (fileRepository.findByFileNameAndUser(fileName, userEntity).isPresent()) {
            log.error("Не пройдена проверка уникальности имени файла {} для пользователем {}", fileName, userEntity.getLogin());
            throw new InputDataException("Файл с таким именем уже существует!");
        }
    }
}
