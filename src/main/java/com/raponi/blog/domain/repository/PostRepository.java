package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Post;

public interface PostRepository {

  public Post save(Post post);

  public Optional<Post> findById(String id);

  public List<Post> findAll();

  public List<Post> findByAccountId(String accountId);

  public List<Post> findByPrivateStatus(boolean privateStatus);

  public List<Post> findByAccountIdAndPrivateStatusFalse(String accountId);

  public void deleteById(String id);

  public void deleteByAccountId(String accountId);
}
