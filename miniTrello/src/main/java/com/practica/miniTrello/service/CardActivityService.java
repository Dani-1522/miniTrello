package com.practica.miniTrello.service;

import com.practica.miniTrello.entity.Card;
import com.practica.miniTrello.entity.CardActivity;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.repository.CardActivityRepository;
import com.practica.miniTrello.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CardActivityService {

    private final CardActivityRepository activityRepo;
    private final CardRepository cardRepo;

    public void logActivity(Long cardId, String description, User user) {
        Card card = cardRepo.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("Card not found"));

        CardActivity activity = CardActivity.builder()
                .card(card)
                .description(description)
                .timestamp(LocalDateTime.now())
                .user(user)
                .build();

        activityRepo.save(activity);
    }

    public List<CardActivity> getHistory(Long cardId) {
        return activityRepo.findByCardIdOrderByTimestampDesc(cardId);
    }
}

