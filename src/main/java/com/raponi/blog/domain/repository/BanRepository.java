package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.model.BanStatus;

public interface BanRepository {

  Ban save(Ban ban);

  Optional<Ban> findById(String id);

  List<Ban> findAll();

  List<Ban> findByStatus(BanStatus status);

  Optional<Ban> findTopByBannedIdOrderByBannedAtDesc(String bannedId);

  List<Ban> findByReasonAndStatus(BanReason banReason, BanStatus status);

  List<Ban> findAllByBannedIdOrderByBannedAtDesc(String bannedId);

  long countByBannedId(String bannedId);

}
