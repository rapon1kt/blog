package com.raponi.blog.application.service.report;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.FindTargetReportsUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.infrastructure.persistence.repository.ReportRepository;
import com.raponi.blog.presentation.dto.ReportResponseDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.mapper.ReportMapper;

@Service
public class FindTargetReportsService implements FindTargetReportsUseCase {

  private final AccountValidatorService accountValidatorService;
  private final ReportRepository reportRepository;
  private final ReportMapper reportMapper;

  public FindTargetReportsService(AccountValidatorService accountValidatorService, ReportRepository reportRepository,
      ReportMapper reportMapper) {
    this.accountValidatorService = accountValidatorService;
    this.reportRepository = reportRepository;
    this.reportMapper = reportMapper;
  }

  @Override
  public List<ReportResponseDTO> handle(String accountId, String targetId) {
    boolean isAdmin = this.accountValidatorService.isAdmin();
    if (isAdmin) {
      return this.reportRepository.findByTargetId(targetId).stream().map(reportMapper::toResponseDTO).toList();
    }
    throw new AccessDeniedException("You don't have permission to do this.");
  }

}
