package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Like;

public interface LikeRepository {

  public Like save(Like like);

  public Optional<Like> findById(String id);

  public List<Like> findByAccountId(String accountId);

  public boolean existsByPostIdAndAccountId(String postId, String accountId);

  public void deleteByPostIdAndAccountId(String postId, String accountId);

  public void deleteByAccountId(String accountId);

  public void deleteById(String id);

}
