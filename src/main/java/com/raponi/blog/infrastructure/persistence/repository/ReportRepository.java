package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportTargetType;
import com.raponi.blog.domain.model.ReportStatus;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {

  List<Report> findByTargetId(String targetId);

  List<Report> findByReporterId(String reporterId);

  List<Report> findByReportType(ReportTargetType reportType);

  List<Report> findByStatus(ReportStatus status);

  boolean existsByReporterIdAndTargetId(String reporterId, String targetId);

  void deleteByStatus(ReportStatus status);

  void deleteByTargetId(String targetId);

  void deleteByReporterId(String reporterId);

}
