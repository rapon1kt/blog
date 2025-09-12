package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Like;
import com.raponi.blog.infrastructure.persistence.entity.LikeEntity;

@Mapper(componentModel = "spring")
public interface LikeMapper {

  LikeEntity toEntity(Like like);

  Like toDomain(LikeEntity likeEntity);

}
