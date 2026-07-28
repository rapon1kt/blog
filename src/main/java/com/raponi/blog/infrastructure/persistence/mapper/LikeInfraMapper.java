package com.raponi.blog.infrastructure.persistence.mapper;

import com.raponi.blog.domain.model.Like;
import com.raponi.blog.infrastructure.persistence.entity.LikeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeInfraMapper {
  LikeEntity toEntity(Like like);

  Like toDomain(LikeEntity likeEntity);
}
