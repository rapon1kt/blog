package com.raponi.blog.domain.model;

import java.time.Instant;

public record Account(
    String id,
    String email,
    String username,
    String password,
    Instant createdAt,
    Instant modifiedAt) {
}
