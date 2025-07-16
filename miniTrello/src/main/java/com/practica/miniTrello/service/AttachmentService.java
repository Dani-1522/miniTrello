package com.practica.miniTrello.service;

import com.practica.miniTrello.DTOs.AttachmentDTO;
import com.practica.miniTrello.entity.Attachment;
import com.practica.miniTrello.entity.Card;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.repository.AttachmentRepository;
import com.practica.miniTrello.repository.CardRepository;
import com.practica.miniTrello.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AttachmentService {

    @Value("${upload.dir}")
    private String uploadDir;

    private final AttachmentRepository attachmentRepo;
    private final CardRepository cardRepo;
    private final UserRepository userRepo;

    public AttachmentDTO uploadFile(Long cardId, MultipartFile file, String username) throws IOException {
        Card card = cardRepo.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("Card not found"));

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, filename);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        Attachment saved = attachmentRepo.save(Attachment.builder()
                .filename(file.getOriginalFilename())
                .fileType(file.getContentType())
                .filePath(path.toString())
                .uploadedAt(LocalDateTime.now())
                .uploader(user)
                .card(card)
                .build());

        return toDTO(saved);
    }


    public Resource downloadFile(Long attachmentId) throws IOException {
        Attachment attachment = attachmentRepo.findById(attachmentId)
                .orElseThrow(() -> new NoSuchElementException("Attachment not found"));

        Path path = Paths.get(attachment.getFilePath());
        return new UrlResource(path.toUri());
    }

    public List<AttachmentDTO> getCardAttachments(Long cardId) {
        return attachmentRepo.findByCardId(cardId)
                .stream()
                .map(this::toDTO)
                .toList();
    }


    public void deleteAttachment(Long attachmentId, String username) throws IOException {
        Attachment attachment = attachmentRepo.findById(attachmentId)
                .orElseThrow(() -> new NoSuchElementException("Attachment not found"));

        if (!attachment.getUploader().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only delete your own attachments.");
        }

        Files.deleteIfExists(Paths.get(attachment.getFilePath()));
        attachmentRepo.delete(attachment);
    }

    private AttachmentDTO toDTO(Attachment attachment) {
        return new AttachmentDTO(
                attachment.getId(),
                attachment.getFilename(),
                attachment.getFileType(),
                attachment.getUploader().getUsername(),
                attachment.getUploadedAt()
        );
    }

}
