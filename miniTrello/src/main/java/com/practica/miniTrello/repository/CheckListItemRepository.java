package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.CheckListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckListItemRepository extends JpaRepository<CheckListItem, Long> {
    List<CheckListItem> findByCardId(Long boardId);
}
