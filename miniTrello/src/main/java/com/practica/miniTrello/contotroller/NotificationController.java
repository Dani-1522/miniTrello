package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.entity.Notification;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> getNotifications(@AuthenticationPrincipal User user) {
        return notificationService.getUserNotifications(user.getUsername());
    }
    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long Id, @AuthenticationPrincipal User user) {
        notificationService.markAsRead(Id, user.getUsername());
    }
}
