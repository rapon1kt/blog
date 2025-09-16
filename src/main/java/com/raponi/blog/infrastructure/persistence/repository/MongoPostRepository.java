package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.PostVisibility;
import com.raponi.blog.infrastructure.persistence.entity.PostEntity;

@Repository
public interface MongoPostRepository extends MongoRepository<PostEntity, String> {
  List<PostEntity> findByAccountId(String accountId);

  List<PostEntity> findByPostVisibility(PostVisibility postVisibility);

  List<PostEntity> findByAccountIdAndPostVisibility(String accountId, PostVisibility postVisibility);

  void deleteByAccountId(String accountId);
}
