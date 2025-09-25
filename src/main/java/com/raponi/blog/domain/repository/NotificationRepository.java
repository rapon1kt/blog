package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Notification;

public interface NotificationRepository {
  Notification save(Notification notification);

  List<Notification> findByAuthorId(String authorId);

  Optional<Notification> findByActorIdAndAuthorId(String actorId, String authorId);

  Optional<Notification> findByActorIdAndTargetId(String actorId, String targetId);

  void deleteById(String id);

}
