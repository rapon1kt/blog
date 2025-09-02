package com.raponi.blog.presentation.dto;

import java.time.Instant;

public record AccountResponseDTO(
    String id,
    String username,
    String email,
    String role,
    boolean active,
    Instant createdAt) {

}
