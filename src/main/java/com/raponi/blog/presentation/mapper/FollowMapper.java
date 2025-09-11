package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.infrastructure.persistence.entity.FollowEntity;

@Mapper(componentModel = "spring")
public interface FollowMapper {

  FollowEntity toEntity(Follow follow);

  Follow toDomain(FollowEntity followEntity);

}
