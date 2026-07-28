package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.model.BanStatus;
import com.raponi.blog.infrastructure.persistence.entity.BanEntity;

public interface MongoBanRepository extends MongoRepository<BanEntity, String> {

  List<BanEntity> findByStatus(BanStatus status);

  Optional<BanEntity> findTopByBannedIdOrderByBannedAtDesc(String bannedId);

  List<BanEntity> findByReasonAndStatus(BanReason banReason, BanStatus status);

  List<BanEntity> findAllByBannedIdOrderByBannedAtDesc(String bannedId);

  long countByBannedId(String bannedId);

}
