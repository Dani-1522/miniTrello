package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByBoardListId(Long listId);
}
