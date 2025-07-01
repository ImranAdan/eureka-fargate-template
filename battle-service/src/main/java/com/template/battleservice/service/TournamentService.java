package com.template.battleservice.service;

import com.template.battleservice.engine.BattleResolver;
import com.template.battleservice.model.BattleLogEntry;
import com.template.battleservice.model.Bot;
import com.template.battleservice.model.TournamentResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TournamentService {

    private final BotFactory botFactory;
    private final BattleResolver resolver;

    public TournamentService(BotFactory botFactory, BattleResolver resolver) {
        this.botFactory = botFactory;
        this.resolver = resolver;
    }

    public TournamentResult runTournament(int participantCount) {
        if (participantCount < 2 || (participantCount & (participantCount - 1)) != 0) {
            throw new IllegalArgumentException("Participant count must be a power of 2 (e.g., 4, 8, 16).");
        }

        List<Bot> bots = createParticipants(participantCount);
        List<BattleLogEntry> battleLog = new ArrayList<>();

        while (bots.size() > 1) {
            List<Bot> nextRound = new ArrayList<>();
            for (int i = 0; i < bots.size(); i += 2) {
                Bot b1 = bots.get(i);
                Bot b2 = bots.get(i + 1);

                Bot winner = resolver.resolve(b1, b2);
                nextRound.add(winner);
                battleLog.add(new BattleLogEntry(b1, b2, winner));
            }
            bots = nextRound;
        }

        return new TournamentResult(bots.get(0), battleLog);
    }

    private List<Bot> createParticipants(int count) {
        List<Bot> bots = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            bots.add(botFactory.createRandomBot());
        }
        return bots;
    }
}
