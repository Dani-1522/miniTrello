package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.entity.Card;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<Card> create(@RequestParam Long listId,
                                       @RequestBody Map<String, String> body,
                                       Authentication authentication) {
        return ResponseEntity.ok(cardService.createCard(
                listId,
                body.get("title"),
                body.get("description"),
                authentication.getName()
        ));
    }

    @GetMapping("/by-list/{listId}")
    public ResponseEntity<List<Card>> getByList(@PathVariable Long listId,
                                                Authentication authentication) {
        return ResponseEntity.ok(cardService.getCardsByList(listId, authentication.getName()));
    }

    @PutMapping("/move/{cardId}")
    public ResponseEntity<Void> moveCard(@PathVariable Long cardId,
                                         @RequestBody Map<String, Object> body,
                                         Authentication auth) {
        Long targetListId = Long.valueOf(body.get("targetListId").toString());
        int newPosition = Integer.parseInt(body.get("newPosition").toString());

        cardService.moveCard(cardId, targetListId, newPosition, auth.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cardId}/assign")
    public ResponseEntity<Void> assignUser(@PathVariable Long cardId,
                                           @RequestBody Map<String, String> body,
                                           Authentication auth) {
        cardService.addUserToCard(cardId, body.get("username"), auth.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cardId}/unassign")
    public ResponseEntity<Void> unassignUser(@PathVariable Long cardId,
                                             @RequestBody Map<String, String> body,
                                             Authentication auth) {
        cardService.removeUserFromCard(cardId, body.get("username"), auth.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cardId}/collaborators")
    public ResponseEntity<Set<User>> getCollaborators(@PathVariable Long cardId,
                                                      Authentication auth) {
        return ResponseEntity.ok(cardService.getCardCollaborators(cardId, auth.getName()));
    }

    @PutMapping("/{cardId}/due-date")
    public ResponseEntity<Card> setDueDate(@PathVariable Long cardId,
                                           @RequestBody Map<String, String> body,
                                           Authentication auth) {
        LocalDateTime dueDate = LocalDateTime.parse(body.get("dueDate"));
        return ResponseEntity.ok(
                cardService.updateDueDate(cardId, dueDate, auth.getName())
        );
    }

    @GetMapping("/due/upcoming")
    public ResponseEntity<List<Card>> getUpcoming(Authentication auth) {
        return ResponseEntity.ok(cardService.getUpcomingDueCards(auth.getName()));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Card> update(@PathVariable Long id,
                                       @RequestBody Map<String, String> body,
                                       Authentication authentication) {
        return ResponseEntity.ok(cardService.updateCard(
                id,
                body.get("title"),
                body.get("description"),
                authentication.getName()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       Authentication authentication) {
        cardService.deleteCard(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
