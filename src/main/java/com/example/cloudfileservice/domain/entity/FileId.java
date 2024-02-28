package com.example.cloudfileservice.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FileId implements Serializable {
    private String fileName;
    private User user;
}
