package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.infrastructure.persistence.entity.PostEntity;

@Repository
public interface MongoPostRepository extends MongoRepository<PostEntity, String> {
  List<PostEntity> findByAccountId(String accountId);

  List<PostEntity> findByPrivateStatus(boolean privateStatus);

  List<PostEntity> findByAccountIdAndPrivateStatusFalse(String accountId);

  void deleteByAccountId(String accountId);
}
