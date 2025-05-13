package ru.example.KirillTestBot.bot;

import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.example.KirillTestBot.config.BotConfig;
import ru.example.KirillTestBot.service.UserService;
import ru.example.KirillTestBot.model.enums.Messages;

@Slf4j
@Service
public class TelegramBotService extends TelegramLongPollingBot {
    @Getter
    private final BotConfig botConfig;
    private final UserService userService;

    public TelegramBotService(BotConfig botConfig, UserService userService) {
        this.botConfig = botConfig;
        this.userService = userService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();
        var userName = update.getMessage().getChat().getFirstName();

        var text = update.getMessage().getText().toLowerCase();
        var message = Messages.getMessageToKey(text);
        log.info("{} drank: {}", userName, text);

        switch (message) {
            case Messages.START: {
                userService.registerUser(update.getMessage());
                sendMessage(chatId, EmojiParser.parseToUnicode(message.getMessage()));
            }
            break;
            case Messages.STOP, HELP, DATA, FREEZE:
                sendMessage(chatId, EmojiParser.parseToUnicode(message.getMessage()));
                break;
            default:
                sendMessage(chatId, EmojiParser.parseToUnicode(Messages.DEFAULT.getMessage()));
                break;
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

    private void sendMessage(Long chatId, String text) {
        var message = new SendMessage(chatId.toString(), text);
        try {
            execute(message);
        } catch (TelegramApiException ex) {
            log.error("Error sending message from bot: {}", ex.getMessage(), ex);
        }
    }


}
