package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.infrastructure.persistence.entity.LikeEntity;

@Repository
public interface MongoLikeRepository extends MongoRepository<LikeEntity, String> {

  boolean existsByTargetIdAndAccountId(String targetId, String accountId);

  Optional<LikeEntity> findByAccountIdAndTargetId(String accountId, String targetId);

  void deleteByTargetIdAndAccountId(String targetId, String accountId);

  void deleteByAccountId(String accountId);

  List<LikeEntity> findByAccountId(String accountId);

  List<LikeEntity> findByTargetId(String targetId);

}
