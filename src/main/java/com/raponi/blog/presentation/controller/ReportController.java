package com.raponi.blog.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.report.CreateReportService;
import com.raponi.blog.application.service.report.FindTargetReportsService;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.presentation.dto.CreateReportRequestDTO;
import com.raponi.blog.presentation.mapper.ReportMapper;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/reports")
public class ReportController {

  private final CreateReportService createReportService;
  private final FindTargetReportsService findTargetReportsService;
  private final ReportMapper reportMapper;

  public ReportController(CreateReportService createReportService, FindTargetReportsService findTargetReportsService,
      ReportMapper reportMapper) {
    this.createReportService = createReportService;
    this.findTargetReportsService = findTargetReportsService;
    this.reportMapper = reportMapper;
  }

  @PostMapping("/{targetId}")
  public ResponseEntity<?> createReport(@PathVariable("targetId") String targetId,
      @RequestBody @Valid CreateReportRequestDTO requestDTO, Authentication auth) {
    Report report = this.createReportService.handle(auth.getName(), targetId, requestDTO.getReason(),
        requestDTO.getReportType());
    return ResponseEntity.status(201).body(this.reportMapper.toResponseDTO(report));
  }

  @GetMapping("/{targetId}")
  public ResponseEntity<?> getTargetReports(@PathVariable("targetId") String targetId, Authentication auth) {
    List<Report> reports = this.findTargetReportsService.handle(auth.getName(), targetId);
    return ResponseEntity.ok(reports.stream().map(reportMapper::toResponseDTO).toList());
  }

}
