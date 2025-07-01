package com.template.battleservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattleLogEntry {
    private Bot bot1;
    private Bot bot2;
    private Bot winner;
}
