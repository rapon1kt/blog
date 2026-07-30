package com.raponi.blog.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.report.*;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.presentation.dto.request.CreateReportRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reports")
public class ReportController {

  private final CreateReportService createReportService;

  public ReportController(CreateReportService createReportService) {
    this.createReportService = createReportService;
  }

  @PostMapping("/{targetId}")
  public ResponseEntity<?> createReport(@PathVariable("targetId") String targetId,
      @RequestBody @Valid CreateReportRequestDTO requestDTO, Authentication auth) {
    Report report = this.createReportService.handle(auth.getName(), targetId, requestDTO.getReason(),
        requestDTO.getReportType());
    return ResponseEntity.status(201).body(report);
  }

  // TO IMPLEMENT
  //
  // @GetMapping
  // public String getFilteredReports(@RequestParam ReportStatus status,
  // @RequestParam ReportTargetType type,
  // @RequestParam String targetId, @RequestParam String reporterId) {
  // return ResponseEntity.ok();
  // }

}
