package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.DTOs.AttachmentDTO;
import com.practica.miniTrello.entity.Attachment;
import com.practica.miniTrello.service.AttachmentService;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/{cardId}")
    public ResponseEntity<AttachmentDTO> upload(@PathVariable Long cardId,
                                                @RequestParam("file") MultipartFile file,
                                                Authentication auth) throws IOException {
        return ResponseEntity.ok(attachmentService.uploadFile(cardId, file, auth.getName()));
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<AttachmentDTO>> list(@PathVariable Long cardId) {
        return ResponseEntity.ok(attachmentService.getCardAttachments(cardId));
    }

    @GetMapping("/{attachmentId}/download")
    public ResponseEntity<Resource> download(@PathVariable Long attachmentId) throws IOException {
        Resource file = attachmentService.downloadFile(attachmentId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<Void> delete(@PathVariable Long attachmentId,
                                       Authentication auth) throws IOException {
        attachmentService.deleteAttachment(attachmentId, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
