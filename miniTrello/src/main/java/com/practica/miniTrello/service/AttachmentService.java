package com.practica.miniTrello.service;


import com.practica.miniTrello.entity.Card;
import com.practica.miniTrello.entity.CardAttachment;
import com.practica.miniTrello.repository.CardActivityRepository;
import com.practica.miniTrello.repository.CardAttachmentRepository;
import com.practica.miniTrello.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final CardActivityRepository cardActivityRepository;
    private final CardRepository cardRepository;

    private final Path rootLocation = Paths.get("storage");
    private final CardAttachmentRepository cardAttachmentRepository;

    @Transactional
    public void uploadAttachment(Long cardId, MultipartFile file, String username) throws IOException {
        Card card = getVerifiedCard(cardId, username);

        if (!Files.exists(rootLocation)) Files.createDirectories(rootLocation);

        String folder = "card-" + card.getId();
        Path cardFolder = rootLocation.resolve(folder);
        if (!Files.exists(cardFolder)) Files.createDirectories(cardFolder);

        String filename = file.getOriginalFilename();
        Path destinantio = cardFolder.resolve(filename);

        Files.copy(file.getInputStream(), destinantio, StandardCopyOption.REPLACE_EXISTING);

        CardAttachment attachment = CardAttachment.builder()
                .card(card)
                .fileName(filename)
                .filepath(destinantio.toString())
                .contentType(file.getContentType())
                .uploadedAt(LocalDateTime.now())
                .build();

        cardAttachmentRepository.save(attachment);
    }

   public List<CardAttachment> getAttachments(Long cardId, String username) {
        getVerifiedCard(cardId, username);
        return cardAttachmentRepository.findByCardId(cardId);
   }

   public void deleteAttachment(Long attachmentId, String username) {
        CardAttachment attachment = cardAttachmentRepository.findById(attachmentId)
                .orElseThrow(()-> new NoSuchElementException("Attachement: " + attachmentId + " not found"));

        if(!attachment.getCard().getBoardList().getBoard().getOwner().getUsername().equals(username)){
            throw new AccessDeniedException("You don't have permission to delete this attachment");
        }
        try {
            Files.deleteIfExists(Paths.get(attachment.getFileName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cardAttachmentRepository.delete(attachment);
   }

    public Card getVerifiedCard(Long cardId, String username) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()-> new NoSuchElementException("Card not found"));

        if (!card.getBoardList().getBoard().getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("You don't have permission to access this resource");
        }
        return card;
    }



}
