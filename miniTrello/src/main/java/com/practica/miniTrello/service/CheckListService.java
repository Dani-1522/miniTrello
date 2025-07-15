package com.practica.miniTrello.service;

import com.practica.miniTrello.entity.Card;
import com.practica.miniTrello.entity.CheckListItem;
import com.practica.miniTrello.repository.CardRepository;
import com.practica.miniTrello.repository.CheckListItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CheckListItemRepository checkListRepo;
    private final CardRepository cardRepo;

    public CheckListItem createItem(Long cardId, String text, String username) {
        Card card = cardRepo.findById(cardId)
                .orElseThrow(()-> new NoSuchElementException("card not found"));

        if (!card.getBoardList().getBoard().getOwner().getUsername().equals(username)){
            throw new AccessDeniedException("user not found");
        }
        CheckListItem item = CheckListItem.builder()
                .text(text)
                .completed(false)
                .card(card)
                .build();

        return checkListRepo.save(item);
    }

    public List<CheckListItem> getItems(Long cardId, String username) {
        Card card = cardRepo.findById(cardId)
                .orElseThrow(()-> new NoSuchElementException("card not found"));

        if (!card.getBoardList().getBoard().getOwner().getUsername().equals(username)
        && card.getCollaborators().stream().noneMatch(u -> u.getUsername().equals(username))){
            throw new AccessDeniedException("Not authorized");
        }
        return checkListRepo.findByCardId(cardId);
    }

    public CheckListItem updateItem(Long id, String text, Boolean completed, String username) {
        CheckListItem item = checkListRepo.findById(id)
                .orElseThrow(()-> new NoSuchElementException("card not found"));

        if(!item.getCard().getBoardList().getBoard().getOwner().getUsername().equals(username)){
            throw new AccessDeniedException("user not found");
        }
        if (text !=null)item.setText(text);
        if (completed!=null)item.setCompleted(completed);
        return checkListRepo.save(item);
    }

    public void deleteItem(Long id, String username) {
        CheckListItem item = checkListRepo.findById(id)
                .orElseThrow(()-> new NoSuchElementException("card not found"));

        if (!item.getCard().getBoardList().getBoard().getOwner().getUsername().equals(username)){
            throw new AccessDeniedException("user not found");
        }
        checkListRepo.delete(item);
    }
}
