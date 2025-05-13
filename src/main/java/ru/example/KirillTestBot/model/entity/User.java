package ru.example.KirillTestBot.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_data")
public class User {
    @Id
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "registered_at")
    private LocalDateTime registeredAt;
    @Column(name = "subscription_start_date")
    private LocalDateTime subscriptionStartDate;
    @Column(name = "subscription_end_date")
    private LocalDateTime subscriptionEndDate;
    @Column(name = "active")
    private Boolean active;

    public User(Long chatId, String firstName, String lastName, String userName, LocalDateTime registeredAt) {
        this.chatId = chatId;
        this.registeredAt = registeredAt;
        this.userName = userName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.active = true;
    }
}