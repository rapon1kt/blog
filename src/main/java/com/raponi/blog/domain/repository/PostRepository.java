package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.model.PostVisibility;

public interface PostRepository {

  public Post save(Post post);

  public Optional<Post> findById(String id);

  public List<Post> findAll();

  public List<Post> findByAuthorId(String authorId);

  public List<Post> findByPostVisibility(PostVisibility postVisibility);

  public List<Post> findByAuthorIdAndPostVisibility(String authorId, PostVisibility postVisibility);

  public void deleteById(String id);

  public void deleteByAuthorId(String authorId);
}
