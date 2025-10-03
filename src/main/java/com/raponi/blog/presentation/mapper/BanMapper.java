package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.infrastructure.persistence.entity.BanEntity;

@Mapper(componentModel = "spring")
public interface BanMapper {

  BanEntity toEntity(Ban ban);

  Ban toDomain(BanEntity banEntity);

}
