package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Notification;
import com.raponi.blog.infrastructure.persistence.entity.NotificationEntity;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

  Notification toDomain(NotificationEntity notificationEntity);

  NotificationEntity toEntity(Notification notification);

}
