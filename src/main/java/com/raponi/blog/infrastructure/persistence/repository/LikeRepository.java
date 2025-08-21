package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.Like;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
  long countByPostId(String postId);

  boolean existsByPostIdAndAccountId(String postId, String accountId);

  void deleteByPostIdAndAccountId(String postId, String accountId);

  List<Like> findByAccountId(String accountId);

}
