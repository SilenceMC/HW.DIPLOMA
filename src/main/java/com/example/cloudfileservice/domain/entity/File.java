package com.example.cloudfileservice.domain.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@Entity
@IdClass(FileId.class)
@Table(name = "FILES")
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    private String fileName;

    @Column(nullable = false)
    private long size;

    @Lob
    @Column(nullable = false)
    private byte[] fileContent;

    @Id
    @ManyToOne(optional = false)
    private User user;
}
