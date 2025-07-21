package com.practica.miniTrello.service;

import com.practica.miniTrello.DTOs.CardSearchRequest;
import com.practica.miniTrello.entity.Board;
import com.practica.miniTrello.entity.Card;
import com.practica.miniTrello.entity.Tag;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.repository.CardRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardSearchService {

    private final CardRepository cardRepository;

    public List<Card> searchCards(CardSearchRequest request, String currentUser) {
        return cardRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            Join<Card, Board> boardJoin = root.join("boardList").join("board");
            Join<Board, User> ownerJoin = boardJoin.join("owner");
            predicates.add(cb.equal(ownerJoin.get("username"), currentUser));

            if (request.keyword() != null && !request.keyword().isBlank()) {
                Predicate titleMatch = cb.like(cb.lower(root.get("title")), "%" + request.keyword().toLowerCase() + "%");
                Predicate descMatch = cb.like(cb.lower(root.get("description")), "%" + request.keyword().toLowerCase() + "%");
                predicates.add(cb.or(titleMatch, descMatch));
            }

            if (request.collaboratorUsernames() != null && !request.collaboratorUsernames().isEmpty()) {
                Join<Card, User> collabJoin = root.join("collaborators");
                predicates.add(collabJoin.get("username").in(request.collaboratorUsernames()));
            }

            if (request.tagIds() != null && !request.tagIds().isEmpty()) {
                Join<Card, Tag> tagJoin = root.join("tags");
                predicates.add(tagJoin.get("id").in(request.tagIds()));
            }

            if (request.boardId() != null) {
                predicates.add(cb.equal(boardJoin.get("id"), request.boardId()));
            }

            if (request.listId() != null) {
                predicates.add(cb.equal(root.join("boardList").get("id"), request.listId()));
            }

            if (request.dueBefore() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dueDate"), request.dueBefore().atStartOfDay()));
            }

            if (request.dueAfter() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dueDate"), request.dueAfter().atStartOfDay()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
