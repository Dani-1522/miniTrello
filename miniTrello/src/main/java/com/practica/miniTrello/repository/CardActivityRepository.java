package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.CardActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardActivityRepository extends JpaRepository<CardActivity, Long> {
    List<CardActivity> findByCardIdOrderByTimestampDesc(Long cardId);
}
