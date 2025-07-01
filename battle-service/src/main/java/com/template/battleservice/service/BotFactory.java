package com.template.battleservice.service;

import com.github.javafaker.Faker;
import com.template.battleservice.config.BotConfigProperties;
import com.template.battleservice.model.Bot;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BotFactory {

    private final BotConfigProperties config;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    public BotFactory(@Qualifier("botConfigProperties") BotConfigProperties config) {
        this.config = config;
    }

    public Bot createRandomBot() {
        List<BotConfigProperties.BotStats> types = new ArrayList<>(config.getTypes().values());
        BotConfigProperties.BotStats stats = types.get(random.nextInt(types.size()));
        return new Bot(
                faker.name().firstName(),
                stats.getType(),
                stats.getHp(),
                stats.getAttack(),
                stats.getDefense(),
                stats.getSpeed(),
                stats.getCritChance(),
                stats.getDodgeChance()
        );
    }
}
