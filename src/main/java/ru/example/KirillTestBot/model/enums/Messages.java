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
        /stop - останавливает подписку
        /data - Получить информацию о подписке
        /freeze - Заморозить подписку
        /help - Информация как использовать этого бота
    """),
    START ("/start", """
    Здравствуйте, я бот Марии Виноградовой, от которой все-все-все в ахуе.
    С помошью меня вы можете оформить подписку, и получать видеоуроки и задания.
    """),
    DATA("/data", "Информация о вашей подписке: %s"),
    FREEZE("/freeze", "Ваша поджписка заморожена, для разморозки нажмите /start"),
    DEFAULT("", "Я не знаю такой команды"),
    STOP ("/stop","Вы отключили подписку.");

    private final String key;
    private final String message;

    public static Messages getMessageToKey(String key) {
        return Arrays.stream(values()).filter(it -> it.key.contains(key)).findFirst().orElseThrow();
    }
}
