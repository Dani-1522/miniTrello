package com.practica.miniTrello.service;

import com.practica.miniTrello.entity.Board;
import com.practica.miniTrello.entity.BoardList;
import com.practica.miniTrello.repository.BoardListRepository;
import com.practica.miniTrello.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;


import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardListService {

    private final BoardRepository boardRepository;
    private final BoardListRepository listRepository;

    public BoardList createList(Long boardId, String title, String username) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));

        if (!board.getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Not the board owner");
        }

        BoardList list = BoardList.builder()
                .title(title)
                .position(board.getPosition())
                .board(board)
                .build();

        return listRepository.save(list);
    }

    public List<BoardList> getListsByBoard(Long boardId, String username) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));

        if (!board.getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Not the board owner");
        }

        return listRepository.findByBoardId(boardId);
    }

    public BoardList updateList(Long id, String title, String username) {
        BoardList list = listRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("List not found"));

        if (!list.getBoard().getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Not the owner");
        }

        list.setTitle(title);
        return listRepository.save(list);
    }

    @Transactional
    public void reorderLists(Long boardId, List<Long> listIds, String username) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));

        if (!board.getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Not the owner");
        }

        List<BoardList> lists = listRepository.findByBoardIdOrderByPosition(boardId);
        Map<Long, BoardList> map = lists.stream()
                .collect(Collectors.toMap(BoardList::getId, Function.identity()));

        for (int i = 0; i < listIds.size(); i++) {
            Long id = listIds.get(i);
            BoardList list = map.get(id);
            if (list != null) {
                list.setPosition(i);
            }
        }

        listRepository.saveAll(map.values());
    }

    public void deleteList(Long id, String username) {
        BoardList list = listRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("List not found"));

        if (!list.getBoard().getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Not the owner");
        }

        listRepository.delete(list);
    }
}
