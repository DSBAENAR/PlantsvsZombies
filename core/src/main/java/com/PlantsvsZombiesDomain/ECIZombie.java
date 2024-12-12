package com.PlantsvsZombiesDomain;

import java.util.Timer;
import java.util.TimerTask;

public class ECIZombie extends AttackZombie{

    private long attackSpeed = 3000;

    public ECIZombie(int[] initalPosition, Player owner, Board board) throws PlantsVsZombiesException {
        super(initalPosition, 200, 250, 50, 3000, owner, board);
    }


}
