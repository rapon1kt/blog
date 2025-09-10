package com.raponi.blog.domain.repository;

import java.util.Optional;

import com.raponi.blog.domain.model.ReportTargetType;
import com.raponi.blog.domain.model.Reportable;

public interface ReportableRepository {
  Optional<Reportable> findById(String targetId, ReportTargetType reportTargetType);
}
