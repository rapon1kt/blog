package com.raponi.blog.presentation.dto;

import java.time.Instant;

public record AccountResponseDTO(
    String id,
    String username,
    Instant createdAt) {

}
