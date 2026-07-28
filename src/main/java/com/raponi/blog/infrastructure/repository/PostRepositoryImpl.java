package com.raponi.blog.infrastructure.repository;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.model.PostVisibility;
import com.raponi.blog.domain.repository.PostRepository;
import com.raponi.blog.infrastructure.persistence.entity.PostEntity;
import com.raponi.blog.infrastructure.persistence.mapper.PostInfraMapper;
import com.raponi.blog.infrastructure.persistence.repository.MongoPostRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class PostRepositoryImpl implements PostRepository {

  private final MongoPostRepository mongoRepository;
  private final PostInfraMapper postMapper;

  public PostRepositoryImpl(MongoPostRepository mongoRepository, PostInfraMapper postMapper) {
    this.mongoRepository = mongoRepository;
    this.postMapper = postMapper;
  }

  @Override
  public Post save(Post post) {
    PostEntity postEntity = this.postMapper.toEntity(post);
    PostEntity savedEntity = this.mongoRepository.save(postEntity);
    return this.postMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Post> findById(String id) {
    return this.mongoRepository.findById(id).map(postMapper::toDomain);
  }

  @Override
  public List<Post> findAll() {
    return this.mongoRepository.findAll().stream().map(postMapper::toDomain).toList();
  }

  @Override
  public List<Post> findByAuthorId(String authorId) {
    return this.mongoRepository.findByAuthorId(authorId).stream().map(postMapper::toDomain).toList();
  }

  @Override
  public void deleteByAuthorId(String authorId) {
    this.mongoRepository.deleteByAuthorId(authorId);
  }

  @Override
  public List<Post> findByAuthorIdAndPinnedTrue(String authorId) {
    return this.mongoRepository.findByAuthorIdAndPinnedTrue(authorId).stream().map(postMapper::toDomain).toList();
  }

  @Override
  public void deleteById(String id) {
    this.mongoRepository.deleteById(id);
  }

  @Override
  public List<Post> findByAuthorIdAndPostVisibility(String authorId, PostVisibility postVisibility) {
    return this.mongoRepository.findByAuthorIdAndPostVisibility(authorId, postVisibility).stream()
        .map(postMapper::toDomain).toList();
  }

  @Override
  public List<Post> findByPostVisibility(PostVisibility postVisibility) {
    return this.mongoRepository.findByPostVisibility(postVisibility).stream().map(postMapper::toDomain).toList();
  }
}
