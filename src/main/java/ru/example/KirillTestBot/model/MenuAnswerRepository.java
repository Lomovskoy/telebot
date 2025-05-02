package ru.example.KirillTestBot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.KirillTestBot.model.entity.MenuAnswer;

import java.util.UUID;

@Repository
public interface MenuAnswerRepository extends JpaRepository<MenuAnswer, UUID> {
    MenuAnswer findByCommand(String command);
}
