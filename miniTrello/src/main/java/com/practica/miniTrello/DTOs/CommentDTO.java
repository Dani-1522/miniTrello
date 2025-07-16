package com.practica.miniTrello.DTOs;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        String text,
        String author,
        LocalDateTime createdAt
) {}
