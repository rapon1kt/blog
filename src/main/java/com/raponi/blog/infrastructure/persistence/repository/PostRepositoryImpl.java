package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.model.PostVisibility;
import com.raponi.blog.domain.repository.PostRepository;
import com.raponi.blog.infrastructure.persistence.entity.PostEntity;
import com.raponi.blog.presentation.mapper.PostMapper;

@Component
public class PostRepositoryImpl implements PostRepository {

  private final MongoPostRepository mongoRepository;
  private final PostMapper postMapper;

  public PostRepositoryImpl(MongoPostRepository mongoRepository, PostMapper postMapper) {
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
    Optional<PostEntity> postEntity = this.mongoRepository.findById(id);
    return Optional.of(postEntity.map(postMapper::toDomain).orElse(null));
  }

  @Override
  public List<Post> findAll() {
    return this.mongoRepository.findAll().stream().map(postMapper::toDomain).toList();
  }

  @Override
  public List<Post> findByAccountId(String accountId) {
    return this.mongoRepository.findByAccountId(accountId).stream().map(postMapper::toDomain).toList();
  }

  @Override
  public void deleteByAccountId(String accountId) {
    this.mongoRepository.deleteByAccountId(accountId);
  }

  @Override
  public void deleteById(String id) {
    this.mongoRepository.deleteById(id);
  }

  @Override
  public List<Post> findByAccountIdAndPostVisibility(String accountId, PostVisibility postVisibility) {
    return this.mongoRepository.findByAccountIdAndPostVisibility(accountId, postVisibility).stream()
        .map(postMapper::toDomain)
        .toList();
  }

  @Override
  public List<Post> findByPostVisibility(PostVisibility postVisibility) {
    return this.mongoRepository.findByPostVisibility(postVisibility).stream().map(postMapper::toDomain).toList();
  }

}
