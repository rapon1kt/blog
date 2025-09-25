package com.raponi.blog.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.raponi.blog.domain.model.Notification;
import com.raponi.blog.domain.model.NotificationType;
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
  public boolean existsByAuthorIdAndActorIdAndType(String actorId, String authorId, NotificationType type) {
    return this.mongoNotificationRepository.existsByAuthorIdAndActorIdAndType(authorId, actorId, type);
  }

  @Override
  public List<Notification> findByAuthorIdAndReadOrderByCreatedAtDesc(String authorId, boolean read) {
    return this.mongoNotificationRepository.findByAuthorIdAndReadOrderByCreatedAtDesc(authorId, read).stream()
        .map(notificationMapper::toDomain)
        .toList();
  }

  @Override
  public Optional<Notification> findByActorIdAndTargetIdAndType(String authorId, String targetId,
      NotificationType type) {
    Optional<NotificationEntity> notificationEntity = this.mongoNotificationRepository.findByActorIdAndTargetIdAndType(
        authorId, targetId, type);
    return notificationEntity.map(notificationMapper::toDomain);
  }

  @Override
  public void deleteById(String id) {
    this.mongoNotificationRepository.deleteById(id);
  }

  @Override
  public void deleteByActorId(String actorId) {
    this.mongoNotificationRepository.deleteByActorId(actorId);
  }

  @Override
  public void deleteByAuthorId(String authorId) {
    this.mongoNotificationRepository.deleteByAuthorId(authorId);
  }

  @Override
  public void deleteByTargetId(String id) {
    this.mongoNotificationRepository.deleteByTargetId(id);
  }

}
