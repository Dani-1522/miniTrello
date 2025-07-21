package com.practica.miniTrello.service;

import com.practica.miniTrello.DTOs.CardEventDTO;
import com.practica.miniTrello.DTOs.NotificationDTO;
import com.practica.miniTrello.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealtimeEventService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendCardEvent(CardEventDTO dto, Long boardId) {
        messagingTemplate.convertAndSend("/topic/board/" + boardId + "/cards", dto);
    }

    public void sendNotification(Notification notification) {
        NotificationDTO dto = new NotificationDTO(
                notification.getId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
        messagingTemplate.convertAndSend(
                "/topic/user/" + notification.getRecipient().getUsername() + "/notifications",
                dto
        );
    }



}

