package com.raponi.blog.infrastructure.persistence.mapper;

import com.raponi.blog.domain.model.Notification;
import com.raponi.blog.infrastructure.persistence.entity.NotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationInfraMapper {
  Notification toDomain(NotificationEntity notificationEntity);

  NotificationEntity toEntity(Notification notification);
}
