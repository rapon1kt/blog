package com.raponi.blog.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.domain.repository.FollowRepository;
import com.raponi.blog.infrastructure.persistence.entity.FollowEntity;
import com.raponi.blog.infrastructure.persistence.repository.MongoFollowRepository;
import com.raponi.blog.presentation.mapper.FollowMapper;

@Component
public class FollowRepositoryImpl implements FollowRepository {

  private final MongoFollowRepository mongoRepository;
  private final FollowMapper followMapper;

  public FollowRepositoryImpl(MongoFollowRepository mongoRepository, FollowMapper followMapper) {
    this.mongoRepository = mongoRepository;
    this.followMapper = followMapper;
  }

  @Override
  public void deleteByFollowerIdAndFollowingId(String followerId, String followingId) {
    this.mongoRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);

  }

  @Override
  public void deleteByFollowerId(String followerId) {
    this.mongoRepository.deleteByFollowerId(followerId);
  }

  @Override
  public void deleteByFollowingId(String followingId) {
    this.mongoRepository.deleteByFollowingId(followingId);
  }

  @Override
  public void deleteById(String id) {
    this.mongoRepository.deleteById(id);
  }

  @Override
  public boolean existsByFollowerIdAndFollowingId(String followerId, String followingId) {
    return this.mongoRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
  }

  @Override
  public List<Follow> findByFollowerId(String followerId) {
    return this.mongoRepository.findByFollowerId(followerId).stream().map(followMapper::toDomain).toList();
  }

  @Override
  public List<Follow> findByFollowingId(String followingId) {
    return this.mongoRepository.findByFollowingId(followingId).stream().map(followMapper::toDomain).toList();
  }

  @Override
  public Optional<Follow> findById(String id) {
    Optional<FollowEntity> followEntity = this.mongoRepository.findById(id);
    return Optional.of(followEntity.map(followMapper::toDomain).orElse(null));
  }

  @Override
  public Follow save(Follow follow) {
    FollowEntity followEntity = this.followMapper.toEntity(follow);
    FollowEntity savedFollow = this.mongoRepository.save(followEntity);
    return this.followMapper.toDomain(savedFollow);
  }

}
