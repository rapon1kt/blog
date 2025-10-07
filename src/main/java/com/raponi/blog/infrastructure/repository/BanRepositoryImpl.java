package com.raponi.blog.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanCategory;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.model.BanStatus;
import com.raponi.blog.domain.repository.BanRepository;
import com.raponi.blog.infrastructure.persistence.entity.BanEntity;
import com.raponi.blog.infrastructure.persistence.repository.MongoBanRepository;
import com.raponi.blog.presentation.mapper.BanMapper;

@Component
public class BanRepositoryImpl implements BanRepository {

  private final MongoBanRepository mongoRepository;
  private final BanMapper banMapper;

  public BanRepositoryImpl(MongoBanRepository mongoBanRepository, BanMapper banMapper) {
    this.mongoRepository = mongoBanRepository;
    this.banMapper = banMapper;
  }

  @Override
  public long countByBannedId(String bannedId) {
    return this.mongoRepository.countByBannedId(bannedId);
  }

  @Override
  public List<Ban> findAllByBannedIdOrderByStatusDesc(String bannedId, BanStatus status) {
    return this.mongoRepository.findAllByBannedIdOrderByStatusDesc(bannedId, status).stream().map(banMapper::toDomain)
        .toList();
  }

  @Override
  public List<Ban> findByStatus(BanStatus status) {
    return this.mongoRepository.findByStatus(status).stream().map(banMapper::toDomain).toList();
  }

  @Override
  public List<Ban> findByCategoryAndStatus(BanCategory category, BanStatus status) {
    return this.mongoRepository.findByCategoryAndStatus(category, status).stream().map(banMapper::toDomain).toList();
  }

  @Override
  public Optional<Ban> findTopByBannedIdAndOrderByBannedAt(String bannedId) {
    Optional<BanEntity> entity = this.mongoRepository.findTopByBannedIdAndOrderByBannedAt(bannedId);
    return entity.map(banMapper::toDomain);
  }

  @Override
  public Optional<Ban> findById(String id) {
    Optional<BanEntity> entity = this.mongoRepository.findById(id);
    return entity.map(banMapper::toDomain);
  }

  @Override
  public List<Ban> findByReasonAndStatus(BanReason banReason, BanStatus status) {
    return this.mongoRepository.findByReasonAndStatus(banReason, status).stream().map(banMapper::toDomain).toList();
  }

  @Override
  public Ban save(Ban ban) {
    BanEntity entity = this.banMapper.toEntity(ban);
    BanEntity savedEntity = this.mongoRepository.save(entity);
    return this.banMapper.toDomain(savedEntity);
  }

}
