package com.practica.miniTrello.contotroller;


import com.practica.miniTrello.entity.CardAttachment;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.service.AttachmentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/cards/{cardId}/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping
    public ResponseEntity<?> uploadAttachment(@PathVariable Long cardId,
                                              @RequestParam("file")MultipartFile file,
                                              @AuthenticationPrincipal User user) throws IOException {
        attachmentService.uploadAttachment(cardId, file, user.getUsername());
        return ResponseEntity.ok("archivo subido");
    }

    @GetMapping
    public ResponseEntity<List<CardAttachment>> getAttachments(@PathVariable Long cardId,
                                                               @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(attachmentService.getAttachments(cardId, user.getUsername()));
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<?> deleteAttachment(@PathVariable Long cardId,
                                              @PathVariable Long attachmentId,
                                              @AuthenticationPrincipal User user) {
        attachmentService.deleteAttachment(attachmentId, user.getUsername());
        return ResponseEntity.noContent().build();

    }
}
