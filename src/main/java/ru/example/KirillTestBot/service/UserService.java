package ru.example.KirillTestBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.example.KirillTestBot.model.UserRepository;
import ru.example.KirillTestBot.model.entity.User;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.time.zone.ZoneOffsetTransitionRule.TimeDefinition.UTC;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(Message message) {
        if (userRepository.findById(message.getChatId()).isEmpty()) {
            var user = new User(
                    message.getChatId(),
                    message.getChat().getFirstName(),
                    message.getChat().getLastName(),
                    message.getChat().getUserName(),
                    LocalDateTime.now(ZoneId.of(UTC.name()))
            );
            userRepository.save(user);
            log.info("Registered user: {}", user);
        }
    }
}
