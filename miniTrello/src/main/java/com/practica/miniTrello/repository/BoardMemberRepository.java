package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
    List<BoardMember> findByUser_Username(String username);
    Optional<BoardMember> findByBoardIdAndUserUsername(Long boardId, String username);
}
