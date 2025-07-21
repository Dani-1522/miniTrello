package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByOwnerUsername(String username);
    List<Board> findByOwner_UsernameOrderByPosition(String username);


}
