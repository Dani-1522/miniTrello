package com.practica.miniTrello.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String filename;
    private String fileType;
    private String filePath;

    private LocalDateTime uploadedAt;

    @ManyToOne
    private User uploader;

    @ManyToOne
    private  Card card;

}
