package com.example.cloudfileservice.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FileDto(
        @JsonProperty("filename")
        String fileName,
        long size) {
}
