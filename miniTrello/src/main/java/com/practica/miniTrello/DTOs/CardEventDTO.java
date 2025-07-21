package com.practica.miniTrello.DTOs;

public record CardEventDTO(
        String type,     // e.g., "CREATED", "UPDATED", "MOVED"
        Long cardId,
        Long listId,
        String title,
        String triggeredBy
) {}
