package com.practica.miniTrello.DTOs;

import java.time.LocalDateTime;

public record NotificationDTO(
        Long id,
        String message,
        boolean read,
        LocalDateTime createdAt
) {}

