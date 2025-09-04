package com.raponi.blog.presentation.dto;

import java.time.Instant;

public record CreatedAccountResponseDTO(
    String id,
    String username,
    Instant createdAt) {

}
