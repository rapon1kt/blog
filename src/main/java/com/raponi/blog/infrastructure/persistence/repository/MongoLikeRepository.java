package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.infrastructure.persistence.entity.LikeEntity;

@Repository
public interface MongoLikeRepository extends MongoRepository<LikeEntity, String> {
  long countByPostId(String postId);

  boolean existsByPostIdAndAccountId(String postId, String accountId);

  void deleteByPostIdAndAccountId(String postId, String accountId);

  void deleteByAccountId(String accountId);

  List<LikeEntity> findByAccountId(String accountId);

}
