package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {
    List<BoardList> findByBoardId(Long BoardId);
    List<BoardList> findByBoardIdOrderByPosition(Long boardId);

}
