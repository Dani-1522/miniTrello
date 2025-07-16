package com.practica.miniTrello.DTOs;

import java.time.LocalDateTime;

public record AttachmentDTO(
        Long id,
        String filename,
        String fileType,
        String uploader,
        LocalDateTime uploadedAt
) {}
