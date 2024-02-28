package com.example.cloudfileservice.advice;

import com.example.cloudfileservice.domain.dto.response.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class IOExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDto> eHandler() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Что-то пошло не так!", 500));
    }
}
