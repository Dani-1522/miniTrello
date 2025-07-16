package com.practica.miniTrello.service;

import com.practica.miniTrello.DTOs.CommentDTO;
import com.practica.miniTrello.entity.Card;
import com.practica.miniTrello.entity.Comment;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.repository.CardRepository;
import com.practica.miniTrello.repository.CommentRepository;
import com.practica.miniTrello.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepo;
    private final CardRepository cardRepo;
    private final UserRepository userRepo;

    public List<CommentDTO> getComments(Long cardId) {
        return commentRepo.findByCardIdOrderByCreatedAtAsc(cardId)
                .stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getText(),
                        comment.getAuthor().getUsername(),
                        comment.getCreatedAt()
                ))
                .toList();
    }

    public CommentDTO addComment(Long cardId, String text, String username){

        Card card = cardRepo.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("Card not found"));

        User user = userRepo.findByUsername(username)
                .orElseThrow(()-> new NoSuchElementException("user not found"));

        Comment comment = Comment.builder()
                .text(text)
                .card(card)
                .author(user)
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved =  commentRepo.save(comment);
        return new CommentDTO(saved.getId(), saved.getText(), user.getUsername(), saved.getCreatedAt());
    }

    public CommentDTO UpdateComment(Long commentId, String newText, String username){
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException("username not match");
        }

        comment.setText(newText);
        commentRepo.save(comment);
        return new CommentDTO(comment.getId(), newText, username, comment.getCreatedAt());
    }

    public void deleteComment(Long commentId, String username){
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException("username not match");
        }
        commentRepo.delete(comment);
    }

}
