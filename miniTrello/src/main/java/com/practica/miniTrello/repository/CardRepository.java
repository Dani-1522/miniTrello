package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    List<Card> findByBoardListId(Long listId);
    List<Card> findByDueDateBeforeAndBoardList_Board_Owner_Username(LocalDateTime date, String username);
    List<Card> findByDueDateBetweenAndBoardList_Board_Owner_Username(LocalDateTime start, LocalDateTime end, String username);
}
