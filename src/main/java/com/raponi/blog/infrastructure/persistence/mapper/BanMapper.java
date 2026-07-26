package com.raponi.blog.infrastructure.persistence.mapper;

import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.infrastructure.persistence.entity.BanEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BanMapper {
  BanEntity toEntity(Ban ban);

  Ban toDomain(BanEntity banEntity);
}
