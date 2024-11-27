package com.PlantsvsZombiesDomain;

import java.util.ArrayList;

/**
 * Class HumanPlayer that extends Player class, this class is for the human players
 */
public class HumanPlayer extends Player {

    /**
     * Constructor of HumanPlayer
     * @param name of the player
     * @param money its the initial money of the player
     * @param isPlant if the player is a plant (True) or a zombie (False)
     */
    public HumanPlayer(String name, int money, boolean isPlant) {
        super(name, money, isPlant);
    }
}
