package com.practica.miniTrello.service;


import com.practica.miniTrello.DTOs.TagDTO;
import com.practica.miniTrello.entity.Board;
import com.practica.miniTrello.entity.Card;
import com.practica.miniTrello.entity.Tag;
import com.practica.miniTrello.repository.BoardRepository;
import com.practica.miniTrello.repository.CardRepository;
import com.practica.miniTrello.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;

    public TagDTO createTag(Long boardID, TagDTO tagDTO) {
        Board board = boardRepository.findById(boardID)
                .orElseThrow(()-> new NoSuchElementException("Board not found"));

        Tag tag = Tag.builder()
                .name(tagDTO.name())
                .color(tagDTO.color())
                .board(board)
                .build();
        return toDTO(tagRepository.save(tag));
    }

    public List<TagDTO> getTagsForBoard(Long boardID) {
        return tagRepository.findByBoardId(boardID)
                .stream()
                .map(this::toDTO)
                .toList();
    }
    public void deleteTag(Long tagID) {
        tagRepository.deleteById(tagID);
    }

    public void assignTagToCard(Long cardId, Long tagId){
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()-> new NoSuchElementException("Card not found"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new NoSuchElementException("Tag not found"));

        card.getTags().add(tag);
        cardRepository.save(card);
    }

    public void removeTagFromCard(Long cardID, Long tagID){

        Card card = cardRepository.findById(cardID)
                .orElseThrow(()-> new NoSuchElementException("Card not found"));

        Tag tag = tagRepository.findById(tagID)
                .orElseThrow(()-> new NoSuchElementException("Tag not found"));

        card.getTags().remove(tag);
        cardRepository.save(card);
    }

    private TagDTO toDTO(Tag tag) {
        return new TagDTO(tag.getId(), tag.getName(), tag.getColor());
    }
}
