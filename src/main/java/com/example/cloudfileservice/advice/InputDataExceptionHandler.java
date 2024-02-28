package com.example.cloudfileservice.advice;

import com.example.cloudfileservice.domain.dto.response.ErrorDto;
import com.example.cloudfileservice.exception.InputDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InputDataExceptionHandler {

    @ExceptionHandler(InputDataException.class)
    public ResponseEntity<ErrorDto> rfHandler(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(e.getMessage(), 400));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> manveHandler(BindException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(((e.getFieldError())).getDefaultMessage(), 400));
    }
}

