package com.raponi.blog.infrastructure.persistence.mapper;

import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.infrastructure.persistence.entity.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentInfraMapper {
  CommentEntity toEntity(Comment comment);

  Comment toDomain(CommentEntity accountEntity);
}
