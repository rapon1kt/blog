package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Block;
import com.raponi.blog.infrastructure.persistence.entity.BlockEntity;

@Mapper(componentModel = "spring")
public interface BlockMapper {

  BlockEntity toEntity(Block block);

  Block toDomain(BlockEntity blockEntity);

}
