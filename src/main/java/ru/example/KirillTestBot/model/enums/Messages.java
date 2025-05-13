package ru.example.KirillTestBot.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Messages {
    HELP ("/help", """
    Мои команды:
        /start - запускает бота
        /stop - останавливает бота
        /data - Получить информацию о подписке
        /subscribe - Оформить подписку
        /help - Информация как использовать этого бота
    """),
    START ("/start", """
    :wave: Здравствуйте, я бот Марии Виноградовой, от которой все-все-все в ахуе. :scream:
    С помошью меня вы можете оформить подписку, и получать видеоуроки и задания.
    """),
    DATA("/data", "Информация о вашей подписке: %s"),
    DEFAULT("", "Я не знаю такой команды"),
    SUBSCRIBE("/subscribe", ""),
    STOP ("/stop","Вы отключили бота.");

    private final String key;
    private final String message;

    public static Messages getMessageToKey(String key) {
        return Arrays.stream(values()).filter(it -> it.key.contains(key)).findFirst().orElseThrow();
    }
}
