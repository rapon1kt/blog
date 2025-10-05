package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.BanCategory;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.infrastructure.persistence.entity.BanEntity;

@Repository
public interface MongoBanRepository extends MongoRepository<BanEntity, String> {

  List<BanEntity> findByActiveTrue(boolean active);

  Optional<BanEntity> findByBannedIdAndActiveTrue(String bannedId);

  List<BanEntity> findByCategoryAndActiveTrue(BanCategory category);

  List<BanEntity> findByReasonAndActiveTrue(BanReason banReason);

  List<BanEntity> findAllByBannedIdOrderByActiveDesc(String bannedId);

  long countByBannedId(String bannedId);

}
