package com.practica.miniTrello.service;


import com.practica.miniTrello.DTOs.NotificationDTO;
import com.practica.miniTrello.entity.Notification;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.repository.NotificationRepository;
import com.practica.miniTrello.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final RealtimeEventService realtimeEventService;

    public void notifyUser(String username, String message) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        Notification notification = Notification.builder()
                .message(message)
                .recipient(user)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        Notification saved = notificationRepository.save(notification);

        realtimeEventService.sendNotification(saved); // envío en tiempo real
    }

    public List<NotificationDTO> getUserNotifications(String username) {
        return notificationRepository.findByRecipient_UsernameOrderByCreatedAtDesc(username)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public void markAsRead(Long notificationId, String username) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NoSuchElementException("Notification no encontrado"));

        if (!notification.getRecipient().getUsername().equals(username)) {
            throw new AccessDeniedException("Not your notification");
        }
        notification.setRead(true);
        notificationRepository.save(notification);
    }
    private NotificationDTO toDTO(Notification notif) {
        return new NotificationDTO(
                notif.getId(),
                notif.getMessage(),
                notif.isRead(),
                notif.getCreatedAt()
        );
    }

}
