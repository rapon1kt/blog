package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
  List<Post> findByAccountId(String accountId);

  List<Post> findByPrivateStatus(boolean privateStatus);

  List<Post> findByAccountIdAndPrivateStatusFalse(String accountId);
}
