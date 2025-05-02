package ru.example.KirillTestBot.service;

import org.springframework.stereotype.Service;
import ru.example.KirillTestBot.model.MenuAnswerRepository;

@Service
public class MenuAnswerService {

    private final MenuAnswerRepository menuAnswerRepository;

    public MenuAnswerService(MenuAnswerRepository menuAnswerRepository) {
        this.menuAnswerRepository = menuAnswerRepository;
    }

    public String getMessageToCommand(String command) {
        return menuAnswerRepository.findByCommand(command).getMessage();
    }
}
