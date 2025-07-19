package com.practica.miniTrello.service;


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

    public void notifyUser(String useername,String message) {
        User user = userRepository.findByUsername(useername)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        Notification notification = Notification.builder()
                .message(message)
                .recipient(user)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(String username) {
        return notificationRepository.findByRecipient_UsernameOrderByCreatedAtDesc(username);
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
}
