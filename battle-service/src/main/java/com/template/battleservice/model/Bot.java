package com.template.battleservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bot {
    private String name;
    private String type;
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private double critChance;
    private double dodgeChance;
}
