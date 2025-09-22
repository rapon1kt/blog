package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Like;

public interface LikeRepository {

  public Like save(Like like);

  public Optional<Like> findById(String id);

  public Optional<Like> findByAccountIdAndTargetId(String accountId, String targetId);

  public List<Like> findByAccountId(String accountId);

  public List<Like> findByTargetId(String targetId);

  public boolean existsByTargetIdAndAccountId(String targetId, String accountId);

  public void deleteByTargetIdAndAccountId(String targetId, String accountId);

  public void deleteByAccountId(String accountId);

  public void deleteById(String id);

}
