package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.entity.CheckListItem;
import com.practica.miniTrello.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
public class CheckListController {

    private final CheckListService checkListService;

    @PostMapping("/{cardId}")
    public ResponseEntity<CheckListItem> crate(@PathVariable Long cardId,
                                               @RequestBody Map<String, String> body,
                                               Authentication authentication) {
        return ResponseEntity.ok(
                checkListService.createItem(cardId, body.get("text"), authentication.getName())
        );
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<List<CheckListItem>> get (@PathVariable Long cardId,
                                                    Authentication auth) {
        return ResponseEntity.ok(checkListService.getItems(cardId, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CheckListItem> update(@PathVariable Long id,
                                                @RequestBody Map<String, Object> body,
                                                Authentication auth) {
        String text = (String) body.get("text");
        Boolean completed = (Boolean) body.get("completed");

        return ResponseEntity.ok( checkListService.updateItem(id, text, completed, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id, Authentication auth) {
        checkListService.deleteItem(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

}
