package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByBoardId(Long boardId);
}
