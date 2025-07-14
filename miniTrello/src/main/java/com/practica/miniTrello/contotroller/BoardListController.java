package com.practica.miniTrello.contotroller;


import com.practica.miniTrello.entity.BoardList;
import com.practica.miniTrello.service.BoardListService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class BoardListController {

    private final BoardListService listService;

    @PostMapping
    public ResponseEntity<BoardList> create(@RequestParam Long boardId,
                                            @RequestBody Map<String, String> body,
                                            Authentication authentication) {
        String title = body.get("title");
        String username = authentication.getName();
        return ResponseEntity.ok(listService.createList(boardId, title, username));
    }

    @GetMapping("/by-board/{boardId}")
    public ResponseEntity<List<BoardList>> getLists(@PathVariable Long boardId,
                                                    Authentication authentication) {
        return ResponseEntity.ok(
                listService.getListsByBoard(boardId, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardList> update(@PathVariable Long id,
                                            @RequestBody Map<String, String> body,
                                            Authentication authentication) {
        return ResponseEntity.ok(
                listService.updateList(id, body.get("title"), authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       Authentication authentication) {
        listService.deleteList(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
