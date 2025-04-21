package ru.example.KirillTestBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.example.KirillTestBot.config.BotConfig;

@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final String defaultMessage = "Хуйню пишешь!";

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();
        var userName = update.getMessage().getChat().getFirstName();

        if (update.hasMessage() && update.getMessage().hasText()) {
            var text = update.getMessage().getText().toLowerCase();
            switch (text) {
                case "/start": startCommandReceived(chatId, userName);
                    break;
                case "/stop": stopCommandReceived(chatId, userName);
                    break;
                default: sendMessage(chatId, defaultMessage);
                    break;
            }
        } else {
            defaultCommandReceived(chatId, userName, "/start", "/stop");
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    private void startCommandReceived(long chatId, String userName) {
        var message = String.format("%s хули ты пишешь?!", userName);
        sendMessage(chatId, message);

    }

    private void stopCommandReceived(long chatId, String userName) {
        var message = String.format("%s пошёл на хуй!", userName);
        sendMessage(chatId, message);

    }

    private void defaultCommandReceived(long chatId, String userName, String start, String stop) {
        var message = String.format("%s я умею %s и %s в остальном пошел на хуй!", userName, start, stop);
        sendMessage(chatId, message);

    }

    private void sendMessage(Long chatId, String text) {
        var message = new SendMessage(chatId.toString(), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}
