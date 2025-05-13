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
    private final Integer subscribeTime = 30;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(Message message) {
        var user = userRepository.findById(message.getChatId());
        if (user.isEmpty()) {
            userRepository.save(
                    new User(
                            message.getChatId(),
                            message.getChat().getFirstName(),
                            message.getChat().getLastName(),
                            message.getChat().getUserName(),
                            LocalDateTime.now(ZoneId.of(UTC.name()))
                    )
            );
            log.info("Registered user: {}", user);
        } else if (!user.get().getActive()) {
            user.get().setActive(true);
            userRepository.save(user.get());
        }
    }

    public void deactivateUser(Long chatId) {
        var user = userRepository.findById(chatId);
        if (user.isPresent()) {
            user.get().setActive(false);
            userRepository.save(user.get());
            log.info("deactivate user: {}", user);
        }
    }

    public String subscribeUser(Long chatId) {
        var user = userRepository.findById(chatId);
        if (user.isPresent() && user.get().getActive()) {
            user.get().setSubscriptionStartDate(LocalDateTime.now(ZoneId.of(UTC.name())));
            user.get().setSubscriptionEndDate(LocalDateTime.now(ZoneId.of(UTC.name())).plusDays(subscribeTime));
            userRepository.save(user.get());
            log.info("subscribe user: {}", user);
            return String.format("Вы оформили подписку на %s дней", subscribeTime);
        }
        return "Вы не ктивны иди не подписались на бота";
    }

    public String getData(Long chatId) {
        var userOptional = userRepository.findById(chatId);
        String message;
        if (userOptional.isEmpty()) {
            message = "Вы пока не зарегистрированны";
        } else {
            var user = userOptional.get();
            if (!user.getActive()) {
                message = "Вы остановили бота для активации нажмите /start";
            } else {
                if (user.getSubscriptionEndDate() == null) {
                    message = "Вы не оформляли подписку";
                } else if (user.getSubscriptionStartDate().isAfter(LocalDateTime.now())) {
                    message = "Ваша подписка закончилась" + user.getSubscriptionEndDate() + ", для продления произведите оплату";
                } else {
                    message = "Ваша подписка активна" + user.getSubscriptionEndDate();
                }
            }
        }
        return message;
    }
}
