package com.practica.miniTrello.contotroller;

import com.practica.miniTrello.DTOs.TagDTO;
import com.practica.miniTrello.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/board/{boardid}")
    public ResponseEntity<TagDTO> create(@PathVariable Long boardId,
                                         @RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.createTag(boardId, tagDTO));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<TagDTO>> getAll(@PathVariable Long boardId) {
        return ResponseEntity.ok(tagService.getTagsForBoard(boardId));
    }

    @DeleteMapping("/{tangId}")
    public ResponseEntity<Void> delete(@PathVariable Long tangId) {
        tagService.deleteTag(tangId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/assign")
    public ResponseEntity<Void> assignToCard(@RequestParam Long cardId,
                                             @RequestParam Long tagId) {
        tagService.assignTagToCard(cardId, tagId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCard(@RequestParam Long cardId,
                                               @RequestParam Long tagId) {
        tagService.removeTagFromCard(cardId, tagId);
        return ResponseEntity.noContent().build();
    }
}
