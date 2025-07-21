package com.practica.miniTrello.service;

import com.practica.miniTrello.entity.Board;
import com.practica.miniTrello.entity.BoardMember;
import com.practica.miniTrello.entity.BoardRole;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.repository.BoardMemberRepository;
import com.practica.miniTrello.repository.BoardRepository;
import com.practica.miniTrello.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardMemberService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardMemberRepository boardMemberRepository;

    public void addMember(Long boardId, String usernameToAdd, BoardRole role, String currentUser) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));

        boolean isOwner = board.getOwner().getUsername().equals(currentUser);
        if (!isOwner) {
            throw new AccessDeniedException("Only owner can share board.");
        }

        User user = userRepository.findByUsername(usernameToAdd)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        BoardMember member = BoardMember.builder()
                .board(board)
                .user(user)
                .role(role)
                .build();

        boardMemberRepository.save(member);
    }

    public BoardRole getUserRole(Long boardId, String username) {
        return boardMemberRepository.findByBoardIdAndUserUsername(boardId, username)
                .map(BoardMember::getRole)
                .orElse(null);
    }

    public List<User> getBoardMembers(Long boardId) {
        return boardMemberRepository.findAll().stream()
                .filter(m -> m.getBoard().getId().equals(boardId))
                .map(BoardMember::getUser)
                .toList();
    }
}
