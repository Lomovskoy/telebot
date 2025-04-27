package ru.example.KirillTestBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.example.KirillTestBot.config.BotConfig;

@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {
    public final BotConfig botConfig;
    public static String HELP_MESSAGE = """
        Здравствуйте, я бот Марии Виноградовой, от которой все-все-все в ахуе.\s
        С помошью меня вы можете оформить подписку, и получать видеоулекии и задания.
        Мои команды:
                /start - запускает бота
                /stop - останавливает бота
                /data - Получить информацию о подписке
                /freeze - Заморозить подписку
                /help - Информация как использовать этого бота
                /settings - Установите ваши предпочтения
   \s""";

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();
        var userName = update.getMessage().getChat().getFirstName();

        if (update.hasMessage() && update.getMessage().hasText()) {
            var text = update.getMessage().getText().toLowerCase();
            log.info("{} drank: {}", userName, text);
            switch (text) {
                case "/start": startCommandReceived(chatId, userName);
                    break;
                case "/stop": stopCommandReceived(chatId, userName);
                    break;
                case "/help": sendMessage(chatId, HELP_MESSAGE);
                    break;
                default: sendMessage(chatId, "Хуйню пишешь!");
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
        } catch (TelegramApiException ex) {
            log.error("Error sending message from bot: {}", ex.getMessage(), ex);
        }
    }


}
