package com.raponi.blog.infrastructure.persistence.mapper;

import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.infrastructure.persistence.entity.FollowEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FollowMapper {
  FollowEntity toEntity(Follow follow);

  Follow toDomain(FollowEntity followEntity);
}
