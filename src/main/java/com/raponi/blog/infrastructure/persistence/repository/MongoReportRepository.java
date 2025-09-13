package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.ReportTargetType;
import com.raponi.blog.infrastructure.persistence.entity.ReportEntity;
import com.raponi.blog.domain.model.ReportStatus;

@Repository
public interface MongoReportRepository extends MongoRepository<ReportEntity, String> {

  List<ReportEntity> findByTargetId(String targetId);

  List<ReportEntity> findByReporterId(String reporterId);

  List<ReportEntity> findByReportTargetType(ReportTargetType reportTargetType);

  List<ReportEntity> findByStatus(ReportStatus status);

  boolean existsByReporterIdAndTargetId(String reporterId, String targetId);

  boolean existsByTargetIdAndStatus(String targetId, ReportStatus status);

  void deleteByStatus(ReportStatus status);

  void deleteByTargetId(String targetId);

  void deleteByReporterId(String reporterId);

}
