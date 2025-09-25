package com.raponi.blog.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.raponi.blog.domain.model.Notification;
import com.raponi.blog.domain.repository.NotificationRepository;
import com.raponi.blog.infrastructure.persistence.entity.NotificationEntity;
import com.raponi.blog.infrastructure.persistence.repository.MongoNotificationRepository;
import com.raponi.blog.presentation.mapper.NotificationMapper;

@Component
public class NotificationRepositoryImpl implements NotificationRepository {

  private final MongoNotificationRepository mongoNotificationRepository;
  private final NotificationMapper notificationMapper;

  public NotificationRepositoryImpl(MongoNotificationRepository mongoNotificationRepository,
      NotificationMapper notificationMapper) {
    this.mongoNotificationRepository = mongoNotificationRepository;
    this.notificationMapper = notificationMapper;

  }

  @Override
  public Notification save(Notification notification) {
    NotificationEntity notificationEntity = this.notificationMapper.toEntity(notification);
    NotificationEntity savedNotificationEntity = this.mongoNotificationRepository.save(notificationEntity);
    return this.notificationMapper.toDomain(savedNotificationEntity);
  }

  @Override
  public Optional<Notification> findByActorIdAndAuthorId(String actorId, String authorId) {
    Optional<NotificationEntity> notificationEntity = this.mongoNotificationRepository
        .findByActorIdAndAuthorId(actorId, authorId);
    return notificationEntity.map(notificationMapper::toDomain);
  }

  @Override
  public List<Notification> findByAuthorId(String authorId) {
    return this.mongoNotificationRepository.findByAuthorId(authorId).stream()
        .map(notificationMapper::toDomain)
        .toList();
  }

  @Override
  public Optional<Notification> findByActorIdAndTargetId(String authorId, String targetId) {
    Optional<NotificationEntity> notificationEntity = this.mongoNotificationRepository.findByActorIdAndTargetId(
        authorId,
        targetId);
    return notificationEntity.map(notificationMapper::toDomain);
  }

  @Override
  public void deleteById(String id) {
    this.mongoNotificationRepository.deleteById(id);
  }

}
