package com.raponi.blog.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.report.*;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportStatus;
import com.raponi.blog.domain.model.ReportTargetType;
import com.raponi.blog.presentation.dto.request.CreateReportRequestDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/reports")
public class ReportController {

  private final CreateReportService createReportService;
  private final FindReportByIdService findReportByIdService;
  private final DeleteReportByIdService deleteReportByIdService;
  private final FindTargetReportsService findTargetReportsService;
  private final UpdateReportStatusService updateReportStatusService;
  private final FindReportsByStatusService findReportsByStatusService;
  private final DeleteReportsByStatusService deleteReportsByStatusService;
  private final FindReportsByTargetTypeService findReportsByTargetTypeService;
  private final FindReportsByReporterIdService findReportsByReporterIdService;
  private final DeleteReportsByTargetIdService deleteReportsByTargetIdService;

  public ReportController(
      CreateReportService createReportService,
      FindReportByIdService findReportByIdService,
      FindReportsByReporterIdService findReportsByReporterIdService,
      FindReportsByStatusService findReportsByStatusService,
      FindReportsByTargetTypeService findReportsByTargetTypeService,
      UpdateReportStatusService updateReportStatusService,
      DeleteReportByIdService deleteReportByIdService,
      FindTargetReportsService findTargetReportsService,
      DeleteReportsByStatusService deleteReportsByStatusService,
      DeleteReportsByTargetIdService deleteReportsByTargetIdService) {
    this.createReportService = createReportService;
    this.findReportByIdService = findReportByIdService;
    this.deleteReportByIdService = deleteReportByIdService;
    this.findTargetReportsService = findTargetReportsService;
    this.updateReportStatusService = updateReportStatusService;
    this.findReportsByStatusService = findReportsByStatusService;
    this.deleteReportsByStatusService = deleteReportsByStatusService;
    this.findReportsByReporterIdService = findReportsByReporterIdService;
    this.findReportsByTargetTypeService = findReportsByTargetTypeService;
    this.deleteReportsByTargetIdService = deleteReportsByTargetIdService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getReportById(@PathVariable("id") String id) {
    Report report = this.findReportByIdService.handle(id);
    return ResponseEntity.ok(report);
  }

  @GetMapping("/reporter")
  public ResponseEntity<?> getReportsByReporterId(@RequestParam("id") String id) {
    List<Report> reports = this.findReportsByReporterIdService.handle(id);
    return ResponseEntity.ok(reports);
  }

  @GetMapping("/status")
  public ResponseEntity<?> getReportsByStatus(@RequestParam("status") ReportStatus status) {
    List<Report> reports = this.findReportsByStatusService.handle(status);
    return ResponseEntity.ok(reports);
  }

  @GetMapping("/type")
  public ResponseEntity<?> getReportsByTargetType(@RequestParam("type") ReportTargetType type) {
    List<Report> reports = this.findReportsByTargetTypeService.handle(type);
    return ResponseEntity.ok(reports);
  }

  @GetMapping("/target")
  public ResponseEntity<?> getTargetReports(@RequestParam("id") String id, Authentication auth) {
    List<Report> reports = this.findTargetReportsService.handle(auth.getName(), id);
    return ResponseEntity.ok(reports);
  }

  @PostMapping("/{targetId}")
  public ResponseEntity<?> createReport(@PathVariable("targetId") String targetId,
      @RequestBody @Valid CreateReportRequestDTO requestDTO, Authentication auth) {
    Report report = this.createReportService.handle(auth.getName(), targetId, requestDTO.getReason(),
        requestDTO.getReportType());
    return ResponseEntity.status(201).body(report);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateReportStatus(@PathVariable("id") String id,
      @RequestParam("status") ReportStatus status) {
    Report report = this.updateReportStatusService.handle(id, status);
    return ResponseEntity.ok(report);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteReportById(@PathVariable("id") String id) {
    return ResponseEntity.ok(this.deleteReportByIdService.handle(id));
  }

  @DeleteMapping("/status")
  public ResponseEntity<?> deleteReportByStatus(@RequestParam("status") ReportStatus status) {
    return ResponseEntity.ok(this.deleteReportsByStatusService.handle(status));
  }

  @DeleteMapping("/target")
  public ResponseEntity<?> deleteReportByTargetId(@RequestParam("id") String id, @RequestParam ReportTargetType type) {
    return ResponseEntity.ok(this.deleteReportsByTargetIdService.handle(id, type));
  }

}
