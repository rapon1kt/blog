package com.raponi.blog.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.infrastructure.persistence.entity.ReportEntity;

@Mapper(componentModel = "spring")
public interface ReportInfraMapper {
  ReportEntity toEntity(Report report);

  Report toDomain(ReportEntity entity);
}
