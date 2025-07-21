package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.entity.BoardRole;
import com.practica.miniTrello.entity.User;
import com.practica.miniTrello.service.BoardMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardMemberController {

    private final BoardMemberService boardMemberService;

    @PostMapping("/{boardId}/share")
    public ResponseEntity<Void> shareBoard(
            @PathVariable Long boardId,
            @RequestParam String username,
            @RequestParam BoardRole role,
            @AuthenticationPrincipal User user
    ) {
        boardMemberService.addMember(boardId, username, role, user.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}/members")
    public ResponseEntity<List<User>> getMembers(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardMemberService.getBoardMembers(boardId));
    }
}

