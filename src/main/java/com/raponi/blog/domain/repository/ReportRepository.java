package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportStatus;
import com.raponi.blog.domain.model.ReportTargetType;

public interface ReportRepository {

  public Report save(Report report);

  public void deleteById(String id);

  public Optional<Report> findById(String id);

  public List<Report> findByTargetId(String targetId);

  public List<Report> findByReporterId(String reporterId);

  public List<Report> findByReportTargetType(ReportTargetType reportTargetType);

  public List<Report> findByStatus(ReportStatus status);

  public boolean existsByReporterIdAndTargetId(String reporterId, String targetId);

  public void deleteByStatus(ReportStatus status);

  public void deleteByTargetId(String targetId);

  public void deleteByReporterId(String reporterId);

}
