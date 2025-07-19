package com.practica.miniTrello.repository;

import com.practica.miniTrello.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipient_UsernameOrderByCreatedAtDesc(String username);
}
