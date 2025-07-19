package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.CardAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardAttachmentRepository extends JpaRepository<CardAttachment, Long> {
    List<CardAttachment> findByCardId(Long cardId);
}
