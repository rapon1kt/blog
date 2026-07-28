package com.raponi.blog.infrastructure.repository;

import com.raponi.blog.domain.model.Block;
import com.raponi.blog.domain.repository.BlockRepository;
import com.raponi.blog.infrastructure.persistence.entity.BlockEntity;
import com.raponi.blog.infrastructure.persistence.mapper.BlockInfraMapper;
import com.raponi.blog.infrastructure.persistence.repository.MongoBlockRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class BlockRepositoryImpl implements BlockRepository {

  private final MongoBlockRepository mongoRepository;
  private final BlockInfraMapper blockMapper;

  public BlockRepositoryImpl(MongoBlockRepository mongoRepository, BlockInfraMapper blockMapper) {
    this.mongoRepository = mongoRepository;
    this.blockMapper = blockMapper;
  }

  @Override
  public void deleteByBlockerIdAndBlockedId(String blockerId, String blockedId) {
    this.mongoRepository.deleteByBlockerIdAndBlockedId(blockerId, blockedId);
  }

  @Override
  public void deleteById(String id) {
    this.mongoRepository.deleteById(id);
  }

  @Override
  public boolean existsByBlockerIdAndBlockedId(String blockerId, String blockedId) {
    return this.mongoRepository.existsByBlockerIdAndBlockedId(blockerId, blockedId);
  }

  @Override
  public Optional<Block> findById(String id) {
    Optional<BlockEntity> blockEntity = this.mongoRepository.findById(id);
    return blockEntity.map(blockMapper::toDomain);
  }

  @Override
  public Block save(Block block) {
    BlockEntity blockEntity = this.blockMapper.toEntity(block);
    BlockEntity savedBlockEntity = this.mongoRepository.save(blockEntity);
    return this.blockMapper.toDomain(savedBlockEntity);
  }
}
