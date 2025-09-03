package com.raponi.blog.presentation.dto;

import java.time.Instant;

public record PostResponseDTO(
    String id,
    String title,
    String content,
    String accountId,
    Instant createdAt) {

}
