package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.infrastructure.persistence.entity.NotificationEntity;

@Repository
public interface MongoNotificationRepository extends MongoRepository<NotificationEntity, String> {

  List<NotificationEntity> findByAuthorId(String authorId);

  Optional<NotificationEntity> findByActorIdAndAuthorId(String actorId, String authorId);

  Optional<NotificationEntity> findByActorIdAndTargetId(String authorId, String targetId);

}
