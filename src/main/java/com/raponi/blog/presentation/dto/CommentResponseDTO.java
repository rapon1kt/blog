package com.raponi.blog.presentation.dto;

import java.time.Instant;

public record CommentResponseDTO(
    String id,
    String postId,
    String content,
    String accountId,
    Instant createdAt) {

}
