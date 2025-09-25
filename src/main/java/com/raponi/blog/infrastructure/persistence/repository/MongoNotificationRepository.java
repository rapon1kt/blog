package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.NotificationType;
import com.raponi.blog.infrastructure.persistence.entity.NotificationEntity;

@Repository
public interface MongoNotificationRepository extends MongoRepository<NotificationEntity, String> {

  void deleteByAuthorId(String authorId);

  void deleteByActorId(String actorId);

  void deleteByTargetId(String targetId);

  Optional<NotificationEntity> findByActorIdAndTargetIdAndType(String actorId, String targetId, NotificationType type);

  List<NotificationEntity> findByAuthorIdAndReadOrderByCreatedAtDesc(String authorId, boolean read);

  boolean existsByAuthorIdAndActorIdAndType(String authorId, String actorId, NotificationType type);

}
