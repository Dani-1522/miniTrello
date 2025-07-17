package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.entity.CardActivity;
import com.practica.miniTrello.service.CardActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardActivityController {

    private final CardActivityService activityService;

    @GetMapping("/{cardId}/history")
    public ResponseEntity<List<CardActivity>> getHistory(@PathVariable Long cardId) {
        return ResponseEntity.ok(activityService.getHistory(cardId));
    }
}
