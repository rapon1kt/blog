package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.raponi.blog.domain.model.Like;
import com.raponi.blog.domain.repository.LikeRepository;
import com.raponi.blog.infrastructure.persistence.entity.LikeEntity;
import com.raponi.blog.presentation.mapper.LikeMapper;

@Component
public class LikeRepositoryImpl implements LikeRepository {

  private final MongoLikeRepository mongoRepository;
  private final LikeMapper likeMapper;

  public LikeRepositoryImpl(MongoLikeRepository mongoRepository, LikeMapper likeMapper) {
    this.mongoRepository = mongoRepository;
    this.likeMapper = likeMapper;
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
  public List<Like> findByTargetId(String targetId) {
    return this.mongoRepository.findByTargetId(targetId).stream().map(likeMapper::toDomain).toList();
  }

  @Override
  public void deleteByTargetIdAndAccountId(String targetId, String accountId) {
    this.mongoRepository.deleteByTargetIdAndAccountId(targetId, accountId);
  }

  @Override
  public boolean existsByTargetIdAndAccountId(String targetId, String accountId) {
    return this.mongoRepository.existsByTargetIdAndAccountId(targetId, accountId);
  }

  @Override
  public List<Like> findByAccountId(String accountId) {
    return this.mongoRepository.findByAccountId(accountId).stream().map(likeMapper::toDomain).toList();
  }

  @Override
  public Optional<Like> findById(String id) {
    Optional<LikeEntity> likeEntity = this.mongoRepository.findById(id);
    return Optional.of(likeEntity.map(likeMapper::toDomain).orElse(null));
  }

  @Override
  public Like save(Like like) {
    LikeEntity likeEntity = this.likeMapper.toEntity(like);
    LikeEntity savedLikeEntity = this.mongoRepository.save(likeEntity);
    return this.likeMapper.toDomain(savedLikeEntity);
  }

}
