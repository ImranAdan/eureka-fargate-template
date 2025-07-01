package com.template.battleservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "bots")
public class BotConfigProperties {
    private Map<String, BotStats> types;

    @Data
    public static class BotStats {
        private String type;
        private int hp;
        private int attack;
        private int defense;
        private int speed;
        private double critChance;
        private double dodgeChance;
    }
}
