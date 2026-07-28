package com.raponi.blog.infrastructure.persistence.mapper;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.infrastructure.persistence.entity.PostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostInfraMapper {
  PostEntity toEntity(Post post);

  Post toDomain(PostEntity postEntity);
}
