package com.template.battleservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
    private List<Bot> participants;
    private List<TournamentRound> rounds;
    private Bot winner;
}
