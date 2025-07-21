package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.entity.Board;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {


    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Board> crearBoard(@RequestBody  Board board,
                                            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(boardService.createBoard(board, username ));
    }

    @GetMapping
    public ResponseEntity<List<Board>> getUserBoards(Principal principal) {
        return ResponseEntity.ok(boardService.getBoardsByUser(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id, Principal principal) {
        return boardService.getById(id)
                .filter(board -> board.getOwner().getUsername().equals(principal.getName()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long id,
                                             @RequestBody Board updatedBoard,
                                             Principal principal) {
        return boardService.updateBoard(id, updatedBoard, principal.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
    @PutMapping("/reorder")
    public ResponseEntity<Void> reorderBoards(
            @RequestBody List<Long> ids,
            @AuthenticationPrincipal User user
    ) {
        boardService.reorderBoards(ids, user.getUsername());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, Principal principal) {
        boardService.deleteBoard(id, principal.getName());
        return ResponseEntity.noContent().build();
    }


}
