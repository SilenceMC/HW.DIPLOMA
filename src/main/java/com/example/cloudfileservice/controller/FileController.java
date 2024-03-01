package com.example.cloudfileservice.controller;

import com.example.cloudfileservice.domain.dto.response.FileDto;
import com.example.cloudfileservice.service.AuthService;
import com.example.cloudfileservice.service.FileService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class FileController {
    private final FileService fileService;
    private final AuthService authService;


    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String fileName,
            @RequestBody MultipartFile file) throws IOException {
        fileService.uploadFile(
                authService.getLoginByToken(authToken),
                fileName,
                file.getSize(),
                file.getBytes());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/file")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String fileName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ByteArrayResource(fileService.downloadFile(authService.getLoginByToken(authToken), fileName)));
    }

    @PutMapping("/file")
    public ResponseEntity<?> renameFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String fileName,
            @RequestBody @Validated @JsonProperty("filename") String newFileName) {
        fileService.renameFile(authService.getLoginByToken(authToken), fileName, newFileName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String fileName) {
        fileService.deleteFile(authService.getLoginByToken(authToken), fileName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileDto>> getFileList(
            @RequestHeader("auth-token") String authToken,
            @RequestParam int limit) {
        ;
        return ResponseEntity.ok(fileService.getFileList(authService.getLoginByToken(authToken), limit));
    }


}
