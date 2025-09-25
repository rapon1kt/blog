package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Notification;
import com.raponi.blog.domain.model.NotificationType;

public interface NotificationRepository {

  Notification save(Notification notification);

  void deleteById(String id);

  Optional<Notification> findByActorIdAndTargetIdAndType(String actorId, String targetId, NotificationType type);

  List<Notification> findByAuthorIdAndReadOrderByCreatedAtDesc(String authorId, boolean read);

  boolean existsByAuthorIdAndActorIdAndType(String authorId, String actorId, NotificationType type);

  void deleteByAuthorId(String authorId);

  void deleteByActorId(String actorId);

  void deleteByTargetId(String targetId);

}
