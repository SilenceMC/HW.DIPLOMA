package com.example.cloudfileservice.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.context.properties.bind.Name;

import java.util.UUID;

public record AuthResponseDto(
        @JsonProperty("auth-token")
        UUID authToken
) {
}
