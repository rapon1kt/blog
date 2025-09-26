package com.raponi.blog.application.service.notification;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Notification;
import com.raponi.blog.domain.model.NotificationType;
import com.raponi.blog.domain.repository.NotificationRepository;

@Service
public class CreateNotificationService {

  private final NotificationRepository notificationRepository;

  public CreateNotificationService(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  public void handle(String authorId, String actorId, NotificationType type, String targetId) {
    Optional<Notification> optional = this.notificationRepository
        .findByActorIdAndTargetIdAndType(actorId, targetId, type);
    if (!authorId.equals(actorId)) {
      Notification notification = Notification.create(authorId, actorId, type,
          targetId);
      if (optional.isPresent()) {
        notification.setTargetId(optional.get().getTargetId());
        this.notificationRepository.save(notification);
        this.notificationRepository.deleteById(optional.get().getId());
        return;
      }
      this.notificationRepository.save(notification);
    }
  }

}
