package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Follow;

public interface FollowRepository {

  public Follow save(Follow follow);

  public Optional<Follow> findById(String id);

  public List<Follow> findByFollowerId(String followerId);

  public List<Follow> findByFollowingId(String followingId);

  public boolean existsByFollowerIdAndFollowingId(String followerId, String followingId);

  public void deleteByFollowerIdAndFollowingId(String followerId, String followingId);

  public void deleteByFollowerId(String followerId);

  public void deleteByFollowingId(String followingId);

  public void deleteById(String id);

}
