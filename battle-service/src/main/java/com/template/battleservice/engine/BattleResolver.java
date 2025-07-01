package com.template.battleservice.engine;

import com.template.battleservice.model.BattleLogEntry;
import com.template.battleservice.model.Bot;
import com.template.battleservice.model.MatchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class BattleResolver {

    private final Random random = new Random();

    public Bot resolve(Bot bot1, Bot bot2) {
        int score1 = calculateScore(bot1);
        int score2 = calculateScore(bot2);

        // Add a bit of randomness to simulate battle variability
        score1 += random.nextInt(10);
        score2 += random.nextInt(10);

        return score1 >= score2 ? bot1 : bot2;
    }

    private int calculateScore(Bot bot) {
        return bot.getAttack() * 2
                + bot.getDefense()
                + bot.getSpeed()
                + (int) (bot.getCritChance() * 100)
                - (int) (bot.getDodgeChance() * 100);
    }

    public MatchResult simulateBattle(Bot bot1, Bot bot2) {
        Bot attacker = bot1.getSpeed() >= bot2.getSpeed() ? bot1 : bot2;
        Bot defender = attacker == bot1 ? bot2 : bot1;

        Bot b1 = cloneBot(bot1);
        Bot b2 = cloneBot(bot2);

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("Battle Start: ").append(b1.getName()).append(" vs ").append(b2.getName()).append("\n");

        while (b1.getHp() > 0 && b2.getHp() > 0) {
            logBuilder.append(attackTurn(attacker, defender));

            Bot temp = attacker;
            attacker = defender;
            defender = temp;
        }

        Bot winner = b1.getHp() > 0 ? b1 : b2;
        Bot loser = b1.getHp() <= 0 ? b1 : b2;

        logBuilder.append("Winner: ").append(winner.getName()).append(" (Type: ").append(winner.getType()).append(")\n");

        return new MatchResult(winner, loser, logBuilder.toString());
    }

    private String attackTurn(Bot attacker, Bot defender) {
        StringBuilder turnLog = new StringBuilder();

        if (random.nextDouble() < defender.getDodgeChance()) {
            turnLog.append(defender.getName()).append(" dodged the attack from ").append(attacker.getName()).append("!\n");
            return turnLog.toString();
        }

        int baseDamage = attacker.getAttack() - defender.getDefense();
        baseDamage = Math.max(1, baseDamage);

        if (random.nextDouble() < attacker.getCritChance()) {
            baseDamage *= 2;
            turnLog.append("Critical hit! ");
        }

        defender.setHp(defender.getHp() - baseDamage);

        turnLog.append(attacker.getName())
                .append(" attacks ")
                .append(defender.getName())
                .append(" for ")
                .append(baseDamage)
                .append(" damage. ")
                .append("[Remaining HP: ")
                .append(defender.getHp())
                .append("]\n");

        return turnLog.toString();
    }

    private Bot cloneBot(Bot original) {
        return new Bot(
                original.getName(),
                original.getType(),
                original.getHp(),
                original.getAttack(),
                original.getDefense(),
                original.getSpeed(),
                original.getCritChance(),
                original.getDodgeChance()
        );
    }
}
