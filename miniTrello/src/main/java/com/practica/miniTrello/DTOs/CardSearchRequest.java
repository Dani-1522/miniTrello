package com.practica.miniTrello.DTOs;

import java.time.LocalDate;
import java.util.List;

public record CardSearchRequest(
        String keyword,
        List<String> collaboratorUsernames,
        List<Long> tagIds,
        Long boardId,
        Long listId,
        LocalDate dueBefore,
        LocalDate dueAfter
) {}
