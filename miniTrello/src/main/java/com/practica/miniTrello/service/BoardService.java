package com.practica.miniTrello.service;

import com.practica.miniTrello.entity.Board;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.repository.BoardRepository;
import com.practica.miniTrello.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board createBoard(Board board, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("user no found"));
        board.setOwner(owner);
        return boardRepository.save(board);
    }
    public List<Board> getBoardsByUser(String username) {
        return boardRepository.findByOwnerUsername(username);
    }

    public Optional<Board> getById(Long id) {
        return boardRepository.findById(id);
    }


    public Optional<Board> updateBoard(Long id, Board updatedData, String username) {
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            if (!board.getOwner().getUsername().equals(username)) {
                return Optional.empty(); // No es el dueño → acceso denegado
            }

            board.setName(updatedData.getName());
            board.setDescription(updatedData.getDescription());
            return Optional.of(boardRepository.save(board));
        }

        return Optional.empty(); // No existe el board
    }
    @Transactional
    public void reorderBoards(List<Long> boardIds, String username) {
        List<Board> boards = boardRepository.findByOwner_UsernameOrderByPosition(username);

        Map<Long, Board> boardMap = boards.stream()
                .collect(Collectors.toMap(Board::getId, Function.identity()));

        for (int i = 0; i < boardIds.size(); i++) {
            Long id = boardIds.get(i);
            Board board = boardMap.get(id);

            if (board != null && board.getOwner().getUsername().equals(username)) {
                board.setPosition(i);
            }
        }

        boardRepository.saveAll(boardMap.values());
    }

    public void deleteBoard(Long id, String username) {
          Board board = boardRepository.findById(id)
                  .orElseThrow(()-> new NoSuchElementException("Board no found"));
          if(!board.getOwner().getUsername().equals(username)){
              throw new UsernameNotFoundException("Not no found");
          }
          boardRepository.deleteById(id);

    }
}
