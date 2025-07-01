package com.template.battleservice.controller;

import com.template.battleservice.model.TournamentResult;
import com.template.battleservice.service.TournamentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tournament")
public class BattleController {

    private final TournamentService tournamentService;

    public BattleController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    // GET /tournament?participants=8
    @GetMapping
    public TournamentResult runTournament(@RequestParam(defaultValue = "8") int participants) {
        return tournamentService.runTournament(participants);
    }
}
