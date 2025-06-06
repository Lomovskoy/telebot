package ru.example.KirillTestBot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    String name;
    String token;
    Map<String, String> menu;
}
