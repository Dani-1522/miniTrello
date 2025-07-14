package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardListRepository extends JpaRepository<BoardList, Long> {
    List<BoardList> findByBoardId(Long BoardId);
}
