package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportStatus;
import com.raponi.blog.domain.model.ReportTargetType;
import com.raponi.blog.domain.repository.ReportRepository;
import com.raponi.blog.infrastructure.persistence.entity.ReportEntity;
import com.raponi.blog.presentation.mapper.ReportMapper;

@Component
public class ReportRepositoryImpl implements ReportRepository {

  private final MongoReportRepository mongoRepository;
  private final ReportMapper reportMapper;

  public ReportRepositoryImpl(MongoReportRepository mongoRepository, ReportMapper reportMapper) {
    this.mongoRepository = mongoRepository;
    this.reportMapper = reportMapper;
  }

  @Override
  public void deleteById(String id) {
    this.mongoRepository.deleteById(id);
  }

  @Override
  public void deleteByReporterId(String reporterId) {
    this.mongoRepository.deleteByReporterId(reporterId);
  }

  @Override
  public void deleteByStatus(ReportStatus status) {
    this.mongoRepository.deleteByStatus(status);
  }

  @Override
  public void deleteByTargetId(String targetId) {
    this.mongoRepository.deleteByTargetId(targetId);
  }

  @Override
  public boolean existsByReporterIdAndTargetId(String reporterId, String targetId) {
    return this.existsByReporterIdAndTargetId(reporterId, targetId);
  }

  @Override
  public Optional<Report> findById(String id) {
    Optional<ReportEntity> reportEntity = this.mongoRepository.findById(id);
    return Optional.of(reportEntity.map(reportMapper::toDomain).orElse(null));
  }

  @Override
  public List<Report> findByReportTargetType(ReportTargetType reportTargetType) {
    return this.mongoRepository.findByReportTargetType(reportTargetType).stream().map(reportMapper::toDomain).toList();
  }

  @Override
  public List<Report> findByReporterId(String reporterId) {
    return this.mongoRepository.findByReporterId(reporterId).stream().map(reportMapper::toDomain).toList();
  }

  @Override
  public List<Report> findByStatus(ReportStatus status) {
    return this.mongoRepository.findByStatus(status).stream().map(reportMapper::toDomain).toList();
  }

  @Override
  public List<Report> findByTargetId(String targetId) {
    return this.mongoRepository.findByTargetId(targetId).stream().map(reportMapper::toDomain).toList();
  }

  @Override
  public Report save(Report report) {
    ReportEntity reportEntity = this.reportMapper.toEntity(report);
    ReportEntity savedReportEntity = this.mongoRepository.save(reportEntity);
    return this.reportMapper.toDomain(savedReportEntity);
  }

}
