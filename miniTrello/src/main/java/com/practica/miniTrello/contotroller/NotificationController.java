package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.DTOs.NotificationDTO;
import com.practica.miniTrello.entity.Notification;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getNotifications(@AuthenticationPrincipal User user) {
        return notificationService.getUserNotifications(user.getUsername());
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        notificationService.markAsRead(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
