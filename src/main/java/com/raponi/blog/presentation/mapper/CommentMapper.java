package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.application.usecase.comment.CreateCommentCommand;
import com.raponi.blog.presentation.dto.request.CreateCommentRequestDTO;

@Mapper(componentModel = "spring")
public interface CommentMapper {
  CreateCommentCommand toCommand(CreateCommentRequestDTO requestDTO);
}
