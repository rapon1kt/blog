package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.presentation.dto.CommentResponseDTO;

@Mapper(componentModel = "spring")
public interface CommentMapper {
  CommentResponseDTO toResponse(Comment comment);
}
