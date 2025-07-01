package com.template.battleservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResult {
    private Bot winner;
    private Bot loser;
    private String log;
}
