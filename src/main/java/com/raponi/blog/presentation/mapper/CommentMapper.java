package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.infrastructure.persistence.entity.CommentEntity;
import com.raponi.blog.presentation.dto.CommentResponseDTO;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  CommentEntity toEntity(Comment comment);

  Comment toDomain(CommentEntity commentEntity);

  CommentResponseDTO toResponse(Comment comment);
}
