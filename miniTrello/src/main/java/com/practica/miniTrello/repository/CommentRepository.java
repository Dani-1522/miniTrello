package com.practica.miniTrello.repository;


import com.practica.miniTrello.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
   List<Comment> findByCardIdOrderByCreatedAtAsc(Long cardId);
}
