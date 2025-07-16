package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByCardId(Long cardId);
}
