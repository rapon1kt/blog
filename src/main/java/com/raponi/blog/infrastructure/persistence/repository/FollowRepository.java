package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.raponi.blog.domain.model.Follow;

public interface FollowRepository extends MongoRepository<Follow, String> {

  List<Follow> findByFollowerId(String followerId);

  List<Follow> findByFollowingId(String followingId);

  boolean existsByFollowerIdAndFollowingId(String followerId, String followingId);

  void deleteByFollowerIdAndFollowingId(String followerId, String followingId);

  void deleteByFollowerId(String followerId);

  void deleteByFollowingId(String followingId);

}
