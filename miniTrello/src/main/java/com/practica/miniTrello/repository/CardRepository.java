package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByBoardListId(Long listId);
    List<Card> findByDueDateBeforeAndBoardList_Board_Owner_Username(LocalDateTime date, String username);
    List<Card> findByDueDateBetweenAndBoardList_Board_Owner_Username(LocalDateTime start, LocalDateTime end, String username);
}
