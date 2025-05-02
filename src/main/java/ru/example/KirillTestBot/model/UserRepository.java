package ru.example.KirillTestBot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.KirillTestBot.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
