package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.PostVisibility;
import com.raponi.blog.infrastructure.persistence.entity.PostEntity;

@Repository
public interface MongoPostRepository extends MongoRepository<PostEntity, String> {
  List<PostEntity> findByAuthorId(String authorId);

  List<PostEntity> findByPostVisibility(PostVisibility postVisibility);

  List<PostEntity> findByAuthorIdAndPostVisibility(String authorId, PostVisibility postVisibility);

  List<PostEntity> findByAuthorIdAndPinnedTrue(String authorId);

  void deleteByAuthorId(String authorId);
}
