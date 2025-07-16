package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.DTOs.CommentDTO;
import com.practica.miniTrello.entity.Comment;
import com.practica.miniTrello.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{cardId}")
    public ResponseEntity<CommentDTO> add(@PathVariable Long cardId,
                                          @RequestBody Map<String, String> body,
                                          Authentication auth) {
        return ResponseEntity.ok(
                commentService.addComment(cardId, body.get("text"), auth.getName())
        );
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<List<CommentDTO>> list(@PathVariable Long cardId) {
        return ResponseEntity.ok(commentService.getComments(cardId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> update(@PathVariable Long commentId,
                                             @RequestBody Map<String, String> body,
                                             Authentication auth) {
        return ResponseEntity.ok(commentService.UpdateComment(commentId, body.get("text"), auth.getName()));
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentDTO> delete(@PathVariable Long commentId,
                                             Authentication auth) {
        commentService.deleteComment(commentId, auth.getName());
        return ResponseEntity.ok().build();
    }

}
