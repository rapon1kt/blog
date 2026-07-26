package com.raponi.blog.infrastructure.persistence.mapper;

import com.raponi.blog.domain.model.Block;
import com.raponi.blog.infrastructure.persistence.entity.BlockEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlockMapper {
  BlockEntity toEntity(Block block);

  Block toDomain(BlockEntity blockEntity);
}
