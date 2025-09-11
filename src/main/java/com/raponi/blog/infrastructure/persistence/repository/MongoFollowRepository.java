package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.infrastructure.persistence.entity.FollowEntity;

@Repository
public interface MongoFollowRepository extends MongoRepository<FollowEntity, String> {

  List<FollowEntity> findByFollowerId(String followerId);

  List<FollowEntity> findByFollowingId(String followingId);

  boolean existsByFollowerIdAndFollowingId(String followerId, String followingId);

  void deleteByFollowerIdAndFollowingId(String followerId, String followingId);

  void deleteByFollowerId(String followerId);

  void deleteByFollowingId(String followingId);

}
