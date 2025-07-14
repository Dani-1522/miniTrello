package com.practica.miniTrello.service;

import com.practica.miniTrello.entity.BoardList;
import com.practica.miniTrello.entity.Card;
import com.practica.miniTrello.repository.BoardListRepository;
import com.practica.miniTrello.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final BoardListRepository listRepository;

    public Card createCard(Long listId, String title, String description, String username) {
        BoardList list = listRepository.findById(listId)
                .orElseThrow(() -> new NoSuchElementException("List not found"));

        if (!list.getBoard().getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Not the owner");
        }

        int nextPosition = cardRepository
                .findByBoardListId(listId)
                .stream()
                .mapToInt(Card::getPosition)
                .max()
                .orElse(-1) + 1;

        Card card = Card.builder()
                .title(title)
                .description(description)
                .boardList(list)
                .position(nextPosition)
                .build();

        return cardRepository.save(card);
    }

    @Transactional
    public void moveCard(Long cardId, Long targetListId, int newPosition, String username) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("Card not found"));

        if (!card.getBoardList().getBoard().getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Not the owner");
        }

        BoardList newList = listRepository.findById(targetListId)
                .orElseThrow(() -> new NoSuchElementException("Target list not found"));

        // Reordenar tarjetas de destino
        List<Card> cards = cardRepository.findByBoardListId(targetListId)
                .stream()
                .sorted(Comparator.comparingInt(Card::getPosition))
                .collect(Collectors.toList());

        cards.add(newPosition, card); // insertamos la tarjeta en la posición deseada

        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            c.setBoardList(newList);
            c.setPosition(i);
        }

        cardRepository.saveAll(cards);
    }


    public List<Card> getCardsByList(Long listId, String username) {
        getVerifiedList(listId, username); // Solo verificación de acceso
        return cardRepository.findByBoardListId(listId);
    }

    public Card updateCard(Long id, String title, String description, String username) {
        Card card = getVerifiedCard(id, username);

        card.setTitle(title);
        card.setDescription(description);
        return cardRepository.save(card);
    }

    public void deleteCard(Long id, String username) {
        Card card = getVerifiedCard(id, username);
        cardRepository.delete(card);
    }

    // Métodos auxiliares privados
    private BoardList getVerifiedList(Long listId, String username) {
        BoardList list = listRepository.findById(listId)
                .orElseThrow(() -> new NoSuchElementException("List not found"));

        if (!list.getBoard().getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Not the board owner");
        }

        return list;
    }

    private Card getVerifiedCard(Long cardId, String username) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("Card not found"));

        if (!card.getBoardList().getBoard().getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Not the board owner");
        }

        return card;
    }
}