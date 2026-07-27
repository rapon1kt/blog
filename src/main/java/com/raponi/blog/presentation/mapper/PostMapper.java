package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.application.usecase.post.CreatePostCommand;
import com.raponi.blog.presentation.dto.request.CreatePostRequestDTO;

@Mapper(componentModel = "spring")
public interface PostMapper {

  CreatePostCommand toCommand(CreatePostRequestDTO requestDTO);

}
