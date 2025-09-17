package com.raponi.blog.domain.repository;

import java.util.Optional;

import com.raponi.blog.domain.model.Block;

public interface BlockRepository {

  public Block save(Block block);

  public Optional<Block> findById(String id);

  public boolean existsByBlockerIdAndBlockedId(String blockerId, String blockedId);

  public void deleteByBlockerIdAndBlockedId(String blockerId, String blockedId);

  public void deleteById(String id);

}
