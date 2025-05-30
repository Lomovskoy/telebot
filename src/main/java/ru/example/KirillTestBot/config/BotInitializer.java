package ru.example.KirillTestBot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.example.KirillTestBot.bot.TelegramBotService;

import java.util.ArrayList;

@Slf4j
@Component
public class BotInitializer {

    private final TelegramBotService telegramBotService;

    public BotInitializer(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
        var listOfCommands = new ArrayList<BotCommand>();
        telegramBotService.getBotConfig().menu.forEach((k, v) -> listOfCommands.add(new BotCommand(String.format("/%s",k), v)));
        try {
            this.telegramBotService.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: {}", e.getMessage());
        }
    }
    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBotService);
        } catch (TelegramApiException ex) {
            log.error("Error registering telegram bot: {}", ex.getMessage(), ex);
        }
    }
}
