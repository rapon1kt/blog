package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.presentation.dto.PostResponseDTO;

@Mapper(componentModel = "spring")
public interface PostMapper {
  PostResponseDTO toResponse(Post post);
}
