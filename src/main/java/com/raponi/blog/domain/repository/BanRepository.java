package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanCategory;
import com.raponi.blog.domain.model.BanReason;

public interface BanRepository {

  Ban save(Ban ban);

  Optional<Ban> findById(String id);

  List<Ban> findByActiveTrue(boolean active);

  Optional<Ban> findByBannedIdAndActiveTrue(String bannedId);

  List<Ban> findByCategoryAndActiveTrue(BanCategory category);

  List<Ban> findByReasonAndActiveTrue(BanReason banReason);

  List<Ban> findAllByBannedIdOrderByActiveDesc(String bannedId);

}
