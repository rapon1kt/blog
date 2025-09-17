package com.raponi.blog.infrastructure.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.infrastructure.persistence.entity.BlockEntity;

@Repository
public interface MongoBlockRepository extends MongoRepository<BlockEntity, String> {

  public boolean existsByBlockerIdAndBlockedId(String blockerId, String blockedId);

  public void deleteByBlockerIdAndBlockedId(String blockerId, String blockedId);

}
