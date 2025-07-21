package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.DTOs.CardSearchRequest;
import com.practica.miniTrello.entity.Card;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.service.CardSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class CardSearchController {

    private final CardSearchService cardSearchService;

    @PostMapping("/cards")
    public ResponseEntity<List<Card>> searchCards(
            @RequestBody CardSearchRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        List<Card> result = cardSearchService.searchCards(request, currentUser.getUsername());
        return ResponseEntity.ok(result);
    }
}
