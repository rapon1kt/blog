package com.raponi.blog.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.report.CreateReportService;
import com.raponi.blog.presentation.dto.CreateReportRequestDTO;

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
    return ResponseEntity.ok(this.createReportService.handle(auth.getName(), targetId, requestDTO));
  }

}
