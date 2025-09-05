package com.raponi.blog.presentation.dto;

public record PublicAccountResponseDTO(
    String id,
    String username,
    String picture,
    String description,
    String createdAt) {

}
