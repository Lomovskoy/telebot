package ru.example.KirillTestBot.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "menu_answer")
public class MenuAnswer {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "command")
    private String command;
    @Column(name = "message")
    private String message;
}
